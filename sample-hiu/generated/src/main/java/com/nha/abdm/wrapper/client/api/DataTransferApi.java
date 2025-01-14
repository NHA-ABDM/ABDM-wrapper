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


package in.nha.abdm.wrapper.client.api;

import in.nha.abdm.wrapper.client.invoker.ApiCallback;
import in.nha.abdm.wrapper.client.invoker.ApiClient;
import in.nha.abdm.wrapper.client.invoker.ApiException;
import in.nha.abdm.wrapper.client.invoker.ApiResponse;
import in.nha.abdm.wrapper.client.invoker.Configuration;
import in.nha.abdm.wrapper.client.invoker.Pair;
import in.nha.abdm.wrapper.client.invoker.ProgressRequestBody;
import in.nha.abdm.wrapper.client.invoker.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import in.nha.abdm.wrapper.client.model.FacadeResponse;
import in.nha.abdm.wrapper.client.model.HIUClientHealthInformationRequest;
import in.nha.abdm.wrapper.client.model.HealthInformationResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;

public class DataTransferApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public DataTransferApi() {
        this(Configuration.getDefaultApiClient());
    }

    public DataTransferApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public int getHostIndex() {
        return localHostIndex;
    }

    public void setHostIndex(int hostIndex) {
        this.localHostIndex = hostIndex;
    }

    public String getCustomBaseUrl() {
        return localCustomBaseUrl;
    }

    public void setCustomBaseUrl(String customBaseUrl) {
        this.localCustomBaseUrl = customBaseUrl;
    }

    /**
     * Build call for fetchHealthInformation
     * @param hiUClientHealthInformationRequest  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 202 </td><td> Request Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request body supplied </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Address not found </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Validation exception </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call fetchHealthInformationCall(HIUClientHealthInformationRequest hiUClientHealthInformationRequest, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = hiUClientHealthInformationRequest;

        // create path and map variables
        String localVarPath = "/health-information/fetch-records";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call fetchHealthInformationValidateBeforeCall(HIUClientHealthInformationRequest hiUClientHealthInformationRequest, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'hiUClientHealthInformationRequest' is set
        if (hiUClientHealthInformationRequest == null) {
            throw new ApiException("Missing the required parameter 'hiUClientHealthInformationRequest' when calling fetchHealthInformation(Async)");
        }

        return fetchHealthInformationCall(hiUClientHealthInformationRequest, _callback);

    }

    /**
     * Submits a request to fetch health information
     * Submits a request to fetch health information
     * @param hiUClientHealthInformationRequest  (required)
     * @return FacadeResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 202 </td><td> Request Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request body supplied </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Address not found </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Validation exception </td><td>  -  </td></tr>
     </table>
     */
    public FacadeResponse fetchHealthInformation(HIUClientHealthInformationRequest hiUClientHealthInformationRequest) throws ApiException {
        ApiResponse<FacadeResponse> localVarResp = fetchHealthInformationWithHttpInfo(hiUClientHealthInformationRequest);
        return localVarResp.getData();
    }

    /**
     * Submits a request to fetch health information
     * Submits a request to fetch health information
     * @param hiUClientHealthInformationRequest  (required)
     * @return ApiResponse&lt;FacadeResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 202 </td><td> Request Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request body supplied </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Address not found </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Validation exception </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<FacadeResponse> fetchHealthInformationWithHttpInfo(HIUClientHealthInformationRequest hiUClientHealthInformationRequest) throws ApiException {
        okhttp3.Call localVarCall = fetchHealthInformationValidateBeforeCall(hiUClientHealthInformationRequest, null);
        Type localVarReturnType = new TypeToken<FacadeResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Submits a request to fetch health information (asynchronously)
     * Submits a request to fetch health information
     * @param hiUClientHealthInformationRequest  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 202 </td><td> Request Accepted </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request body supplied </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Address not found </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Validation exception </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call fetchHealthInformationAsync(HIUClientHealthInformationRequest hiUClientHealthInformationRequest, final ApiCallback<FacadeResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = fetchHealthInformationValidateBeforeCall(hiUClientHealthInformationRequest, _callback);
        Type localVarReturnType = new TypeToken<FacadeResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for healthInformationStatusRequestIdGet
     * @param requestId Request Id of the health information request. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request body supplied </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Address not found </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Validation exception </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call healthInformationStatusRequestIdGetCall(String requestId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/health-information/status/{requestId}"
            .replace("{" + "requestId" + "}", localVarApiClient.escapeString(requestId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call healthInformationStatusRequestIdGetValidateBeforeCall(String requestId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'requestId' is set
        if (requestId == null) {
            throw new ApiException("Missing the required parameter 'requestId' when calling healthInformationStatusRequestIdGet(Async)");
        }

        return healthInformationStatusRequestIdGetCall(requestId, _callback);

    }

    /**
     * Get status of Health Information request.
     * 
     * @param requestId Request Id of the health information request. (required)
     * @return HealthInformationResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request body supplied </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Address not found </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Validation exception </td><td>  -  </td></tr>
     </table>
     */
    public HealthInformationResponse healthInformationStatusRequestIdGet(String requestId) throws ApiException {
        ApiResponse<HealthInformationResponse> localVarResp = healthInformationStatusRequestIdGetWithHttpInfo(requestId);
        return localVarResp.getData();
    }

    /**
     * Get status of Health Information request.
     * 
     * @param requestId Request Id of the health information request. (required)
     * @return ApiResponse&lt;HealthInformationResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request body supplied </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Address not found </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Validation exception </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<HealthInformationResponse> healthInformationStatusRequestIdGetWithHttpInfo(String requestId) throws ApiException {
        okhttp3.Call localVarCall = healthInformationStatusRequestIdGetValidateBeforeCall(requestId, null);
        Type localVarReturnType = new TypeToken<HealthInformationResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get status of Health Information request. (asynchronously)
     * 
     * @param requestId Request Id of the health information request. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Invalid request body supplied </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Address not found </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Validation exception </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call healthInformationStatusRequestIdGetAsync(String requestId, final ApiCallback<HealthInformationResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = healthInformationStatusRequestIdGetValidateBeforeCall(requestId, _callback);
        Type localVarReturnType = new TypeToken<HealthInformationResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
