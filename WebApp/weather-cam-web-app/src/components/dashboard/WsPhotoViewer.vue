<template>
  <v-sheet :elevation="3" rounded="rounded" class="pa-1 mt-3">
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
        <v-carousel
          :cycle="cycle"
          :interval="1000"
          eager
          v-model="pictureIndex"
          ref="carousel"
          :height="calcPictureSize"
          hide-delimiters
          show-arrows="hover"
        >
          <v-carousel-item
            :src="`http://www.wrh.noaa.gov/images/slc/camera/darren2.${n}.jpg`"
            eager
            :key="n"
            v-for="n in 100"
            contain
          ></v-carousel-item>
        </v-carousel>

        <ws-player-controller
          :playing="cycle"
          @click_play="() => (cycle = !cycle)"
          class="my-1 px-2"
          :all_pictures="carouselItems?.length || 0"
          :current_picture="pictureIndex"
        ></ws-player-controller>
      </div>
    </div>
  </v-sheet>
</template>

<style scoped>
.v-window__container {
  transition: none !important;
}
.v-window-x-transition-enter-active {
  transition: none !important;
}
</style>

<script setup lang="ts">
import { computed, nextTick, onMounted, ref } from "vue";
import { calcPictureSize } from "@/utils/Sizing.js";
import WsPlayerController from "@/components/WsPlayerController.vue";
import { VCarousel, VCarouselItem } from "vuetify/components";
const durations = [
  "Last hour",
  "Last 12 hours",
  "Last 24 hours",
  "Last week",
  "Last month",
  "Last year",
  "All",
];

onMounted(() => {});

const carousel = ref<VCarousel | null>(null);
const carouselItems = computed(() => {
  return (carousel.value?.$el as HTMLElement)?.getElementsByClassName("v-carousel-item") ?? 0;
});

const currentIndex = computed(() =>
  Array.prototype.slice
    .call(carouselItems)
    .findIndex((e: HTMLElement) => e.classList.contains("v-window-item--active"))
);
const selecedDuration = ref("Last 24 hours");
const pictureIndex = ref(0);
const cycle = ref(false);
</script>
