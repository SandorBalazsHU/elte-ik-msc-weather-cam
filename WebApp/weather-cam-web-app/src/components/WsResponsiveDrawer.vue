<template>
  <v-navigation-drawer v-if="!isMobile" app>
    <ws-nav-drawer-content
      :contacts="links.contacts"
      :links="links.navDrawerLinks"
    ></ws-nav-drawer-content>
  </v-navigation-drawer>
  <v-navigation-drawer v-else @change="t" v-model="drawerStore.drawerOpen">
    <ws-nav-drawer-content
      :contacts="links.contacts"
      :links="links.navDrawerLinks"
    ></ws-nav-drawer-content>
  </v-navigation-drawer>
</template>

<script lang="ts" setup>
import { useDrawerStore } from "@/store/drawer.js";
import { useLinkStore } from "@/store/links.js";
import { ref } from "vue";
import { useMobile } from "./Composables.js";
import WsNavDrawerContent from "./WsNavDrawerContent.vue";

function t() {
  console.log("asd");
}

const links = useLinkStore();
const isMobile = useMobile();
const drawerStore = useDrawerStore();
</script>
