import { userApi } from "@/api/apis.js";
import { throwErrorByResponse } from "@/api/errors/CustomErrors.js";
import type { Station } from "@/api/openapi/index.js";
import { defineStore } from "pinia";
import { useAlertStore } from "./alert.js";

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

    async fetchUserStations(propagateError: boolean = false) {
      try {
        const result = await userApi.getStationsRaw();
        if (!result.raw.ok) {
          throwErrorByResponse(result.raw.status);
        }
        this.stations = await result.value();
        if (!this.selectedStationId && this.stations.length > 0) {
          this.changeSelectedStation(this.stations[0].stationId);
        }
      } catch (error) {
        this.stations = [];
        if (propagateError) throw error;
      }
      return this.stations;
    },
  },
});
