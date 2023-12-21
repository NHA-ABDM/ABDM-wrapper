package com.nha.abdm.wrapper.hrp.responseBody;

import lombok.Data;

import java.util.List;
@Data
public class OnFetchBody {
    public String getRequestId() {return requestId;}

    public void setRequestId(String requestId) {this.requestId = requestId;}

    public String getTimestamp() {return timestamp;}

    public void setTimestamp(String timestamp) {this.timestamp = timestamp;}

    public String requestId;
   public String timestamp;

    public Constant getConstant() {return constant;}

    public void setConstant(Constant constant) {this.constant = constant;}

    public Constant constant;
   public static class Constant{
       public String getStatus() {return status;}

       public void setStatus(String status) {this.status = status;}

       public String status;

       public ConsentDetails getConsentDetails() {return consentDetails;}

       public void setConsentDetails(ConsentDetails consentDetails) {this.consentDetails = consentDetails;}

       public ConsentDetails consentDetails;
       public class ConsentDetails{
           public String consentId;

           public String getConsentId() {return consentId;}

           public void setConsentId(String consentId) {this.consentId = consentId;}

           public String getCreatedAt() {return createdAt;}

           public void setCreatedAt(String createdAt) {this.createdAt = createdAt;}

           public String getSchemaVersion() {return schemaVersion;}

           public void setSchemaVersion(String schemaVersion) {this.schemaVersion = schemaVersion;}

           public String createdAt;
           public String schemaVersion;

           public Patient getPatient() {return patient;}

           public void setPatient(Patient patient) {this.patient = patient;}

           public Patient patient;
           public class Patient{
               public String getId() {return id;}

               public void setId(String id) {this.id = id;}

               public String id;
           }
           public List<CareContext> careContexts;

           public List<CareContext> getCareContexts() {
               return careContexts;
           }

           public void setCareContexts(List<CareContext> careContexts) {
               this.careContexts = careContexts;
           }

           public class CareContext{
               public String getPatientReference() {
                   return patientReference;
               }

               public void setPatientReference(String patientReference) {
                   this.patientReference = patientReference;
               }

               public String getCareContextReference() {
                   return careContextReference;
               }

               public void setCareContextReference(String careContextReference) {
                   this.careContextReference = careContextReference;
               }

               String patientReference;
               String careContextReference;

           }

           //TODo


           public Purpose getPurpose() {return purpose;}

           public void setPurpose(Purpose purpose) {this.purpose = purpose;}

           public Purpose purpose;
           public class Purpose{
               public String getText() {return text;}

               public void setText(String text) {this.text = text;}

               public String getCode() {return code;}

               public void setCode(String code) {this.code = code;}

               public String getRefUri() {return refUri;}

               public void setRefUri(String refUri) {this.refUri = refUri;}

               public String text;
               public String code;
               public String refUri;
           }

           public Hip getHip() {return hip;}

           public void setHip(Hip hip) {this.hip = hip;}

           public Hip hip;
           public class Hip{
               public String getId() {return id;}

               public void setId(String id) {this.id = id;}

               public String id;
           }

           public Hiu getHiu() {return hiu;}

           public void setHiu(Hiu hiu) {this.hiu = hiu;}

           public Hiu hiu;
           public class Hiu{
               public String id;

               public String getId() {return id;}

               public void setId(String id) {this.id = id;}
           }

           public ConsentManager getConsentManager() {return consentManager;}

           public void setConsentManager(ConsentManager consentManager) {this.consentManager = consentManager;}

           public ConsentManager consentManager;
           public class ConsentManager{
               public String getId() {return id;}

               public void setId(String id) {this.id = id;}

               public String id;

           }

           public Requester getRequester() {
               return requester;
           }

           public void setRequester(Requester requester) {
               this.requester = requester;
           }

           public Requester requester;
           public class Requester{
               public String getName() {return name;}

               public void setName(String name) {this.name = name;}

               public String name;

               public Identifier getIdentifier() {
                   return identifier;
               }

               public void setIdentifier(Identifier identifier) {
                   this.identifier = identifier;
               }

               public Identifier identifier;
               public class Identifier{
                   public String getTypes() {
                       return types;
                   }

                   public void setTypes(String types) {
                       this.types = types;
                   }

                   public String getValue() {
                       return value;
                   }

                   public void setValue(String value) {
                       this.value = value;
                   }

                   public String getSystem() {
                       return system;
                   }

                   public void setSystem(String system) {
                       this.system = system;
                   }

                   public String types;
                   public String value;
                   public String system;
               }

           }
           public List<hiTypes> hiTypesList;

           public List<hiTypes> getHiTypesList() {
               return hiTypesList;
           }

           public void setHiTypesList(List<hiTypes> hiTypesList) {
               this.hiTypesList = hiTypesList;
           }

           public class hiTypes{
               String hiTypes;

               public String getHiTypes() {
                   return hiTypes;
               }

               public void setHiTypes(String hiTypes) {
                   this.hiTypes = hiTypes;
               }
           }

           public Permission getPermission() {return permission;}

           public void setPermission(Permission permission) {this.permission = permission;}


           public Permission permission;
           public class Permission{
               public String getAccessMode() {return accessMode;}

               public void setAccessMode(String accessMode) {this.accessMode = accessMode;}

               public String accessMode;
               public InitiateConsentResponse.Consent.Permission.DateRange dateRange;

               public InitiateConsentResponse.Consent.Permission.DateRange getDateRange() {
                   return dateRange;
               }

               public void setDateRange(InitiateConsentResponse.Consent.Permission.DateRange dateRange) {
                   this.dateRange = dateRange;
               }

               public String getDataEraseAt() {return dataEraseAt;}

               public void setDataEraseAt(String dataEraseAt) {this.dataEraseAt = dataEraseAt;}

               public String dataEraseAt;
               public InitiateConsentResponse.Consent.Permission.Frequency frequency;
           }
       }

       public String getSignature() {return signature;}

       public void setSignature(String signature) {this.signature = signature;}

       public String signature;
   }

    public Error getError() {return error;}

    public void setError(Error error) {
       this.error = error;}

    public Error error;
   public class Error{
       public Integer getCode() {return code;}

       public void setCode(Integer code) {this.code = code;}

       public String getMessage() {return message;}

       public void setMessage(String message) {this.message = message;}

       public Integer code;
       public String message;


   }

    public Resp getResp() {return resp;}

    public void setResp(Resp resp) {this.resp = resp;}

    public Resp resp;
   public class Resp{
       public String getRequestID() {return requestID;}

       public void setRequestID(String requestID) {this.requestID = requestID;}

       public String requestID;
   }

}
