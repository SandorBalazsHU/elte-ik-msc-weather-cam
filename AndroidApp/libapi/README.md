# org.openapitools.client - Kotlin client library for Weather-Cam-API (OpenAPI 3.0)

## Requires

* Kotlin 1.4.30
* Gradle 6.8.3

## Build

First, create the gradle wrapper script:

```
gradle wrapper
```

Then, run:

```
./gradlew check assemble
```

This runs all tests and packages the library.

## Features/Implementation Notes

* Supports JSON inputs/outputs, File inputs, and Form inputs.
* Supports collection formats for query parameters: csv, tsv, ssv, pipes.
* Some Kotlin and Java types are fully qualified to avoid conflicts with types defined in OpenAPI definitions.
* Implementation of ApiClient is intended to reduce method counts, specifically to benefit Android targets.

<a name="documentation-for-api-endpoints"></a>
## Documentation for API Endpoints

All URIs are relative to *https://api.weather.s-b-x.com/v1*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*MeasurementsApi* | [**addMeasurements**](docs/MeasurementsApi.md#addmeasurements) | **POST** /stations/measurements | Add new measurements to server
*MeasurementsApi* | [**getFirstStationMeasurement**](docs/MeasurementsApi.md#getfirststationmeasurement) | **GET** /stations/{station_id}/measurements/first | Find the first measurement of a station.
*MeasurementsApi* | [**getLatestStationMeasurement**](docs/MeasurementsApi.md#getlateststationmeasurement) | **GET** /stations/{station_id}/measurements/latest | Find the latest measurement of a station.
*MeasurementsApi* | [**getStationMeasurementById**](docs/MeasurementsApi.md#getstationmeasurementbyid) | **GET** /stations/{station_id}/measurements/{measurement_id} | Find a single measurement of a station.
*MeasurementsApi* | [**getStationMeasurementsByQuery**](docs/MeasurementsApi.md#getstationmeasurementsbyquery) | **GET** /stations/{station_id}/measurements | Find multiple measurements of a station in a given time range or relative to a measurement.
*PicturesApi* | [**addPicture**](docs/PicturesApi.md#addpicture) | **POST** /stations/pictures | Add picture to server.
*PicturesApi* | [**getLastPicture**](docs/PicturesApi.md#getlastpicture) | **GET** /stations/{station_id}/pictures/latest | Find last picture.
*PicturesApi* | [**getPictureById**](docs/PicturesApi.md#getpicturebyid) | **GET** /stations/{station_id}/pictures/{picture_id} | Find picture by id.
*PicturesApi* | [**getRelativePicture**](docs/PicturesApi.md#getrelativepicture) | **GET** /stations/{station_id}/pictures | Find picture relative to other picture.
*StationsApi* | [**getMeasurementStorageInfo**](docs/StationsApi.md#getmeasurementstorageinfo) | **GET** /stations/measurements/storage | Returns information about the storage server for the measurements.
*StationsApi* | [**getPartialApiKey**](docs/StationsApi.md#getpartialapikey) | **GET** /stations/{station_id}/api/ | Returns part of the API key for a station.
*StationsApi* | [**getPictureStorageInfo**](docs/StationsApi.md#getpicturestorageinfo) | **GET** /stations/pictures/storage | Returns information about the storage server for the pictures.
*StationsApi* | [**getStationStatus**](docs/StationsApi.md#getstationstatus) | **GET** /stations/{station_id}/ping | Pings the weather station.
*StationsApi* | [**updateStationStatus**](docs/StationsApi.md#updatestationstatus) | **PUT** /stations/status/{status_code} | Update the status of the weather station.
*UserApi* | [**addStations**](docs/UserApi.md#addstations) | **POST** /user/stations | Add new stations to the user.
*UserApi* | [**deleteUser**](docs/UserApi.md#deleteuser) | **DELETE** /user/{username} | Delete user.
*UserApi* | [**getStations**](docs/UserApi.md#getstations) | **GET** /user/stations | Returns an array of stations registered with the user.
*UserApi* | [**loginUser**](docs/UserApi.md#loginuser) | **GET** /user/login | Logs user into the system
*UserApi* | [**logoutUser**](docs/UserApi.md#logoutuser) | **GET** /user/logout | Logs out current logged in user.
*UserApi* | [**updateStation**](docs/UserApi.md#updatestation) | **PUT** /user/stations/{station_id} | Generate new API key for a station.
*UserApi* | [**userRegisterPost**](docs/UserApi.md#userregisterpost) | **POST** /user/register | Add new user.
*UserApi* | [**userUsernamePut**](docs/UserApi.md#userusernameput) | **PUT** /user/{username} | Update user data.


<a name="documentation-for-models"></a>
## Documentation for Models

 - [com.example.libapi.data.entities.ApiResponseEntity](docs/ApiResponseEntity.md)
 - [com.example.libapi.data.entities.IndexedMeasurementAllOfEntity](docs/IndexedMeasurementAllOfEntity.md)
 - [com.example.libapi.data.entities.IndexedMeasurementEntity](docs/IndexedMeasurementEntity.md)
 - [com.example.libapi.data.entities.MeasurementEntity](docs/MeasurementEntity.md)
 - [com.example.libapi.data.entities.StationApiKeyEntity](docs/StationApiKeyEntity.md)
 - [com.example.libapi.data.entities.StationEntity](docs/StationEntity.md)
 - [com.example.libapi.data.entities.StorageInfoEntity](docs/StorageInfoEntity.md)
 - [com.example.libapi.data.entities.UserEntity](docs/UserEntity.md)


<a name="documentation-for-authorization"></a>
## Documentation for Authorization

<a name="apiKeyAuth"></a>
### apiKeyAuth

- **Type**: API key
- **API key parameter name**: api_key
- **Location**: HTTP header

<a name="bearerAuth"></a>
### bearerAuth

- **Type**: HTTP basic authentication

