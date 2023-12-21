package com.nha.abdm.wrapper.hrp.responseBody;

import java.util.List;

public class NotifyResponse {
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String timestamp;
   public String requestId;
   public Notification notification;

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public static class Notification{

       public String getConsentRequestId() {
           return consentRequestId;
       }

       public void setConsentRequestId(String consentRequestId) {
           this.consentRequestId = consentRequestId;
       }

       public String consentRequestId;

       public String getStatus() {
           return status;
       }

       public void setStatus(String status) {
           this.status = status;
       }

       public String status;

       public List<ConsentArtefacts> getConsentArtefacts() {
           return consentArtefacts;
       }

       public void setConsentArtefacts(List<ConsentArtefacts> consentArtefacts) {
           this.consentArtefacts = consentArtefacts;
       }

       public  List<ConsentArtefacts> consentArtefacts;
        public static class ConsentArtefacts{
            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String id;

        }



   }


}
