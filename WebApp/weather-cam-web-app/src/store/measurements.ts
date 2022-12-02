import type { Measurement } from "@/api/openapi/index.js";
import { defineStore } from "pinia";
import { measurementApi } from "@/api/apis.js";
import type { FetchCallbacks } from "@/types/types.js";
import { throwErrorByResponse, unifyError } from "@/api/errors/CustomErrors.js";
interface MeasurementsState {
  latestMeasurement: Measurement;
}

export const useMeasurementStore = defineStore("measurements", {
  state: () =>
    ({
      latestMeasurement: {
        battery: NaN,
        humidity: NaN,
        pressure: NaN,
        temperature: NaN,
        timestamp: NaN,
      } as Measurement,
    } as MeasurementsState),

  actions: {
    async fetchLatestMeasurement(stationId: number, callback?: FetchCallbacks) {
      try {
        const measurement = await measurementApi.getLatestStationMeasurementRaw({
          stationId: stationId,
          relative: "latest",
        });
        if (!measurement.raw.ok) {
          throwErrorByResponse(measurement.raw.status);
        }
        this.latestMeasurement = await measurement.value();
        callback?.onSuccess?.call(this);
      } catch (error) {
        callback?.onError?.call(this, unifyError(error));
      }
    },
  },
});
