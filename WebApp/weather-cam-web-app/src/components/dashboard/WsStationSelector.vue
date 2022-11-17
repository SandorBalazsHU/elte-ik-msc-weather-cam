<template>
  <v-card class="d-flex flex-column">
    <v-card-title class="d-flex pl-0 justify-space-between">
      <span class="data-title">Information </span>
      <v-btn
        :size="xs ? 'default' : 'large'"
        color="info"
        class="change-station-btn"
        variant="tonal"
        append-icon="mdi-access-point-network"
        elevation="2"
        >Change</v-btn
      >
    </v-card-title>
    <v-card-text class="station-data justify-space-around d-flex flex-column">
      <span>Name: {{ stationData.stationName }}</span>
      <span class="status-indicator"
        ><span>Status:</span> <v-badge :color="status === 200 ? 'green' : 'red'" inline> </v-badge
        ><span v-show="status === 200">Online</span>
        <span v-show="status !== 200">Offline</span></span
      >
      <span>Last active: {{ getRelativeTime(lastTimestamp) }}</span>
      <span>Time zone: GMT+1</span>
    </v-card-text>
  </v-card>
</template>

<script lang="ts" setup>
import type { Station } from "@/api/openapi/index.js";
import type HttpStatusCode from "@/utils/HttpStatusCode.js";
import { getRelativeTime } from "@/utils/Date.js";
import { xs } from "@/utils/Sizing.js";

defineProps<{ stationData: Station; lastTimestamp: number; status: HttpStatusCode }>();

const batteryBarColor = (percent: number) => {
  if (percent >= 50) return "green";
  if (percent < 50 && percent >= 20) return "yellow";
  return "red";
};
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

<style></style>
