package com.nha.abdm.wrapper.hrp.serviceImpl;

import com.nha.abdm.wrapper.hrp.common.Utils;
import com.nha.abdm.wrapper.hrp.hipInitiatedLinking.responses.OnConfirmResponse;
import com.nha.abdm.wrapper.hrp.mongo.tables.RequestLogs;
import com.nha.abdm.wrapper.hrp.mongo.tables.Token;
import com.nha.abdm.wrapper.hrp.repository.TokenManagementRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TokenManagementService {
    @Autowired
    TokenManagementRepo tokenManagementRepo;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    Utils utils;
    private static final Logger log = LogManager.getLogger(TokenManagementService.class);

    public void setToken(String abhaAddress,OnConfirmResponse data) throws Exception {
        String token=data.getAuth().getAccessToken();
        Query query = new Query(Criteria.where("abhaAddress").is(abhaAddress));
        Token existingRecord = mongoTemplate.findOne(query, Token.class);
        if (existingRecord == null) {
            Token newRecord = new Token(abhaAddress,token,getExpiry(token));
            mongoTemplate.insert(newRecord);
        } else {
            Update update = (new Update()).set("x-token", data.getAuth().getAccessToken());
            mongoTemplate.updateFirst(query, update, RequestLogs.class);
        }
    }
    public Date getExpiry(String token) throws Exception {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getExpiresAt();
    }
    public String fetchToken(String abhaAddress) throws ParseException {
        Query query = new Query(Criteria.where("abhaAddress").is(abhaAddress));
        Token existingRecord = mongoTemplate.findOne(query, Token.class);
        if(existingRecord!=null){
            if((new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(utils.getCurrentTimeStamp()).compareTo(existingRecord.getExpiry())<0)) return existingRecord.getToken();
        }
        return null;
    }
}
