import type { Station } from "@/api/openapi/index.js";
import { defineStore } from "pinia";

interface StationsState {
  stations: Station[];
  selectedStationId: number;
}

export const useThemeStore = defineStore("stations", {
  state: () => ({} as StationsState),
  getters: {},
  actions: {},
});
