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
      latestMeasurement: {} as Measurement,
    } as MeasurementsState),

  actions: {
    async fetchLatestMeasurement(stationId: string, callback?: FetchCallbacks) {
      try {
        const measurement = await measurementApi.getLatestStationMeasurementRaw({
          stationId: stationId,
        });
        if (!measurement.raw.ok) {
          throwErrorByResponse(measurement.raw.status);
        }
        this.latestMeasurement = await measurement.value();
        callback?.onSuccess?.call(this);
      } catch (error) {
        console.log("I got error");
        callback?.onError?.call(this, unifyError(error));
      }
    },
  },
});
