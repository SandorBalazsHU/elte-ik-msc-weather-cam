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

      <v-pagination :length="numberOfPhotos"></v-pagination>
    </div>
  </v-sheet>
</template>

<script setup lang="ts">
import { ref } from "vue";
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
