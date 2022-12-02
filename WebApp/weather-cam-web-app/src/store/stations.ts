import { userApi } from "@/api/apis.js";
import { throwErrorByResponse, unifyError } from "@/api/errors/CustomErrors.js";
import type { Station } from "@/api/openapi/index.js";
import type { FetchCallbacks } from "@/types/types.js";
import { defineStore } from "pinia";

interface StationsState {
  stations: Station[];
  selectedStationId: string | undefined;
}

export const useStationStore = defineStore("stations", {
  state: () =>
    ({
      stations: [] as Station[],
      selectedStationId: undefined,
    } as StationsState),
  getters: {
    getSelectedStation: ({ selectedStationId, stations }) =>
      stations.find((station) => station.stationId === selectedStationId),
  },
  actions: {
    changeSelectedStation(stationId: string) {
      this.selectedStationId = this.stations.find(
        (station) => station.stationId === stationId
      )?.stationId;
    },

    async fetchUserStations(callback?: FetchCallbacks) {
      try {
        const result = await userApi.getStationsRaw();
        if (!result.raw.ok) {
          throwErrorByResponse(result.raw.status);
        }
        this.stations = await result.value();
        if (!this.selectedStationId && this.stations.length > 0) {
          this.changeSelectedStation(this.stations[0].stationId);
        }
        callback?.onSuccess?.call(this);
      } catch (error) {
        this.stations = [];
        callback?.onError?.call(this, unifyError(error));
      }
      return this.stations;
    },
  },
});
