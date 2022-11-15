/* tslint:disable */
/* eslint-disable */
/**
 * Weather-Cam-API (OpenAPI 3.0)
 * Weather-Cam-API is a REST API created as a part a software project at Eötvös Loránd university. The goal of the project is to turn old/unused mobile phones and an [esp8266](https://www.espressif.com/en/products/socs/esp8266) into a diy weather stations capable of recording photos, temperature, pressure and humidity data. 
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { exists, mapValues } from '../runtime';
/**
 * IndexedMeasurement is the same as Measurement with the exception of added properties station_id and measurement_id.  
station_id and measurement_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.

 * @export
 * @interface IndexedMeasurement
 */
export interface IndexedMeasurement {
    /**
     * temperature in celsius.
     * @type {number}
     * @memberof IndexedMeasurement
     */
    temperature: number;
    /**
     * pressure in millibars.
     * @type {number}
     * @memberof IndexedMeasurement
     */
    pressure: number;
    /**
     * humidity in percentage.
     * @type {number}
     * @memberof IndexedMeasurement
     */
    humidity: number;
    /**
     * remaining battery in percentage
     * @type {number}
     * @memberof IndexedMeasurement
     */
    battery: number;
    /**
     * the time of recording the measurement
timestamp follows the [unix time](https://en.wikipedia.org/wiki/Unix_time) standard.

     * @type {number}
     * @memberof IndexedMeasurement
     */
    timestamp: number;
    /**
     * measurement_id is a __unique__ identifier for measurements.  
measurement_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.

     * @type {string}
     * @memberof IndexedMeasurement
     */
    measurementId: string;
    /**
     * station_id is a __unique__ identifier for weather stations.  
station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.

     * @type {string}
     * @memberof IndexedMeasurement
     */
    stationId: string;
}

/**
 * Check if a given object implements the IndexedMeasurement interface.
 */
export function instanceOfIndexedMeasurement(value: object): boolean {
    let isInstance = true;
    isInstance = isInstance && "temperature" in value;
    isInstance = isInstance && "pressure" in value;
    isInstance = isInstance && "humidity" in value;
    isInstance = isInstance && "battery" in value;
    isInstance = isInstance && "timestamp" in value;
    isInstance = isInstance && "measurementId" in value;
    isInstance = isInstance && "stationId" in value;

    return isInstance;
}

export function IndexedMeasurementFromJSON(json: any): IndexedMeasurement {
    return IndexedMeasurementFromJSONTyped(json, false);
}

export function IndexedMeasurementFromJSONTyped(json: any, ignoreDiscriminator: boolean): IndexedMeasurement {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'temperature': json['temperature'],
        'pressure': json['pressure'],
        'humidity': json['humidity'],
        'battery': json['battery'],
        'timestamp': json['timestamp'],
        'measurementId': json['measurement_id'],
        'stationId': json['station_id'],
    };
}

export function IndexedMeasurementToJSON(value?: IndexedMeasurement | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'temperature': value.temperature,
        'pressure': value.pressure,
        'humidity': value.humidity,
        'battery': value.battery,
        'timestamp': value.timestamp,
        'measurement_id': value.measurementId,
        'station_id': value.stationId,
    };
}

