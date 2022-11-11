<template>
  <ws-responsive-drawer></ws-responsive-drawer>
  <v-app-bar :density="isMobile ? 'compact' : 'default'" :elevation="2" app>
    <v-app-bar-nav-icon
      @click="
        !isMobile
          ? drawerStore.changeDrawerState(true)
          : drawerStore.changeDrawerState(!drawerStore.drawerOpen)
      "
      class="d-lg-none"
    ></v-app-bar-nav-icon>
    <v-app-bar-title>Weather camera </v-app-bar-title>

    <v-avatar
      :class="{
        'd-none': isMobile,
      }"
    >
      <v-icon size=" 32" icon="mdi-account-circle"> </v-icon>
    </v-avatar>

    <ws-theme-switcher></ws-theme-switcher>
  </v-app-bar>

  <v-main>
    <v-container fluid>
      <router-view> </router-view>
    </v-container>
  </v-main>

  <v-footer app>
    <div
      :class="{
        'text-grey': themeStore.theme === 'dark',
        'text-grey-darken-1': themeStore.theme !== 'dark',
      }"
    >
      Weather Camera App ({{ new Date().getFullYear() }})
    </div>
  </v-footer>
</template>

<script setup lang="ts">
import { onMounted, onBeforeMount } from "vue";
import vuetify from "@/plugins/vuetify.js";
import { useThemeStore } from "@/store/theme.js";
import WsThemeSwitcher from "@/components/WsThemeSwitcher.vue";
import WsResponsiveDrawer from "@/components/WsResponsiveDrawer.vue";
import { useDrawerStore } from "@/store/drawer.js";
import { computed } from "vue";
import router from "@/router/index.js";

const themeStore = useThemeStore();
const isMobile = computed(() => {
  return vuetify.display.mobile.value;
});
const drawerStore = useDrawerStore();

onMounted(() => {
  isMobile.value ? drawerStore.changeDrawerState(false) : drawerStore.changeDrawerState(true);
});
</script>

<style scoped>
footer {
  justify-content: right;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.35s ease;
}

.fade-enter-from,
.fade-leave-active {
  opacity: 0;
}
</style>
