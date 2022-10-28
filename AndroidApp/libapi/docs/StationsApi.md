# StationsApi

All URIs are relative to *https://api.weather.s-b-x.com/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getMeasurementStorageInfo**](StationsApi.md#getMeasurementStorageInfo) | **GET** /stations/measurements/storage | Returns information about the storage server for the measurements.
[**getPartialApiKey**](StationsApi.md#getPartialApiKey) | **GET** /stations/{station_id}/api/ | Returns part of the API key for a station.
[**getPictureStorageInfo**](StationsApi.md#getPictureStorageInfo) | **GET** /stations/pictures/storage | Returns information about the storage server for the pictures.
[**getStationStatus**](StationsApi.md#getStationStatus) | **GET** /stations/{station_id}/ping | Pings the weather station.
[**updateStationStatus**](StationsApi.md#updateStationStatus) | **PUT** /stations/status/{status_code} | Update the status of the weather station.


<a name="getMeasurementStorageInfo"></a>
# **getMeasurementStorageInfo**
> StorageInfoEntity getMeasurementStorageInfo()

Returns information about the storage server for the measurements.

## Functionality:    Returns information about the storage server for the pictures.         ---   ### Prerequisites:   - This endpoint can only be used with a valid JWT token.   --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = StationsApi()
try {
    val result : StorageInfoEntity = apiInstance.getMeasurementStorageInfo()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StationsApi#getMeasurementStorageInfo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StationsApi#getMeasurementStorageInfo")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**StorageInfoEntity**](StorageInfoEntity.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPartialApiKey"></a>
# **getPartialApiKey**
> kotlin.String getPartialApiKey(stationId)

Returns part of the API key for a station.

## Functionality:    Returns the API key of the station identified by station_id with only the first 4 and last 4 characters visible. Every other character is replaced by an &#39;X&#39; character         ---   ### Prerequisites:   - This endpoint can only be used with a valid JWT token.   --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = StationsApi()
val stationId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 
try {
    val result : kotlin.String = apiInstance.getPartialApiKey(stationId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StationsApi#getPartialApiKey")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StationsApi#getPartialApiKey")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **stationId** | **java.util.UUID**| station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  |

### Return type

**kotlin.String**

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getPictureStorageInfo"></a>
# **getPictureStorageInfo**
> StorageInfoEntity getPictureStorageInfo()

Returns information about the storage server for the pictures.

## Functionality:    Returns information about the storage server for the pictures.         ---   ### Prerequisites:   - This endpoint can only be used with a valid JWT token.   --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = StationsApi()
try {
    val result : StorageInfoEntity = apiInstance.getPictureStorageInfo()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StationsApi#getPictureStorageInfo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StationsApi#getPictureStorageInfo")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**StorageInfoEntity**](StorageInfoEntity.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getStationStatus"></a>
# **getStationStatus**
> ApiResponseEntity getStationStatus(stationId)

Pings the weather station.

## Functionality:    Returns the status of a __single__ weather station.       ---   ### Prerequisites:   - This endpoint can only be used with a valid JWT token.   --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = StationsApi()
val stationId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 
try {
    val result : ApiResponseEntity = apiInstance.getStationStatus(stationId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StationsApi#getStationStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StationsApi#getStationStatus")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **stationId** | **java.util.UUID**| station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  |

### Return type

[**ApiResponseEntity**](ApiResponseEntity.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateStationStatus"></a>
# **updateStationStatus**
> ApiResponseEntity updateStationStatus(statusCode)

Update the status of the weather station.

## Functionality:    Updates the status of the weather station.         ---   ### Prerequisites:   - This endpoint can only be used with a valid API key.   --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = StationsApi()
val statusCode : java.math.BigDecimal = 8.14 // java.math.BigDecimal | 
try {
    val result : ApiResponseEntity = apiInstance.updateStationStatus(statusCode)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling StationsApi#updateStationStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling StationsApi#updateStationStatus")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **statusCode** | **java.math.BigDecimal**|  |

### Return type

[**ApiResponseEntity**](ApiResponseEntity.md)

### Authorization


Configure apiKeyAuth:
    ApiClient.apiKey["api_key"] = ""
    ApiClient.apiKeyPrefix["api_key"] = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

