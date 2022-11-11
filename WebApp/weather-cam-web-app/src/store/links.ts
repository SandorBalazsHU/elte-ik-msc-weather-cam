import { defineStore } from "pinia";
import { useUserStore } from "./user.js";

const defaultDrawerLinks = (username?: string) =>
  (username
    ? [
        { name: "Home", icon: "mdi-home", link: "/user/noratan/home" },
        {
          name: "Weather stations",
          icon: "mdi-access-point-network",
          link: `/user/${username}/stations`,
        },
        {
          name: "My account",
          icon: "mdi-account",
          link: `/user/${username}/account`,
        },
        {
          name: "Settings",
          icon: "mdi-cog-outline",
          link: `/user/${username}/settings`,
        },
        {
          name: "About us",
          icon: "mdi-book-open-blank-variant",
          link: `/user/${username}/about`,
        },
      ]
    : []) as NavigationLink[];

const defaultContactLinks = (username?: string) =>
  (username
    ? [
        {
          link: "https://github.com/SandorBalazsHU/elte-ik-msc-weather-cam",
          icon: "mdi-github",
          name: "Github",
        },
        {
          link: "https://github.com/SandorBalazsHU/elte-ik-msc-weather-cam/tree/feature/webApp/Documentation/WebApi",
          icon: "mdi-api",
          name: "API",
        },
      ]
    : []) as NavigationLink[];

export interface NavigationLink {
  name: string;
  icon: string;
  link: string;
}

export interface LinkStoreState {
  drawerLinks: NavigationLink[];
  contactLinks: NavigationLink[];
}

export const useLinkStore = defineStore("links", {
  state: () =>
    ({
      drawerLinks: defaultDrawerLinks(useUserStore().username),
      contactLinks: defaultContactLinks(useUserStore().username),
    } as LinkStoreState),
});
