<script setup lang="ts">
import WsLandingText from "@/components/WsLandingText.vue";
import { useUserStore } from "@/store/user.js";
</script>

<template>
  <main>
    <ws-landing-text :name="useUserStore().username"></ws-landing-text>
  </main>
</template>
