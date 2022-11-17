<template>
  <v-btn
    v-if="!isMobile"
    @click="themeStore.change(themeStore.nextTheme)"
    color="dark"
    variant="tonal"
    class="ml-2"
    prepend-icon="mdi-theme-light-dark"
    >{{ themeStore.theme }}</v-btn
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
import { isMobile } from "@/utils/Sizing.js";

const themeStore = useThemeStore();
</script>
