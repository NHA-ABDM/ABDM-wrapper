/* (C) 2024 */
package com.nha.abdm.wrapper.hip.hrp.discover;

import com.nha.abdm.wrapper.common.RequestManager;
import com.nha.abdm.wrapper.common.Utils;
import com.nha.abdm.wrapper.common.models.CareContext;
import com.nha.abdm.wrapper.common.models.RespRequest;
import com.nha.abdm.wrapper.common.responses.ErrorResponse;
import com.nha.abdm.wrapper.hip.hrp.common.requests.CareContextRequest;
import com.nha.abdm.wrapper.hip.hrp.database.mongo.repositories.PatientRepo;
import com.nha.abdm.wrapper.hip.hrp.database.mongo.services.RequestLogService;
import com.nha.abdm.wrapper.hip.hrp.database.mongo.tables.Patient;
import com.nha.abdm.wrapper.hip.hrp.discover.requests.*;
import com.nha.abdm.wrapper.hip.hrp.discover.responses.DiscoverResponse;
import com.nha.abdm.wrapper.hip.hrp.link.hipInitiated.responses.GatewayGenericResponse;
import info.debatty.java.stringsimilarity.JaroWinkler;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DiscoveryService implements DiscoveryInterface {

  @Autowired PatientRepo patientRepo;
  private final RequestManager requestManager;
  @Autowired RequestLogService requestLogService;
  ErrorResponse errorResponse = new ErrorResponse();
  JaroWinkler jaroWinkler = new JaroWinkler();

  @Value("${onDiscoverPath}")
  public String onDiscoverPath;

  @Autowired
  public DiscoveryService(RequestManager requestManager) {
    this.requestManager = requestManager;
  }

  private static final Logger log = LogManager.getLogger(DiscoveryService.class);

  /**
   * <B>discovery</B>
   *
   * <p>Using the demographic details and abhaAddress fetching careContexts from db.<br>
   * Logic ->step 1: Check for AbhaAddress, if present build discoverRequest and make POST
   * /discover.<br>
   * step 2: fetch list of users with mobileNumber, then check patientIdentifier if present, then
   * return careContexts.<br>
   * if patientIdentifier present and not matched return null/not found.<br>
   * if patientIdentifier not present check for gender, then +-5 years in Year of birth, then name
   * with fuzzy logic, if any of the above demographics fail to match return null/ not matched.<br>
   * build discoverRequest and make POST /on-discover.
   *
   * @param discoverResponse Response from ABDM gateway with patient demographic details and
   *     abhaAddress.
   */
  @Override
  public void onDiscover(DiscoverResponse discoverResponse) {
    String receivedAbhaAddress = discoverResponse.getPatient().getId();
    String receivedPatientReference =
        discoverResponse.getPatient().getUnverifiedIdentifiers().isEmpty()
            ? null
            : discoverResponse.getPatient().getUnverifiedIdentifiers().get(0).getValue();
    String receivedYob = discoverResponse.getPatient().getYearOfBirth();
    String receivedGender = discoverResponse.getPatient().getGender();
    String receivedName = discoverResponse.getPatient().getName();

    try {
      Patient patientByAbhaAddress = patientRepo.findByAbhaAddress(receivedAbhaAddress);
      List<Patient> patientsByMobileNumber =
          patientRepo.findByPatientMobile(
              discoverResponse.getPatient().getVerifiedIdentifiers().get(0).getValue());
      Patient patientByReference =
          receivedPatientReference != null
              ? patientRepo.findByPatientReference(receivedPatientReference)
              : null;
      if (Objects.nonNull(patientByAbhaAddress)) {
        log.info("Patient matched with AbhaAddress");
        List<CareContext> careContexts =
            patientByAbhaAddress.getCareContexts().stream()
                .filter(context -> !context.isLinked())
                .collect(Collectors.toList());
        onDiscoverRequest(discoverResponse, patientByAbhaAddress, careContexts);
      } else if (patientsByMobileNumber != null) {
        log.info("patient matched with mobile");
        Optional<Patient> matchingPatient =
            findMatchingPatient(
                patientsByMobileNumber,
                receivedPatientReference,
                receivedGender,
                receivedYob,
                receivedName);
        if (matchingPatient.isPresent()) {
          onDiscoverRequest(
              discoverResponse, matchingPatient.get(), matchingPatient.get().getCareContexts());
        } else {
          errorResponse.setCode(1000);
          errorResponse.setMessage("HIP -> Details mismatch with mobile");
          onDiscoverNoPatientRequest(discoverResponse, errorResponse);
        }
      } else if (Objects.nonNull(patientByReference)) {
        log.info("Patient matched with Patient Identifier");
        if (isGenderMatch(patientByReference, receivedGender)
            && (isYOBInRange(patientByReference, receivedYob)
                && (isFuzzyNameMatch(patientByReference, receivedName)))) {
          List<CareContext> careContexts =
              patientByReference.getCareContexts().stream()
                  .filter(context -> !context.isLinked())
                  .collect(Collectors.toList());
          onDiscoverRequest(discoverResponse, patientByReference, careContexts);
        } else {
          errorResponse.setCode(1000);
          errorResponse.setMessage("HIP : Details mismatch with patientIdentifier");
          onDiscoverNoPatientRequest(discoverResponse, errorResponse);
        }
      } else {
        errorResponse.setCode(1000);
        errorResponse.setMessage("HIP -> Patient Not found");
        onDiscoverNoPatientRequest(discoverResponse, errorResponse);
      }
    } catch (Exception e) {
      log.error("OnDiscover : " + Arrays.toString(e.getStackTrace()));
    }
  }

  /**
   * Logic to match demographic details with response demographic details.
   *
   * @param isMobilePresent list of patients with same mobileNumber example: family.
   * @param receivedPatientReference Response : patientReference.
   * @param receivedGender Response : gender.
   * @param receivedYob Response : Year Of Birth.
   * @param receivedName Response : name of the patient.
   * @return Returns the matched patient with demographic details.
   */
  private Optional<Patient> findMatchingPatient(
      List<Patient> isMobilePresent,
      String receivedPatientReference,
      String receivedGender,
      String receivedYob,
      String receivedName) {
    if (receivedPatientReference != null) {
      return isMobilePresent.stream()
          .filter(patient -> receivedPatientReference.equals(patient.getPatientReference()))
          .findFirst();
    } else {
      return isMobilePresent.stream()
          .filter(patient -> isGenderMatch(patient, receivedGender))
          .filter(patient -> isYOBInRange(patient, receivedYob))
          .filter(patient -> isFuzzyNameMatch(patient, receivedName))
          .findFirst();
    }
  }

  /**
   * <B>Discovery</B>
   *
   * <p>Build the body with the respective careContexts into onDiscoverRequest.
   *
   * @param discoverResponse Response from ABDM gateway.
   * @param patient Particular patient record.
   * @param careContexts list of non-linked careContexts.
   */
  private void onDiscoverRequest(
      DiscoverResponse discoverResponse, Patient patient, List<CareContext> careContexts) {

    List<CareContextRequest> careContextList = new ArrayList<>();
    for (CareContext careContext : careContexts) {
      careContextList.add(
          CareContextRequest.builder()
              .referenceNumber(careContext.getReferenceNumber())
              .display(careContext.getDisplay())
              .build());
    }

    OnDiscoverPatient onDiscoverPatient =
        OnDiscoverPatient.builder()
            .referenceNumber(patient.getPatientReference())
            .display(patient.getDisplay())
            .careContexts(careContextList)
            .matchedBy(Arrays.asList("MOBILE"))
            .build();
    OnDiscoverRequest onDiscoverRequest =
        OnDiscoverRequest.builder()
            .requestId(UUID.randomUUID().toString())
            .timestamp(Utils.getCurrentTimeStamp().toString())
            .transactionId(discoverResponse.getTransactionId())
            .patient(onDiscoverPatient)
            .resp(RespRequest.builder().requestId(discoverResponse.getRequestId()).build())
            .build();
    log.info("onDiscover : " + onDiscoverRequest.toString());
    try {
      ResponseEntity<GatewayGenericResponse> responseEntity =
          requestManager.fetchResponseFromGateway(onDiscoverPath, onDiscoverRequest);
      log.info(onDiscoverPath + " : onDiscoverCall: " + responseEntity.getStatusCode());
      requestLogService.setDiscoverResponse(discoverResponse);
    } catch (Exception e) {
      log.info("Error: " + e);
    }
  }

  /**
   * <B>discovery</B>
   *
   * <p>build of onDiscoverRequest with error when patient not found.
   *
   * @param discoverResponse Response from ABDM gateway.
   * @param errorResponse The respective error message while matching patient data.
   */
  private void onDiscoverNoPatientRequest(
      DiscoverResponse discoverResponse, ErrorResponse errorResponse) {

    OnDiscoverErrorRequest onDiscoverErrorRequest =
        OnDiscoverErrorRequest.builder()
            .requestId(UUID.randomUUID().toString())
            .timestamp(Utils.getCurrentTimeStamp().toString())
            .transactionId(discoverResponse.getTransactionId())
            .resp(RespRequest.builder().requestId(discoverResponse.getRequestId()).build())
            .error(errorResponse)
            .build();
    log.info("onDiscover : " + onDiscoverErrorRequest.toString());
    try {
      requestManager.fetchResponseFromGateway(onDiscoverPath, onDiscoverErrorRequest);
      log.info(
          onDiscoverPath
              + " Discover: requestId : "
              + discoverResponse.getRequestId()
              + ": Patient not found");
    } catch (Exception e) {
      log.error(e);
    }
  }

  /**
   * <B>discovery</B> Check of Year Of Birth in the range of +-5 years
   *
   * @param patient Particular patient record.
   * @param receivedYob Response : Year Of Birth.
   * @return true if in range else false
   */
  private boolean isYOBInRange(Patient patient, String receivedYob) {
    int existingDate = Integer.parseInt(patient.getDateOfBirth().substring(0, 4));
    return Math.abs(existingDate - Integer.parseInt(receivedYob)) <= 5;
  }

  /**
   * <B>discovery</B> Check of gender match with response gender.
   *
   * @param patient particular patient record.
   * @param receivedGender Response : gender.
   * @return true if gender matches or else false.
   */
  private boolean isGenderMatch(Patient patient, String receivedGender) {
    return receivedGender.equals(patient.getGender());
  }

  /**
   * <B>discovery</B> Matching of patient name with response name by jaroWinkler algorithm making
   * 0.5 a reasonable validation.
   *
   * @param patient particular patient record.
   * @param receivedName Response : name.
   * @return true if name matches or else false.
   */
  private boolean isFuzzyNameMatch(Patient patient, String receivedName) {
    return jaroWinkler.similarity(patient.getName(), receivedName) >= 0.5;
  }
}
