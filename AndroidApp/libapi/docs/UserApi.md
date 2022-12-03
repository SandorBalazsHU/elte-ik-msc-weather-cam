# UserApi

All URIs are relative to *https://api.weather.s-b-x.com/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addStations**](UserApi.md#addStations) | **POST** user/stations | Add new stations to the user.
[**deleteUser**](UserApi.md#deleteUser) | **DELETE** user/{username} | Delete user.
[**getCurrentUser**](UserApi.md#getCurrentUser) | **GET** user | Gets data about the logged in user.
[**getStations**](UserApi.md#getStations) | **GET** user/stations | Returns an array of stations registered with the user.
[**loginUser**](UserApi.md#loginUser) | **GET** user/login | Logs user into the system
[**logoutUser**](UserApi.md#logoutUser) | **GET** user/logout | Logs out current logged in user.
[**registerUser**](UserApi.md#registerUser) | **POST** user/register | Add new user.
[**updateStation**](UserApi.md#updateStation) | **PUT** user/stations/{station_id} | Generate new API key for a station.
[**updateUser**](UserApi.md#updateUser) | **PUT** user/{username} | Update user data.



Add new stations to the user.

## Functionality:  Adds a new station to the list of stations associated with the user.  __Important__: This response contains information about the newly added station and it&#39;s API key.  The API key is only recieved once after creating a new station make sure to __write it down__ somewhere! After this only parts of the API key can be accessed by the user.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.example.weatherapi.data.entities.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(UserApi::class.java)

launch(Dispatchers.IO) {
    val result : StationApiKeyEntity = webService.addStations()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**StationApiKeyEntity**](StationApiKeyEntity.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


Delete user.

## Functionality:  Deletes a user.  --- ### Prerequisites: - This endpoint can only be used with a valid JWT token.  --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.example.weatherapi.data.entities.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(UserApi::class.java)
val username : kotlin.String = username_example // kotlin.String | The unique username of the user.

launch(Dispatchers.IO) {
    val result : ApiResponseEntity = webService.deleteUser(username)
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **kotlin.String**| The unique username of the user. |

### Return type

[**ApiResponseEntity**](ApiResponseEntity.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


Gets data about the logged in user.

## Functionality:    Returns data about the currently logged in user. This is useful when the JWT token is saved in the cookies.    ---  ### Prerequisites:   - This endpoint can only be used with a valid JWT token.    --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.example.weatherapi.data.entities.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(UserApi::class.java)

launch(Dispatchers.IO) {
    val result : UserEntity = webService.getCurrentUser()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**UserEntity**](UserEntity.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


Returns an array of stations registered with the user.

## Functionality:  Returns an array of stations associated with the user.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token.    --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.example.weatherapi.data.entities.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(UserApi::class.java)

launch(Dispatchers.IO) {
    val result : kotlin.collections.List<StationEntity> = webService.getStations()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**kotlin.collections.List&lt;StationEntity&gt;**](StationEntity.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


Logs user into the system

## Functionality:    Returns a JWT token. This token can later be used to authenticate requests.    --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.example.weatherapi.data.entities.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(UserApi::class.java)
val username : kotlin.String = username_example // kotlin.String | The unique username for the user.
val password : kotlin.String = password_example // kotlin.String | The password associated with the username.

launch(Dispatchers.IO) {
    val result : ApiResponseEntity = webService.loginUser(username, password)
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **kotlin.String**| The unique username for the user. |
 **password** | **kotlin.String**| The password associated with the username. |

### Return type

[**ApiResponseEntity**](ApiResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


Logs out current logged in user.

## Functionality:  Logs out current logged in user.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token.    --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.example.weatherapi.data.entities.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(UserApi::class.java)

launch(Dispatchers.IO) {
    val result : ApiResponseEntity = webService.logoutUser()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ApiResponseEntity**](ApiResponseEntity.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


Add new user.

## Functionality:  Adds a new user to the server.  --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.example.weatherapi.data.entities.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(UserApi::class.java)
val username : kotlin.String = username_example // kotlin.String | The unique username of the new user
val password : kotlin.String = password_example // kotlin.String | The password of the new user

launch(Dispatchers.IO) {
    val result : ApiResponseEntity = webService.registerUser(username, password)
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **kotlin.String**| The unique username of the new user |
 **password** | **kotlin.String**| The password of the new user |

### Return type

[**ApiResponseEntity**](ApiResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


Generate new API key for a station.

## Functionality:  Generate new API key for a station. This can be used to revoke all access of a weather station.  __Important__: After a new API key is generated stations that use this API key lose access to upload measurements. If you want to continue using the weather station you need to update the API key in the mobile app.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token.    --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.example.weatherapi.data.entities.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(UserApi::class.java)
val stationId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | station_id is a __unique__ identifier for weather stations station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 

launch(Dispatchers.IO) {
    val result : StationApiKeyEntity = webService.updateStation(stationId)
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **stationId** | **java.util.UUID**| station_id is a __unique__ identifier for weather stations station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  |

### Return type

[**StationApiKeyEntity**](StationApiKeyEntity.md)

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


Update user data.

## Functionality:  Updates the data of an already existing user.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token.    --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import com.example.weatherapi.data.entities.*

val apiClient = ApiClient()
apiClient.setBearerToken("TOKEN")
val webService = apiClient.createWebservice(UserApi::class.java)
val username : kotlin.String = username_example // kotlin.String | The unique username of the user.
val userUpdateableEntity : UserUpdateableEntity =  // UserUpdateableEntity | **Important:**The user_id of the user cannot be updated with this endpoint. 

launch(Dispatchers.IO) {
    val result : kotlin.String = webService.updateUser(username, userUpdateableEntity)
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **kotlin.String**| The unique username of the user. |
 **userUpdateableEntity** | [**UserUpdateableEntity**](UserUpdateableEntity.md)| **Important:**The user_id of the user cannot be updated with this endpoint.  | [optional]

### Return type

**kotlin.String**

### Authorization


Configure bearerAuth:
    ApiClient().setBearerToken("TOKEN")

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

