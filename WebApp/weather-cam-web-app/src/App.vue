<script setup lang="ts">
import { ref, reactive, computed } from "vue";
import vuetify from "./plugins/vuetify";

const drawer = ref(false);
const mobile = reactive(vuetify.display.mobile);
const theme = reactive(vuetify.theme.global.name);
const navDrawerLinks = [
  "Home",
  "Weather stations",
  "My account",
  "Settings",
  "About us",
];
const contacts: Array<{ icon: string; name: string }> = [
  { icon: "mdi-github", name: "Github" },
];

function setTheme(newTheme: string) {
  return (theme.value = newTheme);
}
const switchTheme = computed(() => {
  return theme.value === "dark" ? "light" : "dark";
});
</script>

<template>
  <v-app>
    <v-navigation-drawer app>
      <navigation-drawer-content></navigation-drawer-content>
      <div class="nav-justify d-flex flex-column h-100">
        <div class="flex-grow-0">
          <div class="my-2">
            <div v-if="theme === 'dark'" class="w-100">
              <v-img
                style="width: 80%; margin: auto"
                lazy-src="src/assets/nav-cloud-dark.svg"
                src="src/assets/nav-cloud-dark.svg"
              ></v-img>
            </div>
            <div v-else class="w-100">
              <v-img
                style="width: 80%; margin: auto"
                lazy-src="src/assets/nav-cloud-light.svg"
                src="src/assets/nav-cloud-light.svg"
              ></v-img>
            </div>
          </div>
          <v-divider></v-divider>
        </div>

        <div class="flex-grow-0">
          <v-divider></v-divider>
          <div :key="contact.icon" v-for="contact in contacts">
            <v-btn :icon="contact.icon" variant="text"></v-btn
            ><span>{{ contact.name }}</span>
          </div>
        </div>
      </div>
    </v-navigation-drawer>
    <v-navigation-drawer v-model="drawer" absolute temporary>
    </v-navigation-drawer>
    <v-app-bar :density="mobile ? 'compact' : ''" :elevation="2" app>
      <v-app-bar-nav-icon
        @click="drawer = !drawer"
        class="d-lg-none"
      ></v-app-bar-nav-icon>
      <v-app-bar-title>Weather camera</v-app-bar-title>

      <v-avatar
        :class="{
          'd-none': mobile,
        }"
      >
        <v-icon size=" 32" icon="mdi-account-circle"> </v-icon>
      </v-avatar>

      <v-btn
        @click="setTheme(switchTheme)"
        color="dark"
        variant="tonal"
        prepend-icon="mdi-theme-light-dark"
        >{{ switchTheme }} mode</v-btn
      >
    </v-app-bar>

    <v-main>
      <v-container fluid>
        <router-view></router-view>
      </v-container>
    </v-main>

    <v-footer app>
      <div>Weather Camera App ({{ new Date().getFullYear() }})</div>
    </v-footer>
  </v-app>
</template>

<style>
.nav-justify {
  justify-content: space-between;
}

footer {
  justify-content: right;
}
</style>
