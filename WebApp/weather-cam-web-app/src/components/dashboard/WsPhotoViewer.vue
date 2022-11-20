<template>
  <v-sheet rounded="rounded" class="pa-1 mt-3">
    <div class="d-flex flex-column h-100 justify-space-between">
      <div class="d-flex justify-space-between pa-2 pb-0 pt-1">
        <span style="" class="text-h5">Photos</span>
        <v-select
          :active="true"
          style="max-width: fit-content; min-width: 20%"
          label="Select interval"
          :items="durations"
          v-model="selecedDuration"
          density="compact"
          variant="solo"
        >
        </v-select>
      </div>
      <div style="">
        <v-carousel :height="calcPictureSize" hide-delimiters show-arrows="hover">
          <v-carousel-item
            contain
            src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
          ></v-carousel-item>

          <v-carousel-item
            contain
            src="https://cdn.vuetifyjs.com/images/cards/hotel.jpg"
          ></v-carousel-item>

          <v-carousel-item
            contain
            src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
          ></v-carousel-item>
        </v-carousel>

        <ws-player-controller
          class="my-1 px-2"
          :all_pictures="500"
          :current_picture="400"
        ></ws-player-controller>
      </div>
    </div>
  </v-sheet>
</template>

<style scoped></style>

<script setup lang="ts">
import { ref } from "vue";
import { calcPictureSize } from "@/utils/Sizing.js";
import WsPlayerController from "@/components/WsPlayerController.vue";
const page = ref(1);
const durations = [
  "Last hour",
  "Last 12 hours",
  "Last 24 hours",
  "Last week",
  "Last month",
  "Last year",
  "All",
];

const selecedDuration = ref("Last 24 hours");
const numberOfPhotos = ref(10);
</script>
