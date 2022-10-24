<template>
  <v-app>
    <v-navigation-drawer v-if="!mobile" app>
      <nav-drawer-content
        :contacts="contacts"
        :links="navDrawerLinks"
      ></nav-drawer-content>
    </v-navigation-drawer>
    <v-navigation-drawer v-else v-model="drawer">
      <nav-drawer-content
        :contacts="contacts"
        :links="navDrawerLinks"
      ></nav-drawer-content>
    </v-navigation-drawer>
    <v-app-bar :density="mobile ? 'compact' : 'default'" :elevation="2" app>
      <v-app-bar-nav-icon
        @click="!mobile ? (drawer = true) : (drawer = !drawer)"
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
        v-if="!mobile"
        @click="changeTheme(themeStore.nextTheme)"
        color="dark"
        variant="tonal"
        prepend-icon="mdi-theme-light-dark"
        >{{ themeStore.theme }} mode</v-btn
      >
      <v-btn
        v-else
        @click="changeTheme(themeStore.nextTheme)"
        icon="mdi-theme-light-dark"
        color="dark"
      ></v-btn>
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
    const navDrawerLinks: NavDrawerLinks = [
      { name: "Home", icon: "mdi-home", routerLink: "/" },
      {
        name: "Weather stations",
        icon: "mdi-access-point-network",
        routerLink: "/stations",
      },
      { name: "My account", icon: "mdi-account", routerLink: "/account" },
      { name: "Settings", icon: "mdi-cog-outline", routerLink: "/settings" },
      {
        name: "About us",
        icon: "mdi-book-open-blank-variant",
        routerLink: "/about",
      },
    ];
    const themeStore = useThemeStore();
    const drawer = ref(false);
    const mobile = reactive(vuetify.display.mobile);
    const contacts: NavDrawerContacts = [
      { icon: "mdi-github", name: "Github" },
    ];

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
type NavDrawerContacts = Array<{ icon: string; name: string }>;
type NavDrawerLinks = Array<{
  icon: string;
  name: string;
  routerLink: string;
}>;
export type { NavDrawerContacts, NavDrawerLinks };
</script>

<style scoped>
footer {
  justify-content: right;
}
</style>
