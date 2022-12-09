<template>
  <div>
    <v-dialog :width="calcFormSize" v-model="dialog">
      <template v-slot:activator="{ props }">
        <v-btn v-if="!isMobile" color="error" size="large" v-bind="props" prepend-icon="mdi-delete"
          >Delete</v-btn
        >
        <v-btn v-else color="error" size="large" v-bind="props" icon="mdi-delete"></v-btn>
      </template>

      <v-card>
        <v-card-title> Delete {{ station.stationName }} </v-card-title>
        <v-card-text>
          Are you sure you want to delete station: {{ station.stationName }}?
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn color="error" @click="deleteStationHandler">Yes</v-btn>
          <v-btn color="info" @click="cancelDeleteStationHandler">Cancel</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import type { Station } from "@/api/openapi/index.js";
import { calcFormSize, xs, isMobile } from "@/utils/Sizing.js";

function deleteStationHandler() {
  //TODO
  console.log("delete");
}

function cancelDeleteStationHandler() {
  dialog.value = false;
}
const props = defineProps<{ station: Station }>();
const dialog = ref(false);
</script>
