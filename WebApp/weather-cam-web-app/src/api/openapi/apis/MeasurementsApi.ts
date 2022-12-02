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


import * as runtime from '../runtime';
import type {
  IndexedMeasurement,
  Measurement,
  ModelApiResponse,
} from '../models';
import {
    IndexedMeasurementFromJSON,
    IndexedMeasurementToJSON,
    MeasurementFromJSON,
    MeasurementToJSON,
    ModelApiResponseFromJSON,
    ModelApiResponseToJSON,
} from '../models';

export interface AddMeasurementsRequest {
    measurement: Array<Measurement>;
}

export interface GetLatestStationMeasurementRequest {
    stationId: number;
    relative: string;
}

export interface GetStationMeasurementByIdRequest {
    measurementId: number;
    stationId: number;
}

export interface GetStationMeasurementsByQueryRequest {
    stationId: number;
    dateBegin?: number;
    dateEnd?: number;
    measurementId?: number;
    offset?: number;
}

/**
 * 
 */
export class MeasurementsApi extends runtime.BaseAPI {

    /**
     * ## Functionality:  Add __multiple__ measurements a station to the server.  --- ### Prerequisites:   - This endpoint can only be used with a valid API key. --- 
     * Add new measurements to server
     */
    async addMeasurementsRaw(requestParameters: AddMeasurementsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<ModelApiResponse>> {
        if (requestParameters.measurement === null || requestParameters.measurement === undefined) {
            throw new runtime.RequiredError('measurement','Required parameter requestParameters.measurement was null or undefined when calling addMeasurements.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        if (this.configuration && this.configuration.apiKey) {
            headerParameters["api_key"] = this.configuration.apiKey("api_key"); // apiKeyAuth authentication
        }

        const response = await this.request({
            path: `/stations/measurements`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: requestParameters.measurement.map(MeasurementToJSON),
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => ModelApiResponseFromJSON(jsonValue));
    }

    /**
     * ## Functionality:  Add __multiple__ measurements a station to the server.  --- ### Prerequisites:   - This endpoint can only be used with a valid API key. --- 
     * Add new measurements to server
     */
    async addMeasurements(requestParameters: AddMeasurementsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<ModelApiResponse> {
        const response = await this.addMeasurementsRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * ## Functionality:  Returns the __latest or the first__ measurement recieved from the weather station.   *Note*: The latest measurement is defined as the last measurement of station with station_id processed by the server. --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 
     * Find the latest measurement of a station.
     */
    async getLatestStationMeasurementRaw(requestParameters: GetLatestStationMeasurementRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<IndexedMeasurement>> {
        if (requestParameters.stationId === null || requestParameters.stationId === undefined) {
            throw new runtime.RequiredError('stationId','Required parameter requestParameters.stationId was null or undefined when calling getLatestStationMeasurement.');
        }

        if (requestParameters.relative === null || requestParameters.relative === undefined) {
            throw new runtime.RequiredError('relative','Required parameter requestParameters.relative was null or undefined when calling getLatestStationMeasurement.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        if (this.configuration && this.configuration.accessToken) {
            const token = this.configuration.accessToken;
            const tokenString = await token("bearerAuth", []);

            if (tokenString) {
                headerParameters["Authorization"] = `Bearer ${tokenString}`;
            }
        }
        const response = await this.request({
            path: `/stations/{station_id}/measurements/{relative}`.replace(`{${"station_id"}}`, encodeURIComponent(String(requestParameters.stationId))).replace(`{${"relative"}}`, encodeURIComponent(String(requestParameters.relative))),
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => IndexedMeasurementFromJSON(jsonValue));
    }

    /**
     * ## Functionality:  Returns the __latest or the first__ measurement recieved from the weather station.   *Note*: The latest measurement is defined as the last measurement of station with station_id processed by the server. --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 
     * Find the latest measurement of a station.
     */
    async getLatestStationMeasurement(requestParameters: GetLatestStationMeasurementRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<IndexedMeasurement> {
        const response = await this.getLatestStationMeasurementRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * ## Functionality:  Returns a measurement identified by measurement_id of a station identified by station_id.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 
     * Find a single measurement of a station.
     */
    async getStationMeasurementByIdRaw(requestParameters: GetStationMeasurementByIdRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<IndexedMeasurement>> {
        if (requestParameters.measurementId === null || requestParameters.measurementId === undefined) {
            throw new runtime.RequiredError('measurementId','Required parameter requestParameters.measurementId was null or undefined when calling getStationMeasurementById.');
        }

        if (requestParameters.stationId === null || requestParameters.stationId === undefined) {
            throw new runtime.RequiredError('stationId','Required parameter requestParameters.stationId was null or undefined when calling getStationMeasurementById.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        if (this.configuration && this.configuration.accessToken) {
            const token = this.configuration.accessToken;
            const tokenString = await token("bearerAuth", []);

            if (tokenString) {
                headerParameters["Authorization"] = `Bearer ${tokenString}`;
            }
        }
        const response = await this.request({
            path: `/stations/{station_id}/measurements/{measurement_id}`.replace(`{${"measurement_id"}}`, encodeURIComponent(String(requestParameters.measurementId))).replace(`{${"station_id"}}`, encodeURIComponent(String(requestParameters.stationId))),
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => IndexedMeasurementFromJSON(jsonValue));
    }

    /**
     * ## Functionality:  Returns a measurement identified by measurement_id of a station identified by station_id.  --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 
     * Find a single measurement of a station.
     */
    async getStationMeasurementById(requestParameters: GetStationMeasurementByIdRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<IndexedMeasurement> {
        const response = await this.getStationMeasurementByIdRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * ## Functionality:  Returns __multiple__ measurements of a station in a given time range or relative to a measurement.  __Important__: Parameters pairs date_begin, date_end and measurement_id, offset are mutually exclusive. Either use date_begin and date_end or measurement_id and offset when sending a request. Mixing them will throw a bad request error. --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 
     * Find multiple measurements of a station in a given time range or relative to a measurement.
     */
    async getStationMeasurementsByQueryRaw(requestParameters: GetStationMeasurementsByQueryRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<Array<IndexedMeasurement>>> {
        if (requestParameters.stationId === null || requestParameters.stationId === undefined) {
            throw new runtime.RequiredError('stationId','Required parameter requestParameters.stationId was null or undefined when calling getStationMeasurementsByQuery.');
        }

        const queryParameters: any = {};

        if (requestParameters.dateBegin !== undefined) {
            queryParameters['date_begin'] = requestParameters.dateBegin;
        }

        if (requestParameters.dateEnd !== undefined) {
            queryParameters['date_end'] = requestParameters.dateEnd;
        }

        if (requestParameters.measurementId !== undefined) {
            queryParameters['measurement_id'] = requestParameters.measurementId;
        }

        if (requestParameters.offset !== undefined) {
            queryParameters['offset'] = requestParameters.offset;
        }

        const headerParameters: runtime.HTTPHeaders = {};

        if (this.configuration && this.configuration.accessToken) {
            const token = this.configuration.accessToken;
            const tokenString = await token("bearerAuth", []);

            if (tokenString) {
                headerParameters["Authorization"] = `Bearer ${tokenString}`;
            }
        }
        const response = await this.request({
            path: `/stations/{station_id}/measurements`.replace(`{${"station_id"}}`, encodeURIComponent(String(requestParameters.stationId))),
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => jsonValue.map(IndexedMeasurementFromJSON));
    }

    /**
     * ## Functionality:  Returns __multiple__ measurements of a station in a given time range or relative to a measurement.  __Important__: Parameters pairs date_begin, date_end and measurement_id, offset are mutually exclusive. Either use date_begin and date_end or measurement_id and offset when sending a request. Mixing them will throw a bad request error. --- ### Prerequisites:   - This endpoint can only be used with a valid JWT token. --- 
     * Find multiple measurements of a station in a given time range or relative to a measurement.
     */
    async getStationMeasurementsByQuery(requestParameters: GetStationMeasurementsByQueryRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<Array<IndexedMeasurement>> {
        const response = await this.getStationMeasurementsByQueryRaw(requestParameters, initOverrides);
        return await response.value();
    }

}
