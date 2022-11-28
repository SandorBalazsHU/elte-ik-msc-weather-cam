<template>
  <v-row class="pr-2" justify="end">
    <v-dialog
      @update:model-value="(value:boolean)=>openDialogHandler(value)"
      :width="calcFormSize"
      v-model="dialog"
    >
      <template v-slot:activator="{ props }">
        <v-btn
          v-show="!xs"
          v-bind="props"
          color="info"
          variant="tonal"
          append-icon="mdi-access-point-network"
          elevation="2"
          >Change</v-btn
        >
        <v-btn
          v-show="xs"
          v-bind="props"
          color="info"
          variant="tonal"
          icon="mdi-access-point-network"
          elevation="2"
        ></v-btn>
      </template>
      <v-card :loading="fetchingStations">
        <v-card-title>
          <span class="text-h5">Select station</span>
        </v-card-title>
        <v-card-text class="px-2">
          <v-container>
            <v-row>
              <v-col class="pa-0" cols="12">
                <v-autocomplete
                  :disabled="fetchingStations"
                  :items="
                    stations.stations
                      .filter(
                        (station) => stations.getSelectedStation?.stationId !== station.stationId
                      )
                      .map((station) => station.stationName)
                  "
                  :label="fetchingStations ? 'Fetching...' : 'Stations'"
                  v-model="currentlySelectedStation"
                ></v-autocomplete>
              </v-col>
            </v-row>
          </v-container>
          <small class="px-3">Select the station you want to monitor.</small>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="error" variant="text" @click="cancelHander"> Cancel </v-btn>
          <v-btn color="primary" variant="text" @click="saveHandler"> Save </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { xs, calcFormSize } from "@/utils/Sizing.js";
import { useStationStore } from "@/store/stations.js";
const stations = useStationStore();
const dialog = ref(false);
const currentlySelectedStation = ref(stations.getSelectedStation?.stationName);
const fetchingStations = ref(true);

const cancelHander = () => (dialog.value = false);
const saveHandler = () => {
  const selectedId = stations.stations.find(
    (station) => station.stationName === currentlySelectedStation.value
  )?.stationId;
  selectedId && stations.changeSelectedStation(selectedId);
  dialog.value = false;
};

function openDialogHandler(open: boolean) {
  if (!open) return;
  fetchingStations.value = true;
  stations.fetchUserStations().then(() => (fetchingStations.value = false));
}
</script>
