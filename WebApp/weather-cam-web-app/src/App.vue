<template>
  <v-app>
    <v-navigation-drawer app>
      <nav-drawer-content
        :contacts="contacts"
        :links="navDrawerLinks"
      ></nav-drawer-content>
    </v-navigation-drawer>
    <v-navigation-drawer v-model="drawer" expand-on-hover rail temporary>
      <nav-drawer-content
        :contacts="contacts"
        :links="navDrawerLinks"
      ></nav-drawer-content>
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

      <v-btn
        @click="changeTheme(themeStore.nextTheme)"
        color="dark"
        variant="tonal"
        prepend-icon="mdi-theme-light-dark"
        >{{ themeStore.theme }} mode</v-btn
      >
    </v-app-bar>

    <v-main>
      <v-container fluid>
        <router-view></router-view>
      </v-container>
    </v-main>

    <v-footer app>
      <div>Weather Camera App ({{ new Date().getFullYear() }})</div>
    </v-footer>
  </v-app>
</template>

<script lang="ts">
import { ref, reactive, defineComponent, onMounted } from "vue";
import vuetify from "./plugins/vuetify";
import { useThemeStore, type ColorThemes } from "./store/theme";
import NavDrawerContent from "./components/NavDrawerContent.vue";

export default defineComponent({
  components: {
    NavDrawerContent,
  },
  setup() {
    const navDrawerLinks = [
      { name: "Home", icon: "mdi-home" },
      { name: "Weather stations", icon: "mdi-access-point-network" },
      { name: "My account", icon: "mdi-account" },
      { name: "Settings", icon: "mdi-cog-outline" },
      { name: "About us", icon: "mdi-book-open-blank-variant" },
    ];
    const themeStore = useThemeStore();
    const drawer = ref(false);
    const mobile = reactive(vuetify.display.mobile);
    const contacts: Contacts = [{ icon: "mdi-github", name: "Github" }];

    function changeTheme(theme: ColorThemes) {
      themeStore.change(theme);
      vuetify.theme.global.name.value = themeStore.theme;
    }

    onMounted(() => {
      themeStore.$subscribe((_, state) => {
        localStorage.setItem("theme", JSON.stringify(state));
      });
      let preferedTheme = themeStore.$state;
      let savedTheme = localStorage.getItem("theme");
      if (savedTheme) {
        preferedTheme = JSON.parse(savedTheme);
      }
      themeStore.$patch(preferedTheme);
      changeTheme(themeStore.$state.theme);
    });
    return {
      contacts,
      mobile,
      drawer,
      themeStore,
      changeTheme,
      navDrawerLinks,
    };
  },
});
export type Contacts = Array<{ icon: string; name: string }>;
</script>

<style scoped>
.nav-justify {
  justify-content: space-between;
}

footer {
  justify-content: right;
}
</style>
