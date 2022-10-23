<template>
  <v-app>
    <v-navigation-drawer app>
      <!-- -->
    </v-navigation-drawer>
    <v-navigation-drawer v-model="drawer" absolute temporary>
    </v-navigation-drawer>
    <v-app-bar :density="mobile ? 'compact' : ''" :elevation="2" app>
      <v-app-bar-nav-icon
        @click="drawer = !drawer"
        class="d-lg-none"
      ></v-app-bar-nav-icon>
      <v-app-bar-title>Weather camera</v-app-bar-title>
      <v-avatar
        :class="{
          'd-none': mobile,
        }"
      >
        <v-icon size=" 32" icon="mdi-account-circle"> </v-icon>
      </v-avatar>
    </v-app-bar>

    <!-- Sizes your content based upon application components -->
    <v-main>
      <!-- Provides the application the proper gutter -->
      <v-container fluid>
        <!-- If using vue-router -->
        <router-view></router-view>
      </v-container>
    </v-main>

    <v-footer app>
      <!-- -->
    </v-footer>
  </v-app>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import vuetify from "./plugins/vuetify";
export default defineComponent({
  name: "App",

  data() {
    return {
      drawer: false,
      mobile: vuetify.display.mdAndDown,
    };
  },
});
</script>
