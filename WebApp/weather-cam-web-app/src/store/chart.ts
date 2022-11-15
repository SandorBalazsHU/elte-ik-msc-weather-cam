import { defineStore } from "pinia";
import type { Measurement } from "@/api/openapi/index.js";
import { useUserStore } from "./user.js";

export interface ChartState {
  measurements: Measurement[];
}

export const useThemeStore = defineStore("chart", {
  state: () =>
    ({
      measurements: [],
    } as ChartState),
  getters: {
    battery: ({ measurements }) => measurements.map((measurement) => measurement.battery),
    temperature: ({ measurements }) => measurements.map((measurement) => measurement.temperature),
    pressure: ({ measurements }) => measurements.map((measurement) => measurement.pressure),
    humidity: ({ measurements }) => measurements.map((measurement) => measurement.humidity),
  },
  actions: {},
});
