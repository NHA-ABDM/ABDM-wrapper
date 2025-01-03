/*
 * Swagger HIU Facade - OpenAPI 3.0
 * This is a set of interfaces based on the OpenAPI 3.0 specification for a wrapper client
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package in.nha.abdm.wrapper.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import in.nha.abdm.wrapper.client.model.ConsentStatusConsentDetailsConsentInner;
import in.nha.abdm.wrapper.client.model.DateRange;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import in.nha.abdm.wrapper.client.invoker.JSON;

/**
 * ConsentStatusConsentDetails
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-04-21T21:07:52.071456600+05:30[Asia/Calcutta]")
public class ConsentStatusConsentDetails {
  public static final String SERIALIZED_NAME_GRANTED_ON = "grantedOn";
  @SerializedName(SERIALIZED_NAME_GRANTED_ON)
  private String grantedOn;

  public static final String SERIALIZED_NAME_DATE_RANGE = "dateRange";
  @SerializedName(SERIALIZED_NAME_DATE_RANGE)
  private DateRange dateRange;

  public static final String SERIALIZED_NAME_DATA_ERASE_AT = "dataEraseAt";
  @SerializedName(SERIALIZED_NAME_DATA_ERASE_AT)
  private String dataEraseAt;

  public static final String SERIALIZED_NAME_HI_TYPES = "hiTypes";
  @SerializedName(SERIALIZED_NAME_HI_TYPES)
  private List<String> hiTypes;

  public static final String SERIALIZED_NAME_CONSENT = "consent";
  @SerializedName(SERIALIZED_NAME_CONSENT)
  private List<ConsentStatusConsentDetailsConsentInner> consent;

  public ConsentStatusConsentDetails() {
  }

  public ConsentStatusConsentDetails grantedOn(String grantedOn) {
    
    this.grantedOn = grantedOn;
    return this;
  }

   /**
   * Get grantedOn
   * @return grantedOn
  **/
  @javax.annotation.Nullable
  public String getGrantedOn() {
    return grantedOn;
  }


  public void setGrantedOn(String grantedOn) {
    this.grantedOn = grantedOn;
  }


  public ConsentStatusConsentDetails dateRange(DateRange dateRange) {
    
    this.dateRange = dateRange;
    return this;
  }

   /**
   * Get dateRange
   * @return dateRange
  **/
  @javax.annotation.Nullable
  public DateRange getDateRange() {
    return dateRange;
  }


  public void setDateRange(DateRange dateRange) {
    this.dateRange = dateRange;
  }


  public ConsentStatusConsentDetails dataEraseAt(String dataEraseAt) {
    
    this.dataEraseAt = dataEraseAt;
    return this;
  }

   /**
   * Get dataEraseAt
   * @return dataEraseAt
  **/
  @javax.annotation.Nullable
  public String getDataEraseAt() {
    return dataEraseAt;
  }


  public void setDataEraseAt(String dataEraseAt) {
    this.dataEraseAt = dataEraseAt;
  }


  public ConsentStatusConsentDetails hiTypes(List<String> hiTypes) {
    
    this.hiTypes = hiTypes;
    return this;
  }

  public ConsentStatusConsentDetails addHiTypesItem(String hiTypesItem) {
    if (this.hiTypes == null) {
      this.hiTypes = new ArrayList<>();
    }
    this.hiTypes.add(hiTypesItem);
    return this;
  }

   /**
   * Get hiTypes
   * @return hiTypes
  **/
  @javax.annotation.Nullable
  public List<String> getHiTypes() {
    return hiTypes;
  }


  public void setHiTypes(List<String> hiTypes) {
    this.hiTypes = hiTypes;
  }


  public ConsentStatusConsentDetails consent(List<ConsentStatusConsentDetailsConsentInner> consent) {
    
    this.consent = consent;
    return this;
  }

  public ConsentStatusConsentDetails addConsentItem(ConsentStatusConsentDetailsConsentInner consentItem) {
    if (this.consent == null) {
      this.consent = new ArrayList<>();
    }
    this.consent.add(consentItem);
    return this;
  }

   /**
   * Get consent
   * @return consent
  **/
  @javax.annotation.Nullable
  public List<ConsentStatusConsentDetailsConsentInner> getConsent() {
    return consent;
  }


  public void setConsent(List<ConsentStatusConsentDetailsConsentInner> consent) {
    this.consent = consent;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConsentStatusConsentDetails consentStatusConsentDetails = (ConsentStatusConsentDetails) o;
    return Objects.equals(this.grantedOn, consentStatusConsentDetails.grantedOn) &&
        Objects.equals(this.dateRange, consentStatusConsentDetails.dateRange) &&
        Objects.equals(this.dataEraseAt, consentStatusConsentDetails.dataEraseAt) &&
        Objects.equals(this.hiTypes, consentStatusConsentDetails.hiTypes) &&
        Objects.equals(this.consent, consentStatusConsentDetails.consent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(grantedOn, dateRange, dataEraseAt, hiTypes, consent);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConsentStatusConsentDetails {\n");
    sb.append("    grantedOn: ").append(toIndentedString(grantedOn)).append("\n");
    sb.append("    dateRange: ").append(toIndentedString(dateRange)).append("\n");
    sb.append("    dataEraseAt: ").append(toIndentedString(dataEraseAt)).append("\n");
    sb.append("    hiTypes: ").append(toIndentedString(hiTypes)).append("\n");
    sb.append("    consent: ").append(toIndentedString(consent)).append("\n");
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
    openapiFields.add("grantedOn");
    openapiFields.add("dateRange");
    openapiFields.add("dataEraseAt");
    openapiFields.add("hiTypes");
    openapiFields.add("consent");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to ConsentStatusConsentDetails
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (!ConsentStatusConsentDetails.openapiRequiredFields.isEmpty()) { // has required fields but JSON object is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ConsentStatusConsentDetails is not found in the empty JSON string", ConsentStatusConsentDetails.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!ConsentStatusConsentDetails.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `ConsentStatusConsentDetails` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }
      if ((jsonObj.get("grantedOn") != null && !jsonObj.get("grantedOn").isJsonNull()) && !jsonObj.get("grantedOn").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `grantedOn` to be a primitive type in the JSON string but got `%s`", jsonObj.get("grantedOn").toString()));
      }
      // validate the optional field `dateRange`
      if (jsonObj.get("dateRange") != null && !jsonObj.get("dateRange").isJsonNull()) {
        DateRange.validateJsonObject(jsonObj.getAsJsonObject("dateRange"));
      }
      if ((jsonObj.get("dataEraseAt") != null && !jsonObj.get("dataEraseAt").isJsonNull()) && !jsonObj.get("dataEraseAt").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `dataEraseAt` to be a primitive type in the JSON string but got `%s`", jsonObj.get("dataEraseAt").toString()));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("hiTypes") != null && !jsonObj.get("hiTypes").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `hiTypes` to be an array in the JSON string but got `%s`", jsonObj.get("hiTypes").toString()));
      }
      if (jsonObj.get("consent") != null && !jsonObj.get("consent").isJsonNull()) {
        JsonArray jsonArrayconsent = jsonObj.getAsJsonArray("consent");
        if (jsonArrayconsent != null) {
          // ensure the json data is an array
          if (!jsonObj.get("consent").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `consent` to be an array in the JSON string but got `%s`", jsonObj.get("consent").toString()));
          }

          // validate the optional field `consent` (array)
          for (int i = 0; i < jsonArrayconsent.size(); i++) {
            ConsentStatusConsentDetailsConsentInner.validateJsonObject(jsonArrayconsent.get(i).getAsJsonObject());
          };
        }
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ConsentStatusConsentDetails.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ConsentStatusConsentDetails' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ConsentStatusConsentDetails> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ConsentStatusConsentDetails.class));

       return (TypeAdapter<T>) new TypeAdapter<ConsentStatusConsentDetails>() {
           @Override
           public void write(JsonWriter out, ConsentStatusConsentDetails value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public ConsentStatusConsentDetails read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of ConsentStatusConsentDetails given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ConsentStatusConsentDetails
  * @throws IOException if the JSON string is invalid with respect to ConsentStatusConsentDetails
  */
  public static ConsentStatusConsentDetails fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ConsentStatusConsentDetails.class);
  }

 /**
  * Convert an instance of ConsentStatusConsentDetails to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

