import { userApi } from "@/api/apis.js";
import { throwErrorByResponse, unifyError } from "@/api/errors/CustomErrors.js";
import type { Station } from "@/api/openapi/index.js";
import type { FetchCallbacks } from "@/types/types.js";
import { defineStore } from "pinia";

interface StationsState {
  stations: Station[];
  selectedStationId: number;
}

export const useStationStore = defineStore("stations", {
  state: () =>
    ({
      stations: [] as Station[],
      selectedStationId: 0,
    } as StationsState),
  getters: {
    getSelectedStation: ({ selectedStationId, stations }) =>
      stations.find((station) => station.stationId === selectedStationId),
  },
  actions: {
    changeSelectedStation(stationId: number) {
      this.selectedStationId =
        this.stations.find((station) => station.stationId === stationId)?.stationId ?? 0;
    },

    async fetchUserStations(callback?: FetchCallbacks) {
      try {
        const result = await userApi.getStationsRaw();
        if (!result.raw.ok) {
          throwErrorByResponse(result.raw.status);
        }
        this.stations = await result.value();
        if (this.selectedStationId === undefined && this.stations.length > 0) {
          console.log(this.selectedStationId);
          this.changeSelectedStation(this.stations[0].stationId);
        }
        callback?.onSuccess?.call(this);
      } catch (error) {
        this.stations = [];
        callback?.onError?.call(this, unifyError(error));
      }
      return this.stations;
    },
    async addUserStation(stationName: string, callback?: FetchCallbacks) {
      try {
        const result = await userApi.addStationsRaw({ stationName });
        if (!result.raw.ok) {
          throwErrorByResponse(result.raw.status);
        }
        this.fetchUserStations();
        callback?.onSuccess?.call(this);
      } catch (error) {
        callback?.onError?.call(this, unifyError(error));
      }
    },
  },
});
