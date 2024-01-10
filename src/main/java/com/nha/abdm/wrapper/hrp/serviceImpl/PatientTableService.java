package com.nha.abdm.wrapper.hrp.serviceImpl;

import com.nha.abdm.wrapper.hrp.CommonHelpers.CareContextBuilder;
import com.nha.abdm.wrapper.hrp.discoveryLinking.responses.DiscoverResponse;
import com.nha.abdm.wrapper.hrp.discoveryLinking.responses.InitResponse;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.AddPatient;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.LinkRecordsResponse;
import com.nha.abdm.wrapper.hrp.mongo.tables.Patients;
import com.nha.abdm.wrapper.hrp.repository.LogsRepo;
import com.nha.abdm.wrapper.hrp.repository.PatientRepo;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Document(collection = "patients")
@Service
public class PatientTableService {
    private static final Logger log = LogManager.getLogger(PatientTableService.class);
    @Autowired
    private final PatientRepo patientRepo;
    @Autowired
    private LogsRepo logsRepo;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    public PatientTableService(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }

    @Transactional
    public String addPatient(Object content) {
        if(content.getClass()== LinkRecordsResponse.class){
            LinkRecordsResponse data=(LinkRecordsResponse) content;
            String patientReference=data.getPatientReference();
            try{
                Patients existingRecord = this.patientRepo.findByPatientReference(patientReference);
                if (existingRecord == null) {
                    return null;
                } else {
                    Query query = new Query(Criteria.where("patientReference").is(data.getPatientReference()));
                    Update update = new Update().addToSet("careContext").each(data.getPatient().getCareContexts()) ;//TODO
                    this.mongoTemplate.updateFirst(query, update, Patients.class);
                }
            }catch(Exception e){
                log.info("addPatient :"+e);
            }
        }else if(content.getClass()== AddPatient.class){
            AddPatient data=(AddPatient)content;
            Patients existingRecord=patientRepo.findByAbhaAddress(data.getAbhaAddress());
            if(existingRecord==null){
                Patients newRecord=new Patients();
                newRecord.setName(data.getName());
                newRecord.setAbhaAddress(data.getAbhaAddress());
                newRecord.setPatientReference(data.getPatientReference());
                newRecord.setGender(data.getGender());
                newRecord.setDateOfBirth(data.getDateOfBirth());
                newRecord.setDisplay(data.getDisplay());
                newRecord.setPatientMobile(data.getPatientMobile());
                mongoTemplate.save(newRecord);
                log.info("Successfully Added Patient : "+data.toString());
                return  "Successfully Added Patient";
            }else{
                Update update = new Update().set("abhaAddress",data.getAbhaAddress())
                        .set("name",data.getName())
                        .set("gender",data.getGender())
                        .set("dateOfBirth",data.getDateOfBirth())
                        .set("display",data.getDisplay())
                        .set("patientReference",data.getPatientReference())
                        .set("patientMobile",data.getPatientMobile());
                Query query = new Query(Criteria.where("abhaAddress").is(data.getAbhaAddress()));
                mongoTemplate.updateFirst(query, update, Patients.class);
                log.info("Successfully Updated Patient : "+data.toString());
                return  "Successfully Updated Patient";
            }

        }
        return null;
    }

    public List<CareContextBuilder> getCareContexts(String abhaAddress, Object content) {
        if(Objects.nonNull(abhaAddress)){
            Patients existingRecord = this.patientRepo.findByAbhaAddress(abhaAddress);
            return existingRecord != null ? existingRecord.getCareContexts() : null;
        }
        if(content==DiscoverResponse.class){
            DiscoverResponse data=(DiscoverResponse) content;
            String patientIdentifier=null;
            if(data.getPatient().getUnverifiedIdentifiers()!=null) patientIdentifier=data.getPatient().getUnverifiedIdentifiers().get(0).getValue();
            String patientName=data.getPatient().getName();
            String patientMobile=data.getPatient().getVerifiedIdentifiers().get(0).getValue();
            if(Objects.nonNull(patientIdentifier)){
                Patients existingRecord = this.patientRepo.findByPatientReference(patientIdentifier);
                return existingRecord != null ? existingRecord.getCareContexts() : null;
            }
            if(Objects.nonNull(patientMobile)){
                List<Patients> existingRecord = this.patientRepo.findByPatientMobile(patientMobile);
                return existingRecord != null ? existingRecord.get(0).getCareContexts() : null;
            }
        }
        return null;
    }

    public String getPatientReference(String abhaAddress) {
        Patients existingRecord = this.patientRepo.findByAbhaAddress(abhaAddress);
        return existingRecord != null ? existingRecord.getPatientReference() : "";
    }

    public String getPatientDisplay(String abhaAddress) {
        Patients existingRecord = this.patientRepo.findByAbhaAddress(abhaAddress);
        return existingRecord != null ? existingRecord.getDisplay() : "";
    }
    public String getAbhaAddress(String patientReference){
        Patients existingRecord = this.patientRepo.findByPatientReference(patientReference);
        return existingRecord != null ? existingRecord.getAbhaAddress() : "";
    }

    public void updateCareContextStatus(String patientReference, List<CareContextBuilder> careContexts) {
        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Patients.class);

        for (CareContextBuilder updatedCareContext : careContexts) {
            Query query = Query.query(
                    Criteria.where("patientReference").is(patientReference)
                            .and("careContexts.referenceNumber").is(updatedCareContext.getReferenceNumber())
            );

            Update update = new Update().set("careContexts.$.isLinked", true);
            bulkOperations.updateOne(query, update);
        }

        bulkOperations.execute();
    }

    public boolean checkCareContexts(InitResponse data) {
        try {
            List<CareContextBuilder> patientCareContexts = patientRepo.findByPatientReference(data.getPatient().getReferenceNumber()).getCareContexts();
            Set<String> patientReferenceNumbers = patientCareContexts.stream()
                    .map(CareContextBuilder::getReferenceNumber)
                    .collect(Collectors.toSet());
            return data.getPatient().getCareContexts().stream()
                    .allMatch(responseContext -> patientReferenceNumbers.contains(responseContext.getReferenceNumber()));

        }catch (NullPointerException e){
            log.error("Init CareContext verify failed -> mismatch of careContexts" + e);
        }
        return true;
    }
}
