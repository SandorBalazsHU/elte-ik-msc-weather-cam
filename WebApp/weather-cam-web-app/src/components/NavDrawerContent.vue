<template>
  <div class="nav-justify d-flex flex-column h-100">
    <div class="flex-grow-0">
      <div class="my-2">
        <div v-if="themeStore.theme === 'dark'" class="w-100">
          <v-img
            style="width: 80%; margin: auto"
            lazy-src="src/assets/nav-cloud-dark.svg"
            src="src/assets/nav-cloud-dark.svg"
          ></v-img>
        </div>
        <div v-else class="w-100">
          <v-img
            style="width: 80%; margin: auto"
            lazy-src="src/assets/nav-cloud-light.svg"
            src="src/assets/nav-cloud-light.svg"
          ></v-img>
        </div>
      </div>
      <v-divider></v-divider>
      <v-list nav>
        <v-list-item
          :key="link.name"
          v-for="link in links"
          :prepend-icon="link.icon"
          :title="link.name"
          :value="link.name"
        ></v-list-item>
      </v-list>
    </div>

    <div class="flex-grow-0">
      <v-divider></v-divider>
      <div :key="contact.icon" v-for="contact in contacts">
        <v-btn :icon="contact.icon" variant="text"></v-btn
        ><span>{{ contact.name }}</span>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { useThemeStore } from "@/store/theme.js";
import { defineComponent } from "vue";

export default defineComponent({
  components: {},
  props: {
    links: Array<{ icon: string; name: string }>,
    contacts: Array<{ icon: string; name: string }>,
  },
  setup(props) {
    const themeStore = useThemeStore();
    return { props, themeStore };
  },
});
</script>

<style scoped></style>
