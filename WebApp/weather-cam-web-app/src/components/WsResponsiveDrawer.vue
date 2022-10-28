<template>
  <v-navigation-drawer v-if="!mobile" app>
    <ws-nav-drawer-content
      :contacts="links.contacts"
      :links="links.navDrawerLinks"
    ></ws-nav-drawer-content>
  </v-navigation-drawer>
  <v-navigation-drawer v-else v-model="links.drawer">
    <ws-nav-drawer-content
      :contacts="links.contacts"
      :links="links.navDrawerLinks"
    ></ws-nav-drawer-content>
  </v-navigation-drawer>
</template>

<script lang="ts" setup>
import { useLinkStore } from "@/store/links.js";
import WsNavDrawerContent from "./WsNavDrawerContent.vue";

const links = useLinkStore();
const props = defineProps<{ mobile: boolean }>();
</script>
