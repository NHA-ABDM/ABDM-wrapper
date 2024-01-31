/* (C) 2024 */
package com.nha.abdm.wrapper.hip.hrp.database.mongo.services;

import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;
import com.nha.abdm.wrapper.common.models.CareContext;
import com.nha.abdm.wrapper.common.responses.FacadeResponse;
import com.nha.abdm.wrapper.hip.hrp.consent.requests.HIPNotifyRequest;
import com.nha.abdm.wrapper.hip.hrp.database.mongo.repositories.LogsRepo;
import com.nha.abdm.wrapper.hip.hrp.database.mongo.repositories.PatientRepo;
import com.nha.abdm.wrapper.hip.hrp.database.mongo.tables.Patient;
import com.nha.abdm.wrapper.hip.hrp.database.mongo.tables.helpers.FieldIdentifiers;
import com.nha.abdm.wrapper.hip.hrp.link.hipInitiated.requests.LinkRecordsRequest;
import com.nha.abdm.wrapper.hip.hrp.link.userInitiated.responses.InitResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
  private static final Logger log = LogManager.getLogger(PatientService.class);
  @Autowired private final PatientRepo patientRepo;
  @Autowired private LogsRepo logsRepo;

  @Autowired MongoTemplate mongoTemplate;

  @Autowired
  public PatientService(PatientRepo patientRepo) {
    this.patientRepo = patientRepo;
  }

  /**
   * Fetch of patientReference using abhaAddress
   *
   * @param abhaAddress abhaAddress of patient.
   * @return patientReference.
   */
  public String getPatientReference(String abhaAddress) {
    Patient existingRecord = this.patientRepo.findByAbhaAddress(abhaAddress);
    return existingRecord != null ? existingRecord.getPatientReference() : "";
  }
  /**
   * Fetch of patientDisplay using abhaAddress
   *
   * @param abhaAddress abhaAddress of patient.
   * @return patientDisplay.
   */
  public String getPatientDisplay(String abhaAddress) {
    Patient existingRecord = this.patientRepo.findByAbhaAddress(abhaAddress);
    return existingRecord != null ? existingRecord.getDisplay() : "";
  }
  /**
   * Fetch of abhaAddress using abhaAddress
   *
   * @param patientReference patientReference of patient.
   * @return abhaAddress.
   */
  public String getAbhaAddress(String patientReference) {
    Patient existingRecord = this.patientRepo.findByPatientReference(patientReference);
    return existingRecord != null ? existingRecord.getAbhaAddress() : "";
  }

  /**
   * After successful linking of careContext updating the status i.e. isLinked to true or false.
   *
   * @param abhaAddress abhaAddress of patient.
   * @param careContexts List of careContext to update the status.
   */
  public void updateCareContextStatus(String abhaAddress, List<CareContext> careContexts) {
    BulkOperations bulkOperations =
        mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Patient.class);

    for (CareContext updatedCareContext : careContexts) {
      Query query =
          Query.query(
              Criteria.where("abhaAddress")
                  .is(abhaAddress)
                  .and("careContexts.referenceNumber")
                  .is(updatedCareContext.getReferenceNumber()));

      Update update = new Update().set("careContexts.$.isLinked", true);
      bulkOperations.updateOne(query, update);
    }

    bulkOperations.execute();
  }

  /**
   * <B>discovery</B>
   *
   * <p>Match the response careContexts with patient careContexts.
   *
   * @param initResponse Response from ABDM gateway to link particular careContexts.
   * @return if careContexts matches returns returns true else false.
   */
  public boolean checkCareContexts(InitResponse initResponse) {
    try {
      List<CareContext> patientCareContexts =
          patientRepo
              .findByPatientReference(initResponse.getPatient().getReferenceNumber())
              .getCareContexts();
      Set<String> patientReferenceNumbers =
          patientCareContexts.stream()
              .map(CareContext::getReferenceNumber)
              .collect(Collectors.toSet());
      return initResponse.getPatient().getCareContexts().stream()
          .allMatch(
              responseContext ->
                  patientReferenceNumbers.contains(responseContext.getReferenceNumber()));

    } catch (NullPointerException e) {
      log.error("Init CareContext verify failed -> mismatch of careContexts" + e);
    }
    return true;
  }

  /**
   * <B>hipInitiatedLinking</B>
   *
   * <p>After successful link of careContexts with abhaAddress storing them into patient.
   *
   * @param linkRecordsRequest Response to facade as /link-records for hipInitiatedLinking.
   */
  public void addPatientCareContexts(LinkRecordsRequest linkRecordsRequest) {
    String abhaAddress = linkRecordsRequest.getAbhaAddress();
    try {
      Patient existingRecord = this.patientRepo.findByAbhaAddress(abhaAddress);
      if (existingRecord == null) {
        log.error("Adding patient failed -> Patient not found");
      } else {
        List<CareContext> modifiedCareContexts =
            linkRecordsRequest.getPatient().getCareContexts().stream()
                .map(
                    careContextRequest -> {
                      CareContext modifiedContext = new CareContext();
                      modifiedContext.setReferenceNumber(careContextRequest.getReferenceNumber());
                      modifiedContext.setDisplay(careContextRequest.getDisplay());
                      modifiedContext.setLinked(true);
                      return modifiedContext;
                    })
                .collect(Collectors.toList());
        Query query =
            new Query(Criteria.where("abhaAddress").is(linkRecordsRequest.getAbhaAddress()));
        Update update = new Update().addToSet("careContext").each(modifiedCareContexts);
        this.mongoTemplate.updateFirst(query, update, Patient.class);
      }
    } catch (Exception e) {
      log.info("addPatient :" + e);
    }
    log.info("Successfully Added Patient careContexts");
  }

  public void addConsentArtefacts(HIPNotifyRequest hipNotifyRequest) {
    String abhaAddress =
        hipNotifyRequest.getHIPNotification().getConsentDetail().getPatient().getId();
    Query query = new Query(Criteria.where(FieldIdentifiers.ABHA_ADDRESS).is(abhaAddress));
    Update update =
        new Update()
            .addToSet(
                FieldIdentifiers.CONSENT_ARTEFACTS,
                hipNotifyRequest.getHIPNotification().getConsentDetail());
    mongoTemplate.updateFirst(query, update, Patient.class);
  }

  /**
   * Adds or Updates patient demographic data.
   *
   * @param patients List of patients with reference and demographic details.
   * @return status of adding or modifying patients in database.
   */
  public FacadeResponse upsertPatients(List<Patient> patients) {
    MongoCollection<Document> collection = mongoTemplate.getCollection("patients");
    List<WriteModel<Document>> updates = new ArrayList<>();
    for (Patient patient : patients) {
      Document document =
          new Document()
              .append("abhaAddress", patient.getAbhaAddress())
              .append("name", patient.getName())
              .append("gender", patient.getGender())
              .append("dateOfBirth", patient.getDateOfBirth())
              .append("patientReference", patient.getPatientReference())
              .append("display", patient.getDisplay())
              .append("patientMobile", patient.getPatientMobile());
      updates.add(
          new UpdateOneModel<Document>(
              new Document("abhaAddress", patient.getAbhaAddress()),
              new Document("$set", document),
              new UpdateOptions().upsert(true)));
    }

    BulkWriteResult bulkWriteResult = collection.bulkWrite(updates);

    return FacadeResponse.builder()
        .message(
            String.format("Successfully upserted %d patients", bulkWriteResult.getUpserts().size()))
        .build();
  }
}
