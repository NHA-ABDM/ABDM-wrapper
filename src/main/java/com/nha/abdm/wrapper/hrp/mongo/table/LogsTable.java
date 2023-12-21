package com.nha.abdm.wrapper.hrp.mongo.table;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document("LogsTable")
public class LogsTable {
    @Field("gatewayRequestId")
    public  String gatewayRequestId;

    public String getGatewayRequestId() {
        return gatewayRequestId;
    }

    public void setGatewayRequestId(String gatewayRequestId) {
        this.gatewayRequestId = gatewayRequestId;
    }

    public String getConsentId() {
        return consentId;
    }

    public void setConsntId(String consntId) {
        this.consentId = consntId;
    }

    public String getClientRequestId() {
        return clientRequestId;
    }

    public void setClientRequestId(String clientRequestId) {
        this.clientRequestId = clientRequestId;
    }

    @Field("consentId")
    public  String consentId;
    @Field("clientRequestId")
    public String clientRequestId;

    public String getRespsonseCode() {
        return respsonseCode;
    }

    public void setRespsonseCode(String respsonseCode) {
        this.respsonseCode = respsonseCode;
    }

    @Field("responseCode")
    public String respsonseCode;

    public LogsTable(String clientRequestId,String gatewayRequestId,String responseCode){
        this.clientRequestId=clientRequestId;
        this.gatewayRequestId=gatewayRequestId;
        this.consentId="";
        this.respsonseCode=responseCode;

    }
}
