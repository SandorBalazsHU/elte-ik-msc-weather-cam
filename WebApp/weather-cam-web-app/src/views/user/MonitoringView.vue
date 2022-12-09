<template>
  <main>
    <ws-alert-container
      :max-displayed="1"
      :max-stored="1"
      id="monitor-error-container"
    ></ws-alert-container>
    <div class="wrapper mb-3 w-100 flex-wrap d-flex justify-center">
      <ws-current-station-info
        class="station-selector"
        :last-timestamp="latestMeasurement.timestamp"
      ></ws-current-station-info>
      <ws-measurement
        :loading="loadingMeasurements"
        class="measurement-display"
        icon="mdi-thermometer "
        unit-of-measure="C°"
        :title="sm || xs ? 'Temp.' : 'Temperature'"
        :data="latestMeasurement.temperature"
      ></ws-measurement>

      <ws-measurement
        :loading="loadingMeasurements"
        class="measurement-display"
        icon="mdi-gauge"
        unit-of-measure="hPa"
        title="Pressure"
        :data="latestMeasurement.pressure"
      ></ws-measurement>

      <ws-measurement
        :loading="loadingMeasurements"
        class="measurement-display"
        :bar="{ color: 'blue' }"
        icon="mdi-water-percent "
        :unit-of-measure="'%'"
        :title="'Humidity'"
        :data="latestMeasurement.humidity"
      ></ws-measurement>
      <ws-measurement
        :loading="loadingMeasurements"
        class="measurement-display"
        :data="latestMeasurement.battery"
        :bar="{ color: batteryBarColor(latestMeasurement.battery) }"
        icon="mdi-battery-40 "
        :unit-of-measure="'%'"
        :title="'Battery'"
      ></ws-measurement>
    </div>
    <v-divider></v-divider>
    <div class="data-visualization mb-3">
      <ws-station-measurement-chart
        class="data-visualization-element pa-1 mt-3"
      ></ws-station-measurement-chart>
      <ws-photo-viewer class="data-visualization-element pa-1 mt-3"> </ws-photo-viewer>
    </div>
    <v-divider></v-divider>
  </main>
</template>

<script setup lang="ts">
import { sm, xs } from "@/utils/Sizing.js";
import WsMeasurement from "@/components/dashboard/WsMeasurement.vue";
import WsCurrentStationInfo from "@/components/dashboard/WsStationMonitorSelector.vue";
import WsPhotoViewer from "@/components/dashboard/WsPhotoViewer.vue";
import WsStationMeasurementChart from "@/components/dashboard/charts/WsStationMeasurementsChart.vue";
import WsAlertContainer from "@/components/WsAlertContainer.vue";
import { onMounted, ref } from "vue";
import { useMeasurementStore } from "@/store/measurements.js";
import { useAlertStore } from "@/store/alert.js";
import type { HttpError } from "@/api/errors/CustomErrors.js";
import { useStationStore } from "@/store/stations.js";
import { storeToRefs } from "pinia";
import { useTimeoutPoll } from "@vueuse/core";

const stationsStore = useStationStore();
const measurementsStore = useMeasurementStore();
const { latestMeasurement } = storeToRefs(measurementsStore);
const loadingMeasurements = ref(false);
const onFetchMeasurementSuccess = () => {
  timeout.resume();
  useAlertStore().clearAlerts("monitor-error-container");
  loadingMeasurements.value = false;
};
const onFetchMeasurementError = (error: HttpError) => {
  timeout.resume();
  useAlertStore().addAlert("monitor-error-container", error);
  loadingMeasurements.value = false;
};
const batteryBarColor = (percent: number) => {
  if (percent >= 50) return "green";
  if (percent < 50 && percent >= 20) return "yellow";
  return "red";
};

const fetchCallbacks = {
  onError: onFetchMeasurementError,
  onSuccess: onFetchMeasurementSuccess,
};
async function loadMeasurements() {
  loadingMeasurements.value = true;

  await measurementsStore.fetchLatestMeasurement(stationsStore.selectedStationId!, fetchCallbacks);
  loadingMeasurements.value = false;
}

const timeout = useTimeoutPoll(loadMeasurements, 10000);

stationsStore.$subscribe(() => {
  loadingMeasurements.value = true;
  measurementsStore
    .fetchLatestMeasurement(stationsStore.selectedStationId!, fetchCallbacks)
    .then(() => (loadingMeasurements.value = false));
});

onMounted(() => {
  loadMeasurements();
});
</script>

<style scoped>
.data-visualization {
  display: flex;
  flex-wrap: wrap;
  column-gap: var(--grid-spacing);
  row-gap: var(--grid-spacing);
}
.wrapper {
  column-gap: var(--grid-spacing);
  row-gap: var(--grid-spacing);
}

.measurement-display {
  flex-grow: calc(2 / 12);
  flex-basis: 120px;
}

.data-visualization-element {
  flex-grow: 0.5;
  flex-basis: calc(50% - var(--grid-spacing));
  max-width: calc(50%);
  column-gap: var(--grid-spacing);
}

@media screen and (max-width: 1000px) {
  .data-visualization-element {
    flex-grow: 1;
    flex-basis: 100%;
    max-width: 100%;
  }
}

.station-selector {
  flex-grow: calc(1 / 3);
  flex-basis: 33%;
}

@media screen and (max-width: 1500px) {
  .measurement-display {
    flex-grow: 0.25;
    flex-basis: 0;
  }

  .station-selector {
    flex-grow: 1;
    flex-basis: 100%;
  }
}

@media screen and (max-width: 700px) {
  .measurement-display {
    flex-grow: 0.5;
    flex-basis: calc(50% - var(--grid-spacing));
  }
}

@media screen and (max-width: 350px) {
  .measurement-display {
    flex-grow: 1 !important;
    flex-basis: 100% !important;
  }
}
</style>

<style>
:root {
  --grid-spacing: max(1vh, 1vw);
}
</style>
