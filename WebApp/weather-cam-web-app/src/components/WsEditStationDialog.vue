<template>
  <div>
    <v-dialog :width="calcFormSize" v-model="dialog">
      <template v-slot:activator="{ props }">
        <v-btn v-if="!isMobile" color="info" size="large" v-bind="props" prepend-icon="mdi-wrench"
          >Edit</v-btn
        >
        <v-btn v-else color="info" size="large" v-bind="props" icon="mdi-wrench"></v-btn>
      </template>

      <v-card>
        <v-card-title> Edit {{ station.stationName }} </v-card-title>
        <v-card-text>
          <v-text-field
            :rules="isRequired('Field station name must not be empty!')"
            label="Station name"
            validate-on="blur"
            v-model="newStationName"
          ></v-text-field>
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn color="green" @click="editStationHandler">Save</v-btn>
          <v-btn color="info" @click="cancelEditStationHandler">Cancel</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import type { Station } from "@/api/openapi/index.js";
import { calcFormSize, isMobile, xs } from "@/utils/Sizing.js";
import { isRequired } from "@/utils/FormValidators.js";
import { stationsApi, userApi } from "@/api/apis.js";
function editStationHandler() {}

function cancelEditStationHandler() {
  dialog.value = false;
}
const props = defineProps<{ station: Station }>();

const newStationName = ref(props.station.stationName);
const dialog = ref(false);
</script>
