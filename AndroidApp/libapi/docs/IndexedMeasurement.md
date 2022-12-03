
# IndexedMeasurementEntity

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**temperature** | **kotlin.Float** | temperature in celsius. |  [optional]
**pressure** | **kotlin.Float** | pressure in millibars. |  [optional]
**humidity** | **kotlin.Float** | humidity in percentage. |  [optional]
**battery** | **kotlin.Float** | remaining battery in percentage |  [optional]
**timestamp** | **kotlin.Long** | the time of recording the measurement timestamp follows the [unix time](https://en.wikipedia.org/wiki/Unix_time) standard.  |  [optional]
**measurementId** | [**java.util.UUID**](java.util.UUID.md) | measurement_id is a __unique__ identifier for measurements.   measurement_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  |  [optional]
**stationId** | [**java.util.UUID**](java.util.UUID.md) | station_id is a __unique__ identifier for weather stations.   station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.  |  [optional]



