<template>
  <v-card class="d-flex flex-column">
    <v-card-title class="data-title d-flex justify-space-between">
      <span>Monitoring </span>
      <v-btn
        size="large"
        color="info"
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
      <span>Last active: {{ getRelativeTime(1668336494) }}</span>
      <span>Time zone: GMT+1</span>
    </v-card-text>
  </v-card>
</template>

<script lang="ts" setup>
import type { Station } from "@/api/openapi/index.js";
import type HttpStatusCode from "@/utils/HttpStatusCode.js";
import { getRelativeTime } from "@/utils/Date.js";
defineProps<{ stationData: Station; lastTimestamp: number; status: HttpStatusCode }>();
</script>

<style scoped>
.v-badge {
  line-height: 0;
  vertical-align: bottom;
}

.station-data {
  font-size: 1.35rem;
}
</style>

<style></style>
