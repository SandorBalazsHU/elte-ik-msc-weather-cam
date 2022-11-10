<template>
  <v-app class="gp-full-height">
    <router-view></router-view>
  </v-app>
</template>

<script lang="ts" setup>
import { nextTick, onMounted, ref } from "vue";
import vuetify from "./plugins/vuetify.js";
import { useThemeStore } from "./store/theme.js";
import { debounce } from "@/utils";
const themeStore = useThemeStore();
const pageLoading = ref(true);
onMounted(() => {
  setMobileViewportHeight();
  loadPreferedTheme();
});

nextTick(() => {
  pageLoading.value = false;
});

function setViewHeight() {
  const vh = window.innerHeight * 0.01;
  document.documentElement.style.setProperty("--vh", `${vh}px`);
}
function setMobileViewportHeight() {
  setViewHeight();
  window.addEventListener(
    "resize",
    debounce(() => setViewHeight, 50)
  );
}

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

<style>
.gp-full-height .v-application__wrap {
  min-height: calc(100 * var(--vh)) !important;
}
</style>
