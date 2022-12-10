<template>
  <v-sheet :elevation="3" rounded="rounded" class="d-flex">
    <div class="flex-grow-1">
      <div class="d-flex justify-space-between pa-2 pb-0 pt-1">
        <span style="" class="text-h5">Analytics</span
        ><v-select
          style="max-width: fit-content; min-width: 20%"
          label="Select interval"
          :items="durations.map((d) => d.text)"
          v-model="selecedDuration"
          @update:model-value="changeDurationHandler"
          density="compact"
          variant="solo"
        ></v-select>
      </div>
      <LineChart v-bind="lineChartProps" />
    </div>
  </v-sheet>
</template>

<script lang="ts" setup>
import { computed, onMounted, ref } from "vue";
import { LineChart, useLineChart } from "vue-chart-3";
import { Chart, type ChartData, type ChartOptions, registerables } from "chart.js";
import { useThemeStore } from "@/store/theme.js";
import { calcPictureSize } from "@/utils/Sizing.js";
import { durations, type DurationText } from "@/store/chart.js";

import { useChartStore, type DurationType } from "@/store/chart.js";
import { storeToRefs } from "pinia";
const chartStore = useChartStore();
Chart.register(...registerables);

function changeDurationHandler(duration: DurationType) {
  console.log(durations);
  chartStore.changeDuration(durations.find((d) => d.text === (duration as unknown))!);
}
const selecedDuration = ref<DurationText>("Last 24 hours");

onMounted(() => {
  chartStore.changeDuration(durations[2]);
});

const testData = computed<ChartData<"line">>(() => ({
  labels: chartStore.generateLabels,
  datasets: [
    {
      data: chartStore.battery,
      borderColor: "green",
      backgroundColor: "green",
      label: "Battery",
    },
    {
      data: chartStore.humidity,
      borderColor: "blue",
      backgroundColor: "blue",
      label: "Humidity",
    },
    {
      data: chartStore.pressure,
      borderColor: "red",
      backgroundColor: "red",
      label: "Pressure",
    },
    {
      data: chartStore.temperature,
      borderColor: "yellow",
      backgroundColor: "yellow",
      label: "Temperature",
    },
  ],
}));

const gridColor = computed(() => {
  return useThemeStore().theme === "light" ? Chart.defaults.borderColor.toString() : "grey";
});

const options = computed<ChartOptions<"line">>(() => ({
  responsive: true,
  maintainAspectRatio: false,
  animation: {
    duration: 0,
  },
  scales: {
    x: {
      grid: {
        color: gridColor.value,
      },

      position: "bottom",
    },
    y: {
      grid: {
        color: gridColor.value,
      },
      position: "left",
    },
  },
  plugins: {
    legend: {
      position: "bottom",
    },
  },
}));

const { lineChartProps, lineChartRef } = useLineChart({
  chartData: testData,
  height: calcPictureSize.value,
  options,
});
</script>
