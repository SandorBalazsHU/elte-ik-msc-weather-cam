# PicturesApi

All URIs are relative to *https://api.weather.s-b-x.com/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addPicture**](PicturesApi.md#addPicture) | **POST** stations/pictures | Add picture to server.
[**getLastPicture**](PicturesApi.md#getLastPicture) | **GET** stations/{station_id}/pictures/latest | Find last picture.
[**getPictureById**](PicturesApi.md#getPictureById) | **GET** stations/{station_id}/pictures/{picture_id} | Find picture by id.
[**getRelativePicture**](PicturesApi.md#getRelativePicture) | **GET** stations/{station_id}/pictures | Find picture relative to other picture.



Add picture to server.

## Functionality:  Upload picture to the server.  --- ### Prerequisites:   - This endpoint can only be used with a valid API key.    --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.example.weatherapi.data.entities.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(PicturesApi::class.java)
val body : java.io.File = BINARY_DATA_HERE // java.io.File | Server

launch(Dispatchers.IO) {
    val result : ApiResponseEntity = webService.addPicture(body)
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | **java.io.File**| Server |

### Return type

[**ApiResponseEntity**](ApiResponseEntity.md)

### Authorization



### HTTP request headers

 - **Content-Type**: image/jpeg
 - **Accept**: application/json


Find last picture.

## Functionality:  Returns the __latest__ picture recieved from the weather station.   *Note*: The latest picture is defined as the last picture of station with station_id processed by the server.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token.    --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.example.weatherapi.data.entities.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(PicturesApi::class.java)
val stationId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | station_id is a __unique__ identifier for weather stations station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 

launch(Dispatchers.IO) {
    val result : java.io.File = webService.getLastPicture(stationId)
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **stationId** | **java.util.UUID**| station_id is a __unique__ identifier for weather stations station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  |

### Return type

[**java.io.File**](java.io.File.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: image/jpeg, application/json


Find picture by id.

## Functionality:    Returns a picture identified by picture_id of a station identified by station_id.      ---   ### Prerequisites:   - This endpoint can only be used with a valid JWT token.      --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.example.weatherapi.data.entities.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(PicturesApi::class.java)
val pictureId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | 
val stationId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | station_id is a __unique__ identifier for weather stations station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 

launch(Dispatchers.IO) {
    val result : java.io.File = webService.getPictureById(pictureId, stationId)
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **pictureId** | **java.util.UUID**|  |
 **stationId** | **java.util.UUID**| station_id is a __unique__ identifier for weather stations station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  |

### Return type

[**java.io.File**](java.io.File.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: image/jpeg, application/json


Find picture relative to other picture.

## Functionality:  Returns a __single__ picture relative to an other picture.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token.    --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.example.weatherapi.data.entities.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(PicturesApi::class.java)
val stationId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | station_id is a __unique__ identifier for weather stations station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 
val pictureId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | picture_id is a __unique__ identifier for pictures.  picture_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 
val offset : kotlin.Long = 789 // kotlin.Long | offset is a signed integer this signed property of is used to calculate the returned picture. For more information see the table below: | offset value | result | |---|---| | offset < 0 | If a picture with picture_id exist than the picture abs(offset) __before__ picture_id is returned.   | | offset > 0 | If a picture with picture_id exist than the picture abs(offset) __after__ picture_id is returned.   | | offset = 0 | If a record with picture_id exist than the picture identified by picture_id is returned.   | 

launch(Dispatchers.IO) {
    val result : java.io.File = webService.getRelativePicture(stationId, pictureId, offset)
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **stationId** | **java.util.UUID**| station_id is a __unique__ identifier for weather stations station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  |
 **pictureId** | **java.util.UUID**| picture_id is a __unique__ identifier for pictures.  picture_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  |
 **offset** | **kotlin.Long**| offset is a signed integer this signed property of is used to calculate the returned picture. For more information see the table below: | offset value | result | |---|---| | offset &lt; 0 | If a picture with picture_id exist than the picture abs(offset) __before__ picture_id is returned.   | | offset &gt; 0 | If a picture with picture_id exist than the picture abs(offset) __after__ picture_id is returned.   | | offset &#x3D; 0 | If a record with picture_id exist than the picture identified by picture_id is returned.   |  |

### Return type

[**java.io.File**](java.io.File.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: image/jpeg, application/json

