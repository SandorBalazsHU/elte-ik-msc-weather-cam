<template>
  <v-btn
    v-if="!vuetify.display.mobile"
    @click="themeStore.change(themeStore.nextTheme)"
    color="dark"
    variant="tonal"
    prepend-icon="mdi-theme-light-dark"
    >{{ themeStore.theme }} mode</v-btn
  >
  <v-btn
    v-else
    @click="themeStore.change(themeStore.nextTheme)"
    icon="mdi-theme-light-dark"
    color="dark"
  ></v-btn>
</template>

<script lang="ts" setup>
import vuetify from "@/plugins/vuetify.js";
import { useThemeStore } from "@/store/theme.js";

const themeStore = useThemeStore();
</script>
