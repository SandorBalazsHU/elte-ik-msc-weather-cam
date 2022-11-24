import defaultApiConfig from "./apis.config.js";
import {
  Configuration,
  MeasurementsApi,
  PicturesApi,
  StationsApi,
  UserApi,
  type ConfigurationParameters,
} from "./openapi/index.js";

let configOptions: ConfigurationParameters = defaultApiConfig;

export let stationsApi = new StationsApi();
export let userApi = new UserApi();
export let picturesApi = new PicturesApi();
export let measurementApi = new MeasurementsApi();
export function changeApiConfig(newConfig: Partial<ConfigurationParameters>) {
  configOptions = { ...configOptions, ...newConfig };
  stationsApi = new StationsApi(new Configuration(configOptions));
  userApi = new UserApi(new Configuration(configOptions));
  picturesApi = new PicturesApi(new Configuration(configOptions));
  measurementApi = new MeasurementsApi(new Configuration(configOptions));
}

changeApiConfig(configOptions);
