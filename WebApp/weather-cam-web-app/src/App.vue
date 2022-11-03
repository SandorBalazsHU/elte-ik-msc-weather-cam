<template>
  <v-app>
    <router-view></router-view>
  </v-app>
</template>

<script lang="ts" setup>
import { nextTick, onMounted, ref } from "vue";
import vuetify from "./plugins/vuetify.js";
import { useThemeStore } from "./store/theme.js";
import { Configuration, MeasurementsApi } from "./generated-sources/openapi";
const themeStore = useThemeStore();
const pageLoading = ref(true);
onMounted(() => {
  loadPreferedTheme();

  const configuration = new Configuration({
    basePath: "http://127.0.0.1:4010",
    accessToken: "asd",
  });
  const api = new MeasurementsApi(configuration);
  api
    .getStationMeasurementById({
      stationId: "3bb20f1e-f676-ee2b-6996-f77a990dc3e4",
      measurementId: "3bb20f1e-f676-ee2b-6996-f77a990dc3e4",
    })
    .catch((x) => console.log(x))
    .then((x) => console.log(x));
});

nextTick(() => {
  pageLoading.value = false;
});

function loadPreferedTheme() {
  themeStore.$subscribe((_, state) => {
    localStorage.setItem("theme", JSON.stringify(state));
    vuetify.theme.global.name.value = themeStore.theme;
  });
  let preferedTheme = themeStore.$state;
  let savedTheme = localStorage.getItem("theme");
  if (savedTheme) {
    preferedTheme = JSON.parse(savedTheme);
  }
  themeStore.$patch(preferedTheme);
}
</script>
