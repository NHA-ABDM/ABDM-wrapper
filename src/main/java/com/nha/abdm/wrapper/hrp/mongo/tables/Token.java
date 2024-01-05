package com.nha.abdm.wrapper.hrp.mongo.tables;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = "tokens")
public class Token {
    @Field("abhaAddress")
    @Indexed(unique = true)
    public String abhaAddress;
    @Field("x-token")
    public String token;

    @Field("expiry")
    public Date expiry;
    public Token(String abhaAddress, String token, Date expiry){
        this.abhaAddress=abhaAddress;
        this.token=token;
        this.expiry=expiry;

    }
}
