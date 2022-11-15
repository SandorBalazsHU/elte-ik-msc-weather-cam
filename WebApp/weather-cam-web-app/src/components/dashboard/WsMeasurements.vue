<template>
  <div class="measurement-pair gap d-flex justify-space-around flex-grow-1">
    <ws-measurement
      icon="mdi-thermometer "
      unit-of-measure="C°"
      :title="width < 1700 ? 'Temp.' : 'Temperature'"
      :data="measurement.temperature!"
    ></ws-measurement>
    <ws-measurement
      icon="mdi-gauge"
      unit-of-measure="hPa"
      title="Pressure"
      :data="measurement.pressure!"
    ></ws-measurement>
  </div>
  <div class="measurement-pair gap d-flex justify-space-around flex-grow-1">
    <ws-measurement
      :bar="{ color: 'blue' }"
      icon="mdi-water-percent "
      :unit-of-measure="'%'"
      :title="'Humidity'"
      :data="measurement.humidity!"
    ></ws-measurement>
    <ws-measurement
      :bar="{ color: batteryBarColor(measurement.battery || 0) }"
      icon="mdi-battery-40 "
      :unit-of-measure="'%'"
      :title="'Battery'"
      :data="measurement.battery!"
    ></ws-measurement>
  </div>
</template>

<script lang="ts" setup>
import type { Measurement } from "@/api/openapi/index.js";
import WsMeasurement from "@/components/dashboard/WsMeasurement.vue";
import { useWindow } from "@/utils/composables/WindowSize.js";

defineProps<{ measurement: Measurement }>();
const batteryBarColor = (percent: number) => {
  if (percent >= 50) return "green";
  if (percent < 50 && percent >= 20) return "yellow";
  return "red";
};

const { width } = useWindow();
</script>

<style scoped>
.measurement-pair {
  max-width: 50%;
  flex-wrap: wrap;
}
</style>

<style>
.data-title {
  display: block;
  flex: none;
  font-size: 1.5rem;
  font-weight: 500;
  hyphens: auto;
  letter-spacing: 0.0125em;
  min-width: 0;
  overflow-wrap: normal;
  overflow: hidden;
  overflow: hidden;
  padding: 0.5rem 0.75rem;
  text-overflow: ellipsis;
  text-overflow: ellipsis;
  text-transform: none;
  white-space: nowrap;
  white-space: nowrap;
  word-break: normal;
  word-wrap: break-word;
}
.gap {
  column-gap: max(1vw, 1rem);
  row-gap: max(1vw, 1rem);
}
</style>
