<template>
  <v-navigation-drawer v-if="!vuetify.display.mobile" app>
    <ws-nav-drawer-content
      :contacts="links.contactLinks"
      :links="links.drawerLinks"
    ></ws-nav-drawer-content>
  </v-navigation-drawer>
  <v-navigation-drawer v-else v-model="drawerStore.drawerOpen">
    <ws-nav-drawer-content
      :contacts="links.contactLinks"
      :links="links.drawerLinks"
    ></ws-nav-drawer-content>
  </v-navigation-drawer>
</template>

<script lang="ts" setup>
import vuetify from "@/plugins/vuetify.js";
import { useDrawerStore } from "@/store/drawer.js";
import { useLinkStore } from "@/store/links.js";

import WsNavDrawerContent from "./WsNavDrawerContent.vue";

const links = useLinkStore();
const drawerStore = useDrawerStore();
</script>
