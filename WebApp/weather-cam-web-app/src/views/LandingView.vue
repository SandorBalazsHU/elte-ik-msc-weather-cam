<template>
  <v-app-bar density="comfortable">
    <v-app-bar-title>{{ isMobile ? "" : "Weather Camera Web Interface" }}</v-app-bar-title>
    <v-btn
      @click="navToLoginHandler"
      prepend-icon="mdi-login-variant"
      class="mx-1"
      variant="tonal"
      color="green"
      >Login</v-btn
    >
    <v-btn
      @click="navToRegisterHandler"
      prepend-icon="mdi-account-plus"
      class="mx-1 mr-2"
      variant="tonal"
      color="blue"
      >Register</v-btn
    >
  </v-app-bar>
  <v-main class="my-4">
    <v-sheet elevation="3" rounded="rounded" class="pa-4 feed-container">
      <ws-landing-text name=""></ws-landing-text>
    </v-sheet>
  </v-main>
</template>

<script setup lang="ts">
import router from "@/router/index.js";
import { isMobile } from "@/utils/Sizing.js";
import { useLinkStore } from "@/store/links.js";
import WsLandingText from "@/components/WsLandingText.vue";
const github = useLinkStore().contactLinks.find(
  (contact) => contact.name.toLowerCase() === "github"
)?.link;

const navToLoginHandler = () => router.push({ path: "/login" });
const navToRegisterHandler = () => router.push({ path: "/register" });
</script>

<style scoped>
.feed-container {
  max-width: min(100% - 2rem, 1200px);
  height: 100%;
  margin: auto;
}

#introduction-text {
  text-align: justify;
}
</style>
