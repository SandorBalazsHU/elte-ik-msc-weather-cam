<template>
  <v-app>
    <router-view></router-view>
  </v-app>
</template>

<script lang="ts" setup>
import { onMounted } from "vue";
import vuetify from "./plugins/vuetify.js";
import { useThemeStore } from "./store/theme.js";
import {
  Configuration,
  StationsApi,
  type Station,
} from "./generated-sources/openapi";
const themeStore = useThemeStore();

onMounted(() => {
  loadPreferedTheme();

  const configuration = new Configuration({
    basePath: "http://127.0.0.1:4010",
    accessToken: "asd",
  });
  const api = new StationsApi(configuration);
  api
    .getPartialApiKey({ stationId: "3bb20f1e-f676-ee2b-6996-f77a990dc3e4" })
    .catch((x) => console.log(x))
    .then((x) => console.log(x));
});

function loadPreferedTheme() {
  console.log("loaded");
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
