<template>
  <div class="text-center">
    <v-dialog @update:model-value="closeDialogHandler" v-model="dialog">
      <template v-slot:activator="{ props }">
        <v-btn
          @click="openDialogHandler"
          v-bind="props"
          v-if="!isMobile"
          color="green"
          size="large"
          prepend-icon="mdi-plus"
          >Add Station</v-btn
        >
        <v-btn v-bind="props" v-if="isMobile" color="green" size="default" icon="mdi-plus"></v-btn>
      </template>

      <v-card :max-height="800" :loading="creatingStation" :width="calcFormSize" class="centered">
        <v-card-title> Create a new station </v-card-title>
        <template v-if="apiKey.length === 0">
          <v-card-text class="px-3">
            <v-text-field
              :rules="isRequired('Field station name must not be empty!')"
              label="Station name"
              validate-on="blur"
              v-model="stationName"
            ></v-text-field>
          </v-card-text>
          <v-card-actions class="justify-end">
            <v-btn
              :disabled="stationName.length === 0"
              color="green"
              @click="stationCreationHandler"
              >Create</v-btn
            >
            <v-btn color="error" @click="closeDialogHandler">Cancel</v-btn>
          </v-card-actions>
        </template>
        <ws-alert-container
          class="mx-2"
          id="station-create-error"
          :max-displayed="1"
          :max-stored="1"
        ></ws-alert-container>
        <v-sheet color="">
          <v-row class="gutter-x pa-2 pt-0 justify-center" v-show="apiKey.length !== 0" no-gutters>
            <v-col
              :style="{ height: Math.min(calcFormSize, 300) + 'px !important' }"
              class="v-col-auto"
            >
              <qrcode-vue
                :size="Math.min(calcFormSize, 300)"
                id="station-qr-code"
                :value="apiKey"
              ></qrcode-vue>
            </v-col>
            <v-col class="v-col-auto d-flex flex-column justify-space-between">
              <div>
                <v-alert
                  class="w-100"
                  type="success"
                  multi-line
                  text="Successfully created new station!"
                ></v-alert>
              </div>
              <div class="text-h5 my-2">
                <p>Name: {{ stationName }}</p>
                <p>API Key: {{ apiKey }}</p>
              </div>
              <div>
                <v-alert class="w-100" type="warning" multi-line
                  >Make sure to save the generated API key!<br />
                  Once you close this you wont see it again!</v-alert
                >
              </div>
            </v-col>
          </v-row>
        </v-sheet>
        <v-card-actions v-if="apiKey.length !== 0" class="justify-end">
          <v-btn :disabled="stationName.length === 0" color="info" @click="closeDialogHandler"
            >Close</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { isMobile, calcFormSize } from "@/utils/Sizing.js";
import { userApi } from "@/api/apis.js";
import WsAlertContainer from "@/components/WsAlertContainer.vue";
import { throwErrorByResponse, unifyError, type HttpError } from "@/api/errors/CustomErrors.js";
import { useAlertStore } from "@/store/alert.js";
import { useStationStore } from "@/store/stations.js";
import type { StationApiKey } from "@/api/openapi/index.js";
import { isRequired } from "@/utils/FormValidators.js";
import QrcodeVue from "qrcode.vue";

const stationName = ref("");
const apiKey = ref("");
const dialog = ref(false);
const creatingStation = ref(false);
const alertStore = useAlertStore();

const openDialogHandler = () => ((stationName.value = ""), (apiKey.value = ""));
function closeDialogHandler() {
  dialog.value = false;
  stationName.value = "";
  apiKey.value = "";
}
const onStationCreateError = (error: HttpError) =>
  alertStore.addAlert("station-create-error", error);

function onStationCreateSuccess(response: StationApiKey) {
  apiKey.value = response.apiKey;
}

async function stationCreationHandler() {
  alertStore.clearAlerts("station-create-error");
  creatingStation.value = true;
  apiKey.value = "";
  try {
    const result = await userApi.addStationsRaw({ stationName: stationName.value });
    if (!result.raw.ok) {
      throwErrorByResponse(result.raw.status);
    }
    useStationStore().fetchUserStations();
    onStationCreateSuccess(await result.value());
  } catch (error) {
    onStationCreateError(unifyError(error));
  }
  creatingStation.value = false;
}
</script>

<style scoped>
.centered {
  margin: auto;
}
.gutter-x {
  column-gap: 1rem;
  row-gap: 1rem;
}
</style>
