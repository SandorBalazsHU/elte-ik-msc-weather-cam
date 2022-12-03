# StationsApi

All URIs are relative to *https://api.weather.s-b-x.com/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getMeasurementStorageInfo**](StationsApi.md#getMeasurementStorageInfo) | **GET** stations/measurements/storage | Returns information about the storage server for the measurements.
[**getPartialApiKey**](StationsApi.md#getPartialApiKey) | **GET** stations/{station_id}/api | Returns part of the API key for a station.
[**getPictureStorageInfo**](StationsApi.md#getPictureStorageInfo) | **GET** stations/pictures/storage | Returns information about the storage server for the pictures.
[**getStationStatus**](StationsApi.md#getStationStatus) | **GET** stations/{station_id}/ping | Pings the weather station.
[**updateStationStatus**](StationsApi.md#updateStationStatus) | **PUT** stations/status/{status_code} | Update the status of the weather station.



Returns information about the storage server for the measurements.

## Functionality:    Returns information about the storage server for the pictures.         ---   ### Prerequisites:   - This endpoint can only be used with a valid JWT token.   --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.example.weatherapi.data.entities.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(StationsApi::class.java)

launch(Dispatchers.IO) {
    val result : StorageInfoEntity = webService.getMeasurementStorageInfo()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**StorageInfoEntity**](StorageInfoEntity.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


Returns part of the API key for a station.

## Functionality:    Returns the API key of the station identified by station_id with only the first 4 and last 4 characters visible. Every other character is replaced by an &#39;X&#39; character         ---   ### Prerequisites:   - This endpoint can only be used with a valid JWT token.   --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.example.weatherapi.data.entities.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(StationsApi::class.java)
val stationId : kotlin.Long = 789 // kotlin.Long | station_id is a __unique__ identifier for weather stations station_id is an unsigned integer. 

launch(Dispatchers.IO) {
    val result : kotlin.String = webService.getPartialApiKey(stationId)
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **stationId** | **kotlin.Long**| station_id is a __unique__ identifier for weather stations station_id is an unsigned integer.  |

### Return type

**kotlin.String**

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


Returns information about the storage server for the pictures.

## Functionality:    Returns information about the storage server for the pictures.         ---   ### Prerequisites:   - This endpoint can only be used with a valid JWT token.   --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.example.weatherapi.data.entities.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(StationsApi::class.java)

launch(Dispatchers.IO) {
    val result : StorageInfoEntity = webService.getPictureStorageInfo()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**StorageInfoEntity**](StorageInfoEntity.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


Pings the weather station.

## Functionality:    Returns the status of a __single__ weather station.       ---   ### Prerequisites:   - This endpoint can only be used with a valid JWT token.   --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.example.weatherapi.data.entities.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(StationsApi::class.java)
val stationId : kotlin.Long = 789 // kotlin.Long | station_id is a __unique__ identifier for weather stations station_id is an unsigned integer. 

launch(Dispatchers.IO) {
    val result : ApiResponseEntity = webService.getStationStatus(stationId)
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **stationId** | **kotlin.Long**| station_id is a __unique__ identifier for weather stations station_id is an unsigned integer.  |

### Return type

[**ApiResponseEntity**](ApiResponseEntity.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


Update the status of the weather station.

## Functionality:    Updates the status of the weather station.         ---   ### Prerequisites:   - This endpoint can only be used with a valid API key.   --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.example.weatherapi.data.entities.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(StationsApi::class.java)
val statusCode : kotlin.Int = 56 // kotlin.Int | 

launch(Dispatchers.IO) {
    val result : ApiResponseEntity = webService.updateStationStatus(statusCode)
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **statusCode** | **kotlin.Int**|  |

### Return type

[**ApiResponseEntity**](ApiResponseEntity.md)

### Authorization



### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

