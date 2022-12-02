<template>
  <v-sheet :elevation="3" rounded="rounded" :width="0" class="square flex-grow-1">
    <div class="measurement d-flex h-100 flex-column">
      <div style="height: 4px" class="w-100">
        <v-progress-linear v-show="loading" indeterminate></v-progress-linear>
      </div>
      <div class="d-flex justify-space-between data-title flex-grow-0">
        <span>{{ title }}</span>
        <v-icon v-if="icon">{{ icon }}</v-icon>
      </div>

      <div class="d-flex data flex-grow-1">
        <span class="measurement-data"> {{ data ? `${data} ${unitOfMeasure}` : "" }} </span>
      </div>
      <v-progress-linear
        v-show="bar"
        rounded
        class="no-border-top"
        :model-value="data"
        :color="bar?.color"
        :max="bar?.max"
        :min="bar?.min"
        height="25"
      ></v-progress-linear>
    </div>
  </v-sheet>
</template>

<script lang="ts" setup>
import { nextTick, onBeforeUnmount, onMounted, onUpdated } from "vue";
import textFit from "textfit";
import debounce from "@/utils/Debounce.js";
defineProps<{
  bar?: { min?: number; max?: number; color: string };
  icon?: string;
  title: string;
  data: number;
  unitOfMeasure?: string;
  loading: boolean;
}>();

onUpdated(() => {
  nextTick(() => {
    textFit(document.getElementsByClassName("measurement-data"), fitParams);
  });
});

const fitParams = {
  multiLine: true,
  widthOnly: true,
  reProcess: true,
  alignHoriz: true,
  alignVert: true,
};
const resizeHandler = debounce(() => {
  textFit(document.getElementsByClassName("measurement-data"), fitParams);
});
onMounted(() => {
  nextTick(() => {
    textFit(document.getElementsByClassName("measurement-data"), fitParams);
  });
  window.addEventListener("resize", resizeHandler);
});

onBeforeUnmount(() => window.removeEventListener("resize", resizeHandler));
</script>

<style scoped>
.square {
  aspect-ratio: 1;
  max-height: 200px;
}
.no-border-top {
  border-top-left-radius: 0px;
  border-top-right-radius: 0px;
}
.measurement-data {
  width: 100%;
  display: block;
}

.data {
  display: block;
  flex: none;
  font-weight: 500;
  hyphens: auto;
  letter-spacing: 0.0125em;
  min-width: 0;
  overflow-wrap: normal;
  overflow: hidden;
  overflow: hidden;
  padding: 0.75rem 0.75rem;
  text-overflow: ellipsis;
  text-overflow: ellipsis;
  text-transform: none;
  white-space: nowrap;
  word-spacing: -0.38rem;
  white-space: nowrap;
  word-break: normal;
  word-wrap: break-word;
}

.data-title {
  display: block;
  flex: none;
  font-size: 1.4rem;
  font-weight: 500;
  hyphens: auto;
  letter-spacing: 0.0125em;
  min-width: 0;
  overflow-wrap: normal;
  overflow: hidden;
  overflow: hidden;
  padding: 0.5rem 0.75rem;
  text-overflow: ellipsis;
  text-overflow: ellipsis;
  text-transform: none;
  white-space: nowrap;
  white-space: nowrap;
  word-break: normal;
  word-wrap: break-word;
}
</style>
