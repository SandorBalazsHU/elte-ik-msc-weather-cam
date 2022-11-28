<template>
  <v-navigation-drawer v-if="!vuetify.display.mobile" app>
    <ws-nav-drawer-content
      :contacts="links.contactLinks"
      :links="links.drawerLinks"
    ></ws-nav-drawer-content>
  </v-navigation-drawer>
  <v-navigation-drawer app :width="calcDrawerSize" v-else v-model="drawerStore.drawerOpen">
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
import { calcDrawerSize } from "@/utils/Sizing";
import { onMounted } from "vue";

import WsNavDrawerContent from "./WsNavDrawerContent.vue";

const links = useLinkStore();
onMounted(() => {
  links.generateUserSpecificLinks();
});
const drawerStore = useDrawerStore();
</script>
