package com.nha.abdm.wrapper.hrp.repository;

import com.nha.abdm.wrapper.hrp.mongo.tables.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenManagementRepo extends MongoRepository<Token, String> {
    Token findByAbhaAddress(String abhaAddress);
}
