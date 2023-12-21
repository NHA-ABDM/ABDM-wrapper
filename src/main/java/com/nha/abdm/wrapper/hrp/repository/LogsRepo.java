package com.nha.abdm.wrapper.hrp.repository;

import com.nha.abdm.wrapper.hrp.mongo.table.LogsTable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogsRepo extends MongoRepository<LogsTable,String> {
    public LogsTable findByClientRequestId(String clientRequestId);


}
