# PicturesApi

All URIs are relative to *https://api.weather.s-b-x.com/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addPicture**](PicturesApi.md#addPicture) | **POST** /stations/pictures | Add picture to server.
[**getLastPicture**](PicturesApi.md#getLastPicture) | **GET** /stations/{station_id}/pictures/latest | Find last picture.
[**getPictureById**](PicturesApi.md#getPictureById) | **GET** /stations/{station_id}/pictures/{picture_id} | Find picture by id.
[**getRelativePicture**](PicturesApi.md#getRelativePicture) | **GET** /stations/{station_id}/pictures | Find picture relative to other picture.


<a name="addPicture"></a>
# **addPicture**
> ApiResponseEntity addPicture(body)

Add picture to server.

## Functionality:  Upload picture to the server.  --- ### Prerequisites:   - This endpoint can only be used with a valid API key. --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = PicturesApi()
val body : java.io.File = BINARY_DATA_HERE // java.io.File | Server
try {
    val result : ApiResponseEntity = apiInstance.addPicture(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PicturesApi#addPicture")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PicturesApi#addPicture")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | **java.io.File**| Server |

### Return type

[**ApiResponseEntity**](ApiResponseEntity.md)

### Authorization


Configure apiKeyAuth:
    ApiClient.apiKey["api_key"] = ""
    ApiClient.apiKeyPrefix["api_key"] = ""

### HTTP request headers

 - **Content-Type**: image/jpeg
 - **Accept**: application/json

<a name="getLastPicture"></a>
# **getLastPicture**
> java.io.File getLastPicture(stationId)

Find last picture.

## Functionality:  Returns the __latest__ picture recieved from the weather station.    *Note*: The latest picture is defined as the last picture of station with station_id processed by the server.   --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = PicturesApi()
val stationId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 
try {
    val result : java.io.File = apiInstance.getLastPicture(stationId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PicturesApi#getLastPicture")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PicturesApi#getLastPicture")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **stationId** | **java.util.UUID**| station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  |

### Return type

[**java.io.File**](java.io.File.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: image/jpeg, application/json

<a name="getPictureById"></a>
# **getPictureById**
> java.io.File getPictureById(pictureId, stationId)

Find picture by id.

## Functionality:    Returns a picture identified by picture_id of a station identified by station_id.        ---   ### Prerequisites:   - This endpoint can only be used with a valid JWT token.   --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = PicturesApi()
val pictureId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | 
val stationId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 
try {
    val result : java.io.File = apiInstance.getPictureById(pictureId, stationId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PicturesApi#getPictureById")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PicturesApi#getPictureById")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **pictureId** | **java.util.UUID**|  |
 **stationId** | **java.util.UUID**| station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  |

### Return type

[**java.io.File**](java.io.File.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: image/jpeg, application/json

<a name="getRelativePicture"></a>
# **getRelativePicture**
> java.io.File getRelativePicture(stationId, pictureId, offset)

Find picture relative to other picture.

## Functionality:  Returns a __single__ picture relative to an other picture.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = PicturesApi()
val stationId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 
val pictureId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | picture_id is a __unique__ identifier for pictures.   picture_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 
val offset : java.math.BigDecimal = 8.14 // java.math.BigDecimal | offset is a signed integer this signed property of is used to calculate the returned picture. For more information see the table below:  | offset value | result | |---|---| | offset < 0 | If a picture with picture_id exist than the picture abs(offset) __before__ picture_id is returned.   | | offset > 0 | If a picture with picture_id exist than the picture abs(offset) __after__ picture_id is returned.   | | offset = 0 | If a record with picture_id exist than the picture identified by picture_id is returned.   | 
try {
    val result : java.io.File = apiInstance.getRelativePicture(stationId, pictureId, offset)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling PicturesApi#getRelativePicture")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PicturesApi#getRelativePicture")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **stationId** | **java.util.UUID**| station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  |
 **pictureId** | **java.util.UUID**| picture_id is a __unique__ identifier for pictures.   picture_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  |
 **offset** | **java.math.BigDecimal**| offset is a signed integer this signed property of is used to calculate the returned picture. For more information see the table below:  | offset value | result | |---|---| | offset &lt; 0 | If a picture with picture_id exist than the picture abs(offset) __before__ picture_id is returned.   | | offset &gt; 0 | If a picture with picture_id exist than the picture abs(offset) __after__ picture_id is returned.   | | offset &#x3D; 0 | If a record with picture_id exist than the picture identified by picture_id is returned.   |  |

### Return type

[**java.io.File**](java.io.File.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: image/jpeg, application/json

