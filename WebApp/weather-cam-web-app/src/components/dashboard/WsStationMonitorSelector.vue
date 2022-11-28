<template>
  <v-card class="d-flex flex-column">
    <v-card-title class="d-flex pl-0 justify-space-between">
      <span class="data-title">Information </span>
      <ws-station-selector class="change-station-btn"></ws-station-selector>
    </v-card-title>
    <v-card-text class="station-data justify-space-around d-flex flex-column">
      <ws-status-indicator
        :loading="loadingStations"
        :status="200"
        :status-to-style-map="states"
      ></ws-status-indicator>
      <span v-if="!loadingStations">Name: {{ stationStore.getSelectedStation?.stationName }}</span>
      <span v-else>Name: <v-progress-circular indeterminate></v-progress-circular></span>

      <span>Last active: {{ getRelativeTime(lastTimestamp) }}</span>
      <span v-if="!loadingStations"
        >Time zone: GMT{{
          formatTimezone(
            stationStore.getSelectedStation?.stationTimezone ?? new Date().getTimezoneOffset() / 60
          )
        }}</span
      >
      <span v-else>Time zone: <v-progress-circular indeterminate></v-progress-circular></span>
    </v-card-text>
  </v-card>
</template>

<script lang="ts" setup>
import type { Station } from "@/api/openapi/index.js";
import type HttpStatusCode from "@/utils/HttpStatusCode.js";
import { getRelativeTime } from "@/utils/Date.js";
import { useStationStore } from "@/store/stations.js";
import { onMounted, ref } from "vue";
import WsStationSelector from "@/components/dashboard/WsStationSelector.vue";
import WsStatusIndicator from "@/components/WsStatusIndicator.vue";
import states from "@/utils/StationStates.js";

defineProps<{ stationData: Station; lastTimestamp: number; status: HttpStatusCode }>();

const formatTimezone = (timezone: number) => (timezone < 0 ? timezone : `+${timezone}`);
const stationStore = useStationStore();

const loadingStations = ref(false);

onMounted(() => {
  loadStations();
});

async function loadStations() {
  const stations = await stationStore.fetchUserStations();
  if (!stationStore.selectedStationId && stations.length > 0) {
    stationStore.changeSelectedStation(stations[0].stationId);
  }
}
</script>

<style scoped>
.v-badge {
  vertical-align: text-bottom;
}

.station-data {
  font-size: 1.3rem;
  line-height: 1.4 !important;
}

.data-title {
  display: block;
  flex: none;
  font-size: 1.4rem;
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
.change-station-btn {
  margin-top: auto;
  margin-bottom: auto;
}
</style>
