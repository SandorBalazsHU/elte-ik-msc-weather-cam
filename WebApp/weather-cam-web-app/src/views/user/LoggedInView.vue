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

    <div class="avatar-container">
      <v-avatar
        :class="{
          'd-none': false,
        }"
      >
        <v-icon size=" 32" icon="mdi-account-circle"> </v-icon> </v-avatar
      ><span style="vertical-align: middle; font-size: 1.25rem">{{
        !isMobile ? userStore.username : ""
      }}</span>

      <v-menu activator="parent">
        <v-list>
          <v-list-item style="cursor: pointer"
            ><span style="cursor: pointer"
              >Sign out <v-icon>mdi-logout-variant</v-icon></span
            ></v-list-item
          >
        </v-list>
      </v-menu>
    </div>

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
import { onMounted } from "vue";
import vuetify from "@/plugins/vuetify.js";
import { useThemeStore } from "@/store/theme.js";
import { useUserStore } from "@/store/user.js";
import WsThemeSwitcher from "@/components/WsThemeSwitcher.vue";
import WsResponsiveDrawer from "@/components/WsResponsiveDrawer.vue";
import { useDrawerStore } from "@/store/drawer.js";
import { computed } from "vue";

const themeStore = useThemeStore();
const isMobile = computed(() => {
  return vuetify.display.mobile.value;
});
const drawerStore = useDrawerStore();
const userStore = useUserStore();
onMounted(() => {
  isMobile.value ? drawerStore.changeDrawerState(false) : drawerStore.changeDrawerState(true);
});
</script>

<style scoped>
footer {
  justify-content: right;
}

.avatar-container {
  cursor: pointer;
}
</style>
