package com.nha.abdm.wrapper.hrp.mongo.tables;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.HashMap;

@Data
@Document(collection = "request-logs")
public class RequestLogs {
    @Field("clientRequestId")
    public String clientRequestId;

    @Field("gatewayRequestId1")
    public String gatewayRequestId1;

    @Field("gatewayRequestId2")
    public String gatewayRequestId2;

    @Field("response")
    public String response;

    @Field("transactionId")
    public String transactionId;

    @Field("linkRefNumber")
    public String linkRefNumber;

    @Field("abhaAddress")
    public String abhaAddress;

    @Field("rawResponse")
    public HashMap<String, Object> rawResponse;
    public RequestLogs(String clientRequestId, String gatewayRequestId2, String abhaAddress, String transactionId){
        this.clientRequestId=clientRequestId;
        this.gatewayRequestId2=gatewayRequestId2;
        this.abhaAddress=abhaAddress;
        this.transactionId=transactionId;
        this.response=response;
        this.rawResponse =new HashMap<>();
    }
    public RequestLogs(){

    }

}
