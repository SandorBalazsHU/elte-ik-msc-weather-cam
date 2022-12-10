import { defineStore } from "pinia";
import type { Measurement } from "@/api/openapi/index.js";
import { useUserStore } from "./user.js";
import { measurementApi } from "@/api/apis.js";
import { useStationStore } from "./stations.js";
export const durations = [
  { text: "Last hour", seconds: 3600 },
  { text: "Last 12 hours", seconds: 3600 * 12 },
  { text: "Last 24 hours", seconds: 3600 * 24 },
  { text: "Last week", seconds: 3600 * 24 * 7 },
  { text: "Last month", seconds: 3600 * 24 * 30 },
  { text: "Last year", seconds: 3600 * 24 * 365 },
  { text: "All", seconds: Number.MAX_SAFE_INTEGER },
] as const; // TS3.4 syntax

export type DurationText =
  | "Last hour"
  | "Last 12 hours"
  | "Last 24 hours"
  | "Last week"
  | "Last month"
  | "Last year"
  | "All";
export type DurationType = typeof durations[number];

export interface ChartState {
  measurements: Measurement[];
  currentDurationBegin: number;
  currentDurationEnd: number;
}

export const useChartStore = defineStore("chart", {
  state: () =>
    ({
      measurements: [
        { battery: 66, humidity: 58, temperature: 14.3, pressure: 1014.25, timestamp: 1549312452 },
      ],
    } as ChartState),
  getters: {
    generateLabels: ({ measurements, currentDurationBegin, currentDurationEnd }) => {
      const interval = (currentDurationBegin - currentDurationEnd) / measurements.length;
      return measurements
        .map((_, ind) => new Date(currentDurationBegin + interval * ind).toDateString())
        .reverse();
    },
    battery: ({ measurements }) => measurements.map((measurement) => measurement.battery),
    temperature: ({ measurements }) => measurements.map((measurement) => measurement.temperature),
    pressure: ({ measurements }) => measurements.map((measurement) => measurement.pressure),
    humidity: ({ measurements }) => measurements.map((measurement) => measurement.humidity),
    timestamp: ({ measurements }) =>
      measurements.map((measurement) => new Date(measurement.timestamp).toDateString()),
  },
  actions: {
    async changeDuration(duration: DurationType) {
      console.log(duration);
      const dateBeginNormal = (duration: DurationType) => {
        return Number(duration.text === "All" ? 0 : Date.now() - duration.seconds * 1000);
      };
      try {
        this.currentDurationBegin = dateBeginNormal(duration);
        this.currentDurationEnd = Date.now();
        const res = await measurementApi.getStationMeasurementsByQueryRaw({
          stationId: useStationStore().selectedStationId,
          dateEnd: Math.floor(this.currentDurationEnd / 1000),
          dateBegin: Math.floor(this.currentDurationBegin / 1000),
        });
        this.measurements = await res.value();
      } catch (error) {
        console.log(error);
      }
    },
  },
});
