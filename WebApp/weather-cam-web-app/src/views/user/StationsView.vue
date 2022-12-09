<template>
  <header class="text-h4 w-100 mb-2 d-flex justify-space-between">
    <div>
      <span>My stations</span>
      <v-icon size="small" class="ml-1">mdi-access-point-network</v-icon>
    </div>
    <ws-create-station-dialog></ws-create-station-dialog>
  </header>
  <v-divider class="my-2"></v-divider>
  <ws-station-panel-container>
    <v-sheet v-show="loadingStations" :elevation="3" rounded="rounded" class="w-100 px-4 py-4">
      <v-progress-circular class="ml-2" indeterminate></v-progress-circular>
      <span class="ml-2">Fetching stations...</span>
    </v-sheet>
    <ws-alert-container
      id="station-viewer-container"
      :max-displayed="1"
      :max-stored="1"
    ></ws-alert-container>

    <ws-station-panel
      class="my-1"
      v-show="!loadingStations"
      v-bind:key="station.stationId"
      v-bind="{ station }"
      v-for="station in stationsStore.stations"
    ></ws-station-panel>
  </ws-station-panel-container>
</template>

<script setup lang="ts">
import WsStationPanel from "@/components/WsStationPanel.vue";
import { useStationStore } from "@/store/stations.js";
import { isMobile } from "@/utils/Sizing.js";
import { onMounted, ref } from "vue";
import WsStationPanelContainer from "@/components/WsStationPanelContainer.vue";
import WsAlertContainer from "@/components/WsAlertContainer.vue";
import { useAlertStore } from "@/store/alert.js";
import type { HttpError } from "@/api/errors/CustomErrors.js";
import WsCreateStationDialog from "@/components/WsCreateStationDialog.vue";

const stationsStore = useStationStore();
const loadingStations = ref(false);

const onStationFetchError = (error: HttpError) => {
  loadingStations.value = false;
  useAlertStore().addAlert("station-viewer-container", error);
};

const onStationFetchSuccess = () => (loadingStations.value = false);

onMounted(() => {
  loadingStations.value = true;
  stationsStore.fetchUserStations({
    onError: onStationFetchError,
    onSuccess: onStationFetchSuccess,
  });
});
</script>
<style scoped></style>
