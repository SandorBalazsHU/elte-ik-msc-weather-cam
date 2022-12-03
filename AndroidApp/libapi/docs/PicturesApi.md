# PicturesApi

All URIs are relative to *https://api.weather.s-b-x.com/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addPicture**](PicturesApi.md#addPicture) | **POST** stations/pictures | Add picture to server.
[**getLastPicture**](PicturesApi.md#getLastPicture) | **GET** stations/{station_id}/pictures/{relative} | Find last picture.
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

## Functionality:  Returns the __latest or the first__ picture recieved from the weather station.   *Note*: The latest picture is defined as the last picture of station with station_id processed by the server.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token.    --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.example.weatherapi.data.entities.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(PicturesApi::class.java)
val stationId : kotlin.Long = 789 // kotlin.Long | station_id is a __unique__ identifier for weather stations station_id is an unsigned integer. 
val relative : kotlin.String = relative_example // kotlin.String | 

launch(Dispatchers.IO) {
    val result : java.io.File = webService.getLastPicture(stationId, relative)
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **stationId** | **kotlin.Long**| station_id is a __unique__ identifier for weather stations station_id is an unsigned integer.  |
 **relative** | **kotlin.String**|  |

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
val pictureId : kotlin.Long = 789 // kotlin.Long | picture_id is a __unique__ identifier for pictures.  picture_id is an unsigned integer. 
val stationId : kotlin.Long = 789 // kotlin.Long | station_id is a __unique__ identifier for weather stations station_id is an unsigned integer. 

launch(Dispatchers.IO) {
    val result : java.io.File = webService.getPictureById(pictureId, stationId)
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **pictureId** | **kotlin.Long**| picture_id is a __unique__ identifier for pictures.  picture_id is an unsigned integer.  |
 **stationId** | **kotlin.Long**| station_id is a __unique__ identifier for weather stations station_id is an unsigned integer.  |

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
val stationId : kotlin.Long = 789 // kotlin.Long | station_id is a __unique__ identifier for weather stations station_id is an unsigned integer. 
val pictureId : kotlin.Long = 789 // kotlin.Long | picture_id is a __unique__ identifier for pictures.  picture_id is an unsigned integer. 
val offset : kotlin.Long = 789 // kotlin.Long | offset is a signed integer this signed property of is used to calculate the returned picture. For more information see the table below: | offset value | result | |---|---| | offset < 0 | If a picture with picture_id exist than the picture abs(offset) __before__ picture_id is returned.   | | offset > 0 | If a picture with picture_id exist than the picture abs(offset) __after__ picture_id is returned.   | | offset = 0 | If a record with picture_id exist than the picture identified by picture_id is returned.   | 

launch(Dispatchers.IO) {
    val result : java.io.File = webService.getRelativePicture(stationId, pictureId, offset)
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **stationId** | **kotlin.Long**| station_id is a __unique__ identifier for weather stations station_id is an unsigned integer.  |
 **pictureId** | **kotlin.Long**| picture_id is a __unique__ identifier for pictures.  picture_id is an unsigned integer.  |
 **offset** | **kotlin.Long**| offset is a signed integer this signed property of is used to calculate the returned picture. For more information see the table below: | offset value | result | |---|---| | offset &lt; 0 | If a picture with picture_id exist than the picture abs(offset) __before__ picture_id is returned.   | | offset &gt; 0 | If a picture with picture_id exist than the picture abs(offset) __after__ picture_id is returned.   | | offset &#x3D; 0 | If a record with picture_id exist than the picture identified by picture_id is returned.   |  |

### Return type

[**java.io.File**](java.io.File.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: image/jpeg, application/json

