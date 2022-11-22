<template>
  <v-app-bar density="comfortable">
    <v-app-bar-title>{{ isMobile ? "" : "Weather Camera Web Interface" }}</v-app-bar-title>
    <v-btn
      @click="() => router.push({ path: '/login' })"
      prepend-icon="mdi-login-variant"
      class="mx-1"
      variant="tonal"
      color="green"
      >Login</v-btn
    >
    <v-btn
      @click="() => router.push({ path: '/register' })"
      prepend-icon="mdi-account-plus"
      class="mx-1 mr-2"
      variant="tonal"
      color="blue"
      >Register</v-btn
    >
  </v-app-bar>
  <v-main class="my-4">
    <v-sheet elevation="3" rounded="rounded" class="pa-4 feed-container">
      <header class="text-h2">Welcome!</header>
      <v-divider class="ma-2"></v-divider>
      <article id="introduction-text" class="text-body-1">
        <header class="text-h4 mb-1">Introduction</header>
        <p class="mb-2">
          This web application is part of an ongoing project created by the students of Eötvös
          Loránd University (ELTE). <br />
          The application is a subject to major changes in the future so be aware of this.<br />
          The project is fully open source so all contributions are welcome!
        </p>
        <header class="text-h4 mb-1">The idea behind the project</header>
        <p class="mb-2">
          The purpose of the project is to reuse old phones as full fledged weather stations with
          cameras. <br />
          The avarage lifespan of a mobile phone is 3 to 5 years. Most of them is perfectly usable
          for not computationally intensive tasks. All of them contain GSP, compass, camera, wifi,
          bluetooth, SD card and SIM card.
        </p>
      </article>
    </v-sheet>
  </v-main>
</template>

<script setup lang="ts">
import router from "@/router/index.js";
import { isMobile } from "@/utils/Sizing.js";
import { useLinkStore } from "@/store/links.js";
const github = useLinkStore().contactLinks.find(
  (contact) => contact.name.toLowerCase() === "github"
)?.link;
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
