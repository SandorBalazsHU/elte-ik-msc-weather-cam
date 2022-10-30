# MeasurementsApi

All URIs are relative to *https://api.weather.s-b-x.com/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addMeasurements**](MeasurementsApi.md#addMeasurements) | **POST** /stations/measurements | Add new measurements to server
[**getFirstStationMeasurement**](MeasurementsApi.md#getFirstStationMeasurement) | **GET** /stations/{station_id}/measurements/first | Find the first measurement of a station.
[**getLatestStationMeasurement**](MeasurementsApi.md#getLatestStationMeasurement) | **GET** /stations/{station_id}/measurements/latest | Find the latest measurement of a station.
[**getStationMeasurementById**](MeasurementsApi.md#getStationMeasurementById) | **GET** /stations/{station_id}/measurements/{measurement_id} | Find a single measurement of a station.
[**getStationMeasurementsByQuery**](MeasurementsApi.md#getStationMeasurementsByQuery) | **GET** /stations/{station_id}/measurements | Find multiple measurements of a station in a given time range or relative to a measurement.


<a name="addMeasurements"></a>
# **addMeasurements**
> ApiResponseEntity addMeasurements(measurementEntity)

Add new measurements to server

## Functionality:  Add __multiple__ measurements a station to the server.  --- ### Prerequisites:   - This endpoint can only be used with a valid API key. --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = MeasurementsApi()
val measurementEntity : kotlin.collections.List<MeasurementEntity> =  // kotlin.collections.List<MeasurementEntity> | **Important**:    New measurements sent to the server do **not** contain a unique identifiers station_id and measurement_id like the ones recieved by the server.   Unique identifiers are assigned by the server upon processing the measurements. 
try {
    val result : ApiResponseEntity = apiInstance.addMeasurements(measurementEntity)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MeasurementsApi#addMeasurements")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MeasurementsApi#addMeasurements")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **measurementEntity** | [**kotlin.collections.List&lt;MeasurementEntity&gt;**](MeasurementEntity.md)| **Important**:    New measurements sent to the server do **not** contain a unique identifiers station_id and measurement_id like the ones recieved by the server.   Unique identifiers are assigned by the server upon processing the measurements.  |

### Return type

[**ApiResponseEntity**](ApiResponseEntity.md)

### Authorization


Configure apiKeyAuth:
    ApiClient.apiKey["api_key"] = ""
    ApiClient.apiKeyPrefix["api_key"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getFirstStationMeasurement"></a>
# **getFirstStationMeasurement**
> IndexedMeasurementEntity getFirstStationMeasurement(stationId)

Find the first measurement of a station.

## Functionality:  Returns the __first__ measurement recieved from the weather station.    *Note*: The first measurement is defined as the first measurement of station with station_id processed by the server.   --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = MeasurementsApi()
val stationId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 
try {
    val result : IndexedMeasurementEntity = apiInstance.getFirstStationMeasurement(stationId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MeasurementsApi#getFirstStationMeasurement")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MeasurementsApi#getFirstStationMeasurement")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **stationId** | **java.util.UUID**| station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  |

### Return type

[**IndexedMeasurementEntity**](IndexedMeasurementEntity.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getLatestStationMeasurement"></a>
# **getLatestStationMeasurement**
> IndexedMeasurementEntity getLatestStationMeasurement(stationId)

Find the latest measurement of a station.

## Functionality:  Returns the __latest__ measurement recieved from the weather station.    *Note*: The latest measurement is defined as the last measurement of station with station_id processed by the server.   --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = MeasurementsApi()
val stationId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 
try {
    val result : IndexedMeasurementEntity = apiInstance.getLatestStationMeasurement(stationId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MeasurementsApi#getLatestStationMeasurement")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MeasurementsApi#getLatestStationMeasurement")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **stationId** | **java.util.UUID**| station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  |

### Return type

[**IndexedMeasurementEntity**](IndexedMeasurementEntity.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getStationMeasurementById"></a>
# **getStationMeasurementById**
> IndexedMeasurementEntity getStationMeasurementById(measurementId, stationId)

Find a single measurement of a station.

## Functionality:  Returns a measurement identified by measurement_id of a station identified by station_id.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = MeasurementsApi()
val measurementId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | measurement_id is a __unique__ identifier for measurements.   measurement_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 
val stationId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 
try {
    val result : IndexedMeasurementEntity = apiInstance.getStationMeasurementById(measurementId, stationId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MeasurementsApi#getStationMeasurementById")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MeasurementsApi#getStationMeasurementById")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **measurementId** | **java.util.UUID**| measurement_id is a __unique__ identifier for measurements.   measurement_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  |
 **stationId** | **java.util.UUID**| station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  |

### Return type

[**IndexedMeasurementEntity**](IndexedMeasurementEntity.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getStationMeasurementsByQuery"></a>
# **getStationMeasurementsByQuery**
> kotlin.collections.List&lt;IndexedMeasurementEntity&gt; getStationMeasurementsByQuery(stationId, dateBegin, dateEnd, measurementId, offset)

Find multiple measurements of a station in a given time range or relative to a measurement.

## Functionality:  Returns __multiple__ measurements of a station in a given time range or relative to a measurement.  __Important__: Parameters pairs date_begin, date_end and measurement_id, offset are mutually exclusive. Either use date_begin and date_end or measurement_id and offset when sending a request. Mixing them will throw a bad request error.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 

### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import com.example.libapi.data.entities.*

val apiInstance = MeasurementsApi()
val stationId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 
val dateBegin : java.math.BigDecimal = 8.14 // java.math.BigDecimal | date_begin is the timestamp of which the returned measurements must be chronologically bigger or equal to.  date_begin follows the [unix time](https://en.wikipedia.org/wiki/Unix_time) standard. 
val dateEnd : java.math.BigDecimal = 8.14 // java.math.BigDecimal | date_end is the timestamp of which the returned measurements must be chronologically smaller or equal to.  date_end follows the [unix time](https://en.wikipedia.org/wiki/Unix_time) standard. 
val measurementId : java.util.UUID = 38400000-8cf0-11bd-b23e-10b96e4ef00d // java.util.UUID | measurement_id is a __unique__ identifier for measurements.   measurement_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard. 
val offset : java.math.BigDecimal = 8.14 // java.math.BigDecimal | offset is the number of measurements to be returned before or after the measurement provided in parameter measurement_id.  The signed property of offset is used to decide the returned measurements. For more information see the table below:  | offset value | result | |---|---| | offset < 0 | If a measurement with measurement_id for station with sation_id exist than an array containing the measurement identified by measurement_id and an additional abs(offset) amount of measurements chronologically __before__ measurement_id is returned.   | | offset > 0 | If a measurement with measurement_id for station with sation_id exist than an array containing the measurement identified by measurement_id and an additional abs(offset) amount of measurements chronologically __after__ measurement_id is returned.   | | offset = 0 | If a measurement with measurement_id for station with sation_id exist than an array containing the measurement identified by measurement_id is returned.   | 
try {
    val result : kotlin.collections.List<IndexedMeasurementEntity> = apiInstance.getStationMeasurementsByQuery(stationId, dateBegin, dateEnd, measurementId, offset)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MeasurementsApi#getStationMeasurementsByQuery")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MeasurementsApi#getStationMeasurementsByQuery")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **stationId** | **java.util.UUID**| station_id is a __unique__ identifier for weather stations  station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  |
 **dateBegin** | **java.math.BigDecimal**| date_begin is the timestamp of which the returned measurements must be chronologically bigger or equal to.  date_begin follows the [unix time](https://en.wikipedia.org/wiki/Unix_time) standard.  | [optional]
 **dateEnd** | **java.math.BigDecimal**| date_end is the timestamp of which the returned measurements must be chronologically smaller or equal to.  date_end follows the [unix time](https://en.wikipedia.org/wiki/Unix_time) standard.  | [optional]
 **measurementId** | **java.util.UUID**| measurement_id is a __unique__ identifier for measurements.   measurement_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  | [optional]
 **offset** | **java.math.BigDecimal**| offset is the number of measurements to be returned before or after the measurement provided in parameter measurement_id.  The signed property of offset is used to decide the returned measurements. For more information see the table below:  | offset value | result | |---|---| | offset &lt; 0 | If a measurement with measurement_id for station with sation_id exist than an array containing the measurement identified by measurement_id and an additional abs(offset) amount of measurements chronologically __before__ measurement_id is returned.   | | offset &gt; 0 | If a measurement with measurement_id for station with sation_id exist than an array containing the measurement identified by measurement_id and an additional abs(offset) amount of measurements chronologically __after__ measurement_id is returned.   | | offset &#x3D; 0 | If a measurement with measurement_id for station with sation_id exist than an array containing the measurement identified by measurement_id is returned.   |  | [optional]

### Return type

[**kotlin.collections.List&lt;IndexedMeasurementEntity&gt;**](IndexedMeasurementEntity.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

