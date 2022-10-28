<template>
  <v-app>
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
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component"></component>
          </transition>
        </router-view>
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
  </v-app>
</template>

<script setup lang="ts">
import { onMounted } from "vue";
import vuetify from "./plugins/vuetify";
import { useThemeStore } from "./store/theme";
import WsThemeSwitcher from "./components/WsThemeSwitcher.vue";
import WsResponsiveDrawer from "./components/WsResponsiveDrawer.vue";
import { useMobile } from "./components/Composables.js";
import { useDrawerStore } from "./store/drawer.js";

const themeStore = useThemeStore();
const isMobile = useMobile();
const drawerStore = useDrawerStore();

onMounted(() => {
  loadPreferedTheme();
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
