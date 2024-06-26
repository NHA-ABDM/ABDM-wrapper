/*
 * Swagger HIP Facade - OpenAPI 3.0
 * This is a set of interfaces based on the OpenAPI 3.0 specification for a wrapper client
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.nha.abdm.wrapper.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.nha.abdm.wrapper.client.model.PatientDetails;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.nha.abdm.wrapper.client.invoker.JSON;

/**
 * PatientProfile
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-04-22T00:27:32.411905600+05:30[Asia/Calcutta]")
public class PatientProfile {
  public static final String SERIALIZED_NAME_HIP_CODE = "hipCode";
  @SerializedName(SERIALIZED_NAME_HIP_CODE)
  private String hipCode;

  public static final String SERIALIZED_NAME_PATIENT = "patient";
  @SerializedName(SERIALIZED_NAME_PATIENT)
  private PatientDetails patient;

  public PatientProfile() {
  }

  public PatientProfile hipCode(String hipCode) {
    
    this.hipCode = hipCode;
    return this;
  }

   /**
   * The HIP code associated with the patient profile
   * @return hipCode
  **/
  @javax.annotation.Nullable
  public String getHipCode() {
    return hipCode;
  }


  public void setHipCode(String hipCode) {
    this.hipCode = hipCode;
  }


  public PatientProfile patient(PatientDetails patient) {
    
    this.patient = patient;
    return this;
  }

   /**
   * Get patient
   * @return patient
  **/
  @javax.annotation.Nullable
  public PatientDetails getPatient() {
    return patient;
  }


  public void setPatient(PatientDetails patient) {
    this.patient = patient;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PatientProfile patientProfile = (PatientProfile) o;
    return Objects.equals(this.hipCode, patientProfile.hipCode) &&
        Objects.equals(this.patient, patientProfile.patient);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hipCode, patient);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PatientProfile {\n");
    sb.append("    hipCode: ").append(toIndentedString(hipCode)).append("\n");
    sb.append("    patient: ").append(toIndentedString(patient)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("hipCode");
    openapiFields.add("patient");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to PatientProfile
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (!PatientProfile.openapiRequiredFields.isEmpty()) { // has required fields but JSON object is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in PatientProfile is not found in the empty JSON string", PatientProfile.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!PatientProfile.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `PatientProfile` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }
      if ((jsonObj.get("hipCode") != null && !jsonObj.get("hipCode").isJsonNull()) && !jsonObj.get("hipCode").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `hipCode` to be a primitive type in the JSON string but got `%s`", jsonObj.get("hipCode").toString()));
      }
      // validate the optional field `patient`
      if (jsonObj.get("patient") != null && !jsonObj.get("patient").isJsonNull()) {
        PatientDetails.validateJsonObject(jsonObj.getAsJsonObject("patient"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!PatientProfile.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'PatientProfile' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<PatientProfile> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(PatientProfile.class));

       return (TypeAdapter<T>) new TypeAdapter<PatientProfile>() {
           @Override
           public void write(JsonWriter out, PatientProfile value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public PatientProfile read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of PatientProfile given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of PatientProfile
  * @throws IOException if the JSON string is invalid with respect to PatientProfile
  */
  public static PatientProfile fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, PatientProfile.class);
  }

 /**
  * Convert an instance of PatientProfile to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

