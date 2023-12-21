package com.nha.abdm.wrapper.hrp.responseBody;

import lombok.Data;

@Data
public class OnInitResponse {
    String requestId;
    String timeStamp;
    String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getRequestId() {return requestId;}

    public void setRequestId(String requestId) {this.requestId = requestId;}

    public String getTimeStamp() {return timeStamp;}

    public void setTimeStamp(String timeStamp) {this.timeStamp = timeStamp;}


    public ConsentRequest getConsentRequest() {return consentRequest;}

    public void setConsentRequest(ConsentRequest consentRequest) {this.consentRequest = consentRequest;}

    public ConsentRequest consentRequest;
    public class ConsentRequest{
        public String getId() {return id;}

        public void setId(String id) {this.id = id;}

        public String id;

    }

    public Resp getResp() {return resp;}

    public void setResp(Resp resp) {this.resp = resp;}

    public Resp resp;
    public class Resp{
        public String getRequestId() {return requestId;}

        public void setRequestId(String requestId) {this.requestId = requestId;}

        public String requestId;

    }
    public String printData(){
        return "OnInit Call- Request ID :"+getRequestId()+" Error :"+getError()+"responseReqId"+getResp().getRequestId();
    }


}
