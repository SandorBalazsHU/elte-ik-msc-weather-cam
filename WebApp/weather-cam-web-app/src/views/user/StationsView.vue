<template>
  <main>
    <div class="mb-3 w-100 gap flex-wrap d-flex justify-center">
      <ws-station-selector
        class="break-station-selector station-selector"
        :status="200"
        :last-timestamp="1668297328"
        :station-data="{
          stationId: 'fdd19b30-62e5-11ed-81ce-0242ac120002',
          stationName: 'Home station',
          stationTimezone: 1,
        }"
      ></ws-station-selector>
      <ws-measurements
        :measurement="{
          battery: 32,
          humidity: 66,
          pressure: 1012,
          temperature: 24.1,
          timestamp: 1668510959,
        }"
      >
      </ws-measurements>
    </div>
    <v-divider></v-divider>
    <div class="data-visualization">
      <ws-station-measurement-chart
        class="data-visualization-element mt-3"
      ></ws-station-measurement-chart>
      <v-sheet rounded="rounded" style="min-height: 475px" class="data-visualization-element mt-3"
        >Data here</v-sheet
      >
    </div>
  </main>
</template>

<script setup lang="ts">
import WsMeasurements from "@/components/dashboard/WsMeasurements.vue";
import WsStationSelector from "@/components/dashboard/WsStationSelector.vue";
import WsStationMeasurementChart from "@/components/dashboard/charts/WsStationMeasurementsChart.vue";
</script>

<style>
@media screen and (max-width: 1100px) {
  .break-station-selector {
    width: 100% !important;
  }
}

.data-visualization {
  display: flex;
  column-gap: 1rem;
  flex-wrap: wrap;
}

.data-visualization-element {
  flex-grow: 1;
  flex-basis: 500px;
  min-width: 320px;
}

@media screen and (max-width: 800px) {
  .measurement-pair {
    min-width: 100%;
  }
}

@media screen and (max-width: 1400px) and (min-width: 1100px) {
  .break-station-selector {
    width: 100% !important;
  }
}
</style>
