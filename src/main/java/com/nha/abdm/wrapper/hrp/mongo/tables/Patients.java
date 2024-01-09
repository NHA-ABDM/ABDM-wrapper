package com.nha.abdm.wrapper.hrp.mongo.tables;

import com.nha.abdm.wrapper.hrp.CommonHelpers.CareContextBuilder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;

@Data
@Document(collection = "patients")
public class Patients {

    @Field("abhaAddress")
    @Indexed(unique = true)
    public String abhaAddress;

    @Field("name")
    public String name;

    @Field("gender")
    public String gender;

    @Field("dateOfBirth")
    public String dateOfBirth;

    @Field("patientReference")
    public String patientReference;

    @Field("patientDisplay")
    public String display;
    @Field("patientMobile")
    public String patientMobile;

    @Field("careContext")
    public List<CareContextBuilder> careContexts;

    @Field("lastUpdated")
    public String lastUpdated;
    public Patients(String abhaAddress, String name, String gender, String dateOfBirth, String patientReference, String display, List<CareContextBuilder> careContexts, String lastUpdated) {
        this.abhaAddress=abhaAddress;
        this.name=name;
        this.gender=gender;
        this.dateOfBirth=dateOfBirth;
        this.patientReference = patientReference;
        this.display=display;
        this.careContexts=careContexts;
        this.lastUpdated=lastUpdated;
    }
    public Patients(){
    }

}
