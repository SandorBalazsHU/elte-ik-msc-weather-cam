<template>
  <div class="nav-justify d-flex flex-column h-100">
    <div class="flex-grow-0">
      <div class="my-2">
        <div class="w-100">
          <v-img
            class="nav-logo-img"
            :src="themeStore.current === 'light' ? darkCloudLogo : lightCloudLogo"
          ></v-img>
        </div>
      </div>
      <v-divider></v-divider>
      <v-list nav>
        <v-list-item
          class="pointer"
          :key="link.name"
          v-for="link in links"
          :prepend-icon="link.icon"
          :title="link.name"
          :active="router.currentRoute.value.fullPath === link.link"
          @click="() => navItemClickHandler(link.link)"
        ></v-list-item>
      </v-list>
    </div>

    <div class="flex-grow-0">
      <v-divider></v-divider>
      <v-list nav>
        <v-list-item
          :key="contact.name"
          v-for="contact in contacts"
          :prepend-icon="contact.icon"
          :title="contact.name"
          :value="contact.name"
        ></v-list-item>
      </v-list>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useThemeStore } from "@/store/theme.js";
import { useRouter } from "vue-router";
import darkCloudLogo from "@/assets/nav-cloud-light.svg";
import lightCloudLogo from "@/assets/nav-cloud-dark.svg";
import type { NavigationLink } from "@/store/links.js";
export interface Props {
  links: NavigationLink[];
  contacts: NavigationLink[];
}

defineProps<Props>();
const themeStore = useThemeStore();
const router = useRouter();
const navItemClickHandler = (link: string) => router.push(link);
</script>

<style scoped>
.nav-justify {
  justify-content: space-between;
}
.nav-logo-img {
  width: 80%;
  margin: auto;
}

.pointer {
  cursor: pointer;
}
</style>
