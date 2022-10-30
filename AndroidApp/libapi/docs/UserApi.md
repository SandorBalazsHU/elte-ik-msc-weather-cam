# UserApi

All URIs are relative to *https://api.weather.s-b-x.com/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addStations**](UserApi.md#addStations) | **POST** /user/stations | Add new stations to the user.
[**deleteUser**](UserApi.md#deleteUser) | **DELETE** /user/{username} | Delete user.
[**getStations**](UserApi.md#getStations) | **GET** /user/stations | Returns an array of stations registered with the user.
[**loginUser**](UserApi.md#loginUser) | **GET** /user/login | Logs user into the system
[**logoutUser**](UserApi.md#logoutUser) | **GET** /user/logout | Logs out current logged in user.
[**updateStation**](UserApi.md#updateStation) | **PUT** /user/stations/{station_id} | Generate new API key for a station.
[**userRegisterPost**](UserApi.md#userRegisterPost) | **POST** /user/register | Add new user.
[**userUsernamePut**](UserApi.md#userUsernamePut) | **PUT** /user/{username} | Update user data.


<a name="addStations"></a>
# **addStations**
> StationApiKeyEntity addStations()

Add new stations to the user.

## Functionality:  Adds a new station to the list of stations associated with the user.  __Important__: This response contains information about the newly added station and it&#39;s API key.  The API key is only recieved once after creating a new station make sure to __write it down__ somewhere! After this only parts of the API key can be accessed by the user.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = UserApi()
try {
    val result : StationApiKeyEntity = apiInstance.addStations()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#addStations")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#addStations")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**StationApiKeyEntity**](StationApiKeyEntity.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="deleteUser"></a>
# **deleteUser**
> ApiResponseEntity deleteUser(username)

Delete user.

## Functionality:  Deletes a user.  --- ### Prerequisites: - This endpoint can only be used with a valid JWT token. --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = UserApi()
val username : kotlin.String = username_example // kotlin.String | The unique username of the user.
try {
    val result : ApiResponseEntity = apiInstance.deleteUser(username)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#deleteUser")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#deleteUser")
    e.printStackTrace()
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
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getStations"></a>
# **getStations**
> kotlin.collections.List&lt;StationEntity&gt; getStations()

Returns an array of stations registered with the user.

## Functionality:  Returns an array of stations associated with the user.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = UserApi()
try {
    val result : kotlin.collections.List<StationEntity> = apiInstance.getStations()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#getStations")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#getStations")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**kotlin.collections.List&lt;StationEntity&gt;**](StationEntity.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="loginUser"></a>
# **loginUser**
> ApiResponseEntity loginUser(username, password)

Logs user into the system

## Functionality:  Returns a JWT token. This token can later be used to authenticate requests.  --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = UserApi()
val username : kotlin.String = username_example // kotlin.String | The unique username for the user.
val password : kotlin.String = password_example // kotlin.String | The password associated with the username.
try {
    val result : ApiResponseEntity = apiInstance.loginUser(username, password)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#loginUser")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#loginUser")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **kotlin.String**| The unique username for the user. | [optional]
 **password** | **kotlin.String**| The password associated with the username. | [optional]

### Return type

[**ApiResponseEntity**](ApiResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="logoutUser"></a>
# **logoutUser**
> ApiResponseEntity logoutUser()

Logs out current logged in user.

## Functionality:  Logs out current logged in user.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = UserApi()
try {
    val result : ApiResponseEntity = apiInstance.logoutUser()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#logoutUser")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#logoutUser")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ApiResponseEntity**](ApiResponseEntity.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateStation"></a>
# **updateStation**
> StationApiKeyEntity updateStation(stationId)

Generate new API key for a station.

## Functionality:  Generate new API key for a station. This can be used to revoke all access of a weather station.  __Important__: After a new API key is generated stations that use this API key lose access to upload measurements. If you want to continue using the weather station you need to update the API key in the mobile app.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = UserApi()
val stationId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 
try {
    val result : StationApiKeyEntity = apiInstance.updateStation(stationId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#updateStation")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#updateStation")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **stationId** | **java.util.UUID**| station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  |

### Return type

[**StationApiKeyEntity**](StationApiKeyEntity.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="userRegisterPost"></a>
# **userRegisterPost**
> ApiResponseEntity userRegisterPost(username, password)

Add new user.

## Functionality:  Adds a new user to the server.  --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = UserApi()
val username : kotlin.String = username_example // kotlin.String | The unique username of the new user
val password : kotlin.String = password_example // kotlin.String | The password of the new user
try {
    val result : ApiResponseEntity = apiInstance.userRegisterPost(username, password)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#userRegisterPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#userRegisterPost")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **kotlin.String**| The unique username of the new user | [optional]
 **password** | **kotlin.String**| The password of the new user | [optional]

### Return type

[**ApiResponseEntity**](ApiResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="userUsernamePut"></a>
# **userUsernamePut**
> kotlin.String userUsernamePut(username, userEntity)

Update user data.

## Functionality:  Updates the data of an already existing user.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = UserApi()
val username : kotlin.String = username_example // kotlin.String | The unique username of the user.
val userEntity : UserEntity =  // UserEntity | **Important:**The user_id of the user cannot be updated with this endpoint. 
try {
    val result : kotlin.String = apiInstance.userUsernamePut(username, userEntity)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#userUsernamePut")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#userUsernamePut")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **kotlin.String**| The unique username of the user. |
 **userEntity** | [**UserEntity**](UserEntity.md)| **Important:**The user_id of the user cannot be updated with this endpoint.  | [optional]

### Return type

**kotlin.String**

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

