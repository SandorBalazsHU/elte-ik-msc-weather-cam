<template>
  <v-btn
    v-if="!isMobile"
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
import { useThemeStore } from "@/store/theme.js";
import { useMobile } from "./Composables.js";

const themeStore = useThemeStore();
const isMobile = useMobile();
</script>
