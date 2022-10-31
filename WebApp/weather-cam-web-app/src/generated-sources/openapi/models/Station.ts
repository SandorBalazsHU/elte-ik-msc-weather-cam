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
 * Station contains information about a weather station.
 * @export
 * @interface Station
 */
export interface Station {
    /**
     * station_id is a __unique__ identifier for stations. 
station_id follows the [uuid](https://en.wikipedia.org/wiki/Universally_unique_identifier) standard.

     * @type {string}
     * @memberof Station
     */
    stationId?: string;
    /**
     * station_name is the unique name of the station.
     * @type {string}
     * @memberof Station
     */
    stationName?: string;
}

/**
 * Check if a given object implements the Station interface.
 */
export function instanceOfStation(value: object): boolean {
    let isInstance = true;

    return isInstance;
}

export function StationFromJSON(json: any): Station {
    return StationFromJSONTyped(json, false);
}

export function StationFromJSONTyped(json: any, ignoreDiscriminator: boolean): Station {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'stationId': !exists(json, 'station_id') ? undefined : json['station_id'],
        'stationName': !exists(json, 'station_name') ? undefined : json['station_name'],
    };
}

export function StationToJSON(value?: Station | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'station_id': value.stationId,
        'station_name': value.stationName,
    };
}

