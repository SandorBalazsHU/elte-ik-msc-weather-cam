<template>
  <v-expansion-panel :disable="fetchingApiKey" :elevation="3">
    <v-expansion-panel-title class="text-h5">
      <v-icon class="active-icon">mdi-chevron-down</v-icon>
      <span class="name-text text-truncate">{{ station.stationName }}</span>
      <template v-slot:actions>
        <template v-if="!isMobile">
          <ws-edit-station-dialog class="mx-2" :station="props.station"></ws-edit-station-dialog>
          <ws-delete-station-dialog :station="props.station"> </ws-delete-station-dialog>
        </template>
        <template v-else>
          <template v-if="!xs">
            <ws-edit-station-dialog class="mr-2" :station="props.station"></ws-edit-station-dialog>
            <ws-delete-station-dialog :station="props.station"> </ws-delete-station-dialog>
          </template>
        </template>
      </template>
    </v-expansion-panel-title>
    <v-expansion-panel-text class="text-h6 text-wrap">
      <ws-status-indicator
        prepend-text="Status:"
        :status-to-style-map="stationStates"
        :status="200"
      ></ws-status-indicator>
      <div>ID: {{ station.stationId }}</div>
      <div style="word-break: break-all">API Key: {{ apiKey }}</div>
      <div v-if="xs" class="d-flex justify-end mt-2">
        <ws-edit-station-dialog class="mr-2" :station="props.station"></ws-edit-station-dialog>
        <ws-delete-station-dialog :station="props.station"></ws-delete-station-dialog>
      </div>
    </v-expansion-panel-text>
  </v-expansion-panel>
</template>

<script lang="ts" setup>
import type { Station } from "@/api/openapi/index.js";
import { xs, isMobile } from "@/utils/Sizing.js";
import stationStates from "@/utils/StationStates.js";
import { stationsApi } from "@/api/apis.js";
import { onMounted, ref } from "vue";
import { useAlertStore } from "@/store/alert.js";
import WsStatusIndicator from "./WsStatusIndicator.vue";
import WsDeleteStationDialog from "@/components/WsDeleteStationDialog.vue";
import WsEditStationDialog from "@/components/WsEditStationDialog.vue";
import { calcFormSize } from "@/utils/Sizing.js";

const props = defineProps<{ station: Station }>();
const fetchingApiKey = ref(false);
const apiKey = ref("");
onMounted(() => {
  fetchApiKey();
});

function fetchApiKey() {
  fetchingApiKey.value = true;
  stationsApi
    .getPartialApiKey({ stationId: props.station.stationId })
    .then((key) => ((apiKey.value = key), (fetchingApiKey.value = false)))
    .catch((err) => {
      useAlertStore().addAlert("station-viewer-container", err);
    });
}
</script>

<style scoped>
.v-expansion-panel-title[aria-expanded="true"] > .active-icon::before {
  content: "\F0143";
}

.v-expansion-panel-title[aria-expanded="false"] > .inactive-icon::before {
  content: "\F0142";
}
</style>
