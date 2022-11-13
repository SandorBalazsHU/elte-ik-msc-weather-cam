<template>
  <div class="w-100 gap flex-wrap d-flex justify-center">
    <ws-station-selector
      class="add-margin break-station-selector station-selector"
      :status="200"
      :last-timestamp="1668297328"
      :station-data="{
        stationId: 'fdd19b30-62e5-11ed-81ce-0242ac120002',
        stationName: 'Home station',
      }"
      style="width: 36%"
    ></ws-station-selector>
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
  </div>
</template>

<script lang="ts" setup>
import type { Measurement } from "@/api/openapi/index.js";
import WsMeasurement from "@/components/dashboard/WsMeasurement.vue";
import WsStationSelector from "@/components/dashboard/WsStationSelector.vue";
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
.square {
  aspect-ratio: 1;
}
.gap {
  column-gap: max(2vw, 1rem);
  row-gap: max(1vw, 1rem);
}
.measurement-pair {
  min-width: 320px;
  max-width: 32%;
  max-height: max(max(15vh, 10vw), 175px);
}
@media screen and (max-width: 688px) {
  .measurement-pair {
    min-width: 100% !important;
  }
}

@media screen and (max-width: 1100px) {
  .measurement-pair {
    max-width: 50%;
  }

  .break-station-selector {
    width: 100% !important;
  }

  .station-data {
    row-gap: max(3vh, 3vw) !important;
  }
}

@media screen and (max-width: 1400px) {
  .station-selector {
    max-height: max(40vh, 40vw) !important;
  }
}

@media screen and (max-width: 1400px) and (min-width: 1100px) {
  .measurement-pair {
    max-width: 50%;
  }

  .break-station-selector {
    width: 100% !important;
  }
}

@media screen and (max-width: 1280px) {
  .measurement-pair {
    max-width: 50%;
  }
  .station-selector {
    max-height: max(40vh, 40vw) !important;
  }

  .station-data {
    row-gap: 3rem !important;
  }
}

.station-selector {
  max-height: max(max(15vh, 10vw), 175px);
}
</style>

<style>
@media screen and (max-width: 1400px) {
  .station-data {
    row-gap: min(1vh, 1vw) !important;
  }
}
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
</style>
