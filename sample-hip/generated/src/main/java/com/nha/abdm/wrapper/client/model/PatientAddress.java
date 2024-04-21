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
 * PatientAddress
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-04-21T21:17:13.008114500+05:30[Asia/Calcutta]")
public class PatientAddress {
  public static final String SERIALIZED_NAME_LINE = "line";
  @SerializedName(SERIALIZED_NAME_LINE)
  private String line;

  public static final String SERIALIZED_NAME_DISTRICT = "district";
  @SerializedName(SERIALIZED_NAME_DISTRICT)
  private String district;

  public static final String SERIALIZED_NAME_STATE = "state";
  @SerializedName(SERIALIZED_NAME_STATE)
  private String state;

  public static final String SERIALIZED_NAME_PINCODE = "pincode";
  @SerializedName(SERIALIZED_NAME_PINCODE)
  private String pincode;

  public PatientAddress() {
  }

  public PatientAddress line(String line) {
    
    this.line = line;
    return this;
  }

   /**
   * The line of the patient address
   * @return line
  **/
  @javax.annotation.Nullable
  public String getLine() {
    return line;
  }


  public void setLine(String line) {
    this.line = line;
  }


  public PatientAddress district(String district) {
    
    this.district = district;
    return this;
  }

   /**
   * The district of the patient address
   * @return district
  **/
  @javax.annotation.Nullable
  public String getDistrict() {
    return district;
  }


  public void setDistrict(String district) {
    this.district = district;
  }


  public PatientAddress state(String state) {
    
    this.state = state;
    return this;
  }

   /**
   * The state of the patient address
   * @return state
  **/
  @javax.annotation.Nullable
  public String getState() {
    return state;
  }


  public void setState(String state) {
    this.state = state;
  }


  public PatientAddress pincode(String pincode) {
    
    this.pincode = pincode;
    return this;
  }

   /**
   * The pincode of the patient address
   * @return pincode
  **/
  @javax.annotation.Nullable
  public String getPincode() {
    return pincode;
  }


  public void setPincode(String pincode) {
    this.pincode = pincode;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PatientAddress patientAddress = (PatientAddress) o;
    return Objects.equals(this.line, patientAddress.line) &&
        Objects.equals(this.district, patientAddress.district) &&
        Objects.equals(this.state, patientAddress.state) &&
        Objects.equals(this.pincode, patientAddress.pincode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(line, district, state, pincode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PatientAddress {\n");
    sb.append("    line: ").append(toIndentedString(line)).append("\n");
    sb.append("    district: ").append(toIndentedString(district)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    pincode: ").append(toIndentedString(pincode)).append("\n");
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
    openapiFields.add("line");
    openapiFields.add("district");
    openapiFields.add("state");
    openapiFields.add("pincode");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to PatientAddress
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (!PatientAddress.openapiRequiredFields.isEmpty()) { // has required fields but JSON object is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in PatientAddress is not found in the empty JSON string", PatientAddress.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!PatientAddress.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `PatientAddress` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }
      if ((jsonObj.get("line") != null && !jsonObj.get("line").isJsonNull()) && !jsonObj.get("line").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `line` to be a primitive type in the JSON string but got `%s`", jsonObj.get("line").toString()));
      }
      if ((jsonObj.get("district") != null && !jsonObj.get("district").isJsonNull()) && !jsonObj.get("district").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `district` to be a primitive type in the JSON string but got `%s`", jsonObj.get("district").toString()));
      }
      if ((jsonObj.get("state") != null && !jsonObj.get("state").isJsonNull()) && !jsonObj.get("state").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `state` to be a primitive type in the JSON string but got `%s`", jsonObj.get("state").toString()));
      }
      if ((jsonObj.get("pincode") != null && !jsonObj.get("pincode").isJsonNull()) && !jsonObj.get("pincode").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `pincode` to be a primitive type in the JSON string but got `%s`", jsonObj.get("pincode").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!PatientAddress.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'PatientAddress' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<PatientAddress> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(PatientAddress.class));

       return (TypeAdapter<T>) new TypeAdapter<PatientAddress>() {
           @Override
           public void write(JsonWriter out, PatientAddress value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public PatientAddress read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of PatientAddress given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of PatientAddress
  * @throws IOException if the JSON string is invalid with respect to PatientAddress
  */
  public static PatientAddress fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, PatientAddress.class);
  }

 /**
  * Convert an instance of PatientAddress to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

