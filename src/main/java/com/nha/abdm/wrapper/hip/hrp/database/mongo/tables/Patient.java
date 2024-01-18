/* (C) 2024 */
package com.nha.abdm.wrapper.hip.hrp.database.mongo.tables;

import com.nha.abdm.wrapper.common.models.CareContext;
import java.util.List;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "patients")
public class Patient {

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
  public List<CareContext> careContexts;

  @Field("lastUpdated")
  public String lastUpdated;

  public Patient(
      String abhaAddress,
      String name,
      String gender,
      String dateOfBirth,
      String patientReference,
      String display,
      List<CareContext> careContexts,
      String lastUpdated) {
    this.abhaAddress = abhaAddress;
    this.name = name;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.patientReference = patientReference;
    this.display = display;
    this.careContexts = careContexts;
    this.lastUpdated = lastUpdated;
  }

  public Patient() {}
}