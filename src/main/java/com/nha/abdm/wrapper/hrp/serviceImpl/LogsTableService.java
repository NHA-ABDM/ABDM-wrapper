package com.nha.abdm.wrapper.hrp.serviceImpl;

import com.nha.abdm.wrapper.hrp.responseBody.OnInitResponse;
import com.nha.abdm.wrapper.hrp.mongo.table.LogsTable;
import com.nha.abdm.wrapper.hrp.repository.LogsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class LogsTableService {
    @Autowired
    LogsRepo logsRepo;

    @Autowired
    MongoTemplate mongoTemplate;

    public void setRequestId(String clientRequestId, String gatewayRequestId, String statusCode) {
        LogsTable existingRecord= logsRepo.findByClientRequestId(clientRequestId);
        if(existingRecord==null){
            LogsTable newRecord=new LogsTable(clientRequestId,gatewayRequestId,statusCode);
            logsRepo.save(newRecord);
        }else{
            existingRecord.setClientRequestId(clientRequestId);
            existingRecord.setGatewayRequestId(gatewayRequestId);
            existingRecord.setRespsonseCode(statusCode);
            logsRepo.save(existingRecord);
        }
    }

    public void setConsentId(OnInitResponse data) {
        Query query=new Query(Criteria.where("clientRequestId").is(data.getResp().getRequestId()));
        Update update=new Update().set("consentId",data.getConsentRequest().getId());
        mongoTemplate.findAndModify(query,update,LogsTable.class);

    }
}
