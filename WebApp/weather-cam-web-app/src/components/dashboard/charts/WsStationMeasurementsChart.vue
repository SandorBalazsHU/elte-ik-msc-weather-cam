<template>
  <v-sheet :elevation="3" rounded="rounded" class="d-flex">
    <div class="flex-grow-1">
      <div class="d-flex justify-space-between pa-2 pb-0 pt-1">
        <span style="" class="text-h5">Analytics</span
        ><v-select
          :active="true"
          style="max-width: fit-content; min-width: 20%"
          label="Select interval"
          :items="durations"
          v-model="selecedDuration"
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

Chart.register(...registerables);
const dataValues = ref([30, 40, 60, 70, 5]);
const dataValues2 = ref([12, 33, 55, 17, 66]);
const dataValues3 = ref([632, 843, 815, 717, 870]);
const dataValues4 = ref([1, 5, 7, 1, 23]);
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
const testData = computed<ChartData<"line">>(() => ({
  labels: [1, 2, 3, 4, 5],
  datasets: [
    {
      data: dataValues.value,
      borderColor: "green",
      backgroundColor: "green",
      label: "Battery",
    },
    {
      data: dataValues2.value,
      borderColor: "blue",
      backgroundColor: "blue",
      label: "Humidity",
    },
    {
      data: dataValues3.value,
      borderColor: "red",
      backgroundColor: "red",
      label: "Pressure",
    },
    {
      data: dataValues4.value,
      borderColor: "yellow",
      backgroundColor: "yellow",
      label: "Temperature",
    },
  ],
}));

const gridColor = computed(() => {
  return useThemeStore().theme === "light" ? Chart.defaults.borderColor.toString() : "grey";
});

onMounted(() => {
  console.log("Wat");
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
  options,
});
</script>
