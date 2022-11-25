import { defineStore } from "pinia";
import { useUserStore } from "./user.js";

const defaultDrawerLinks = (username?: string) =>
  (username
    ? [
        { name: "Home", icon: "mdi-home", link: "/user/noratan/home" },
        {
          name: "Monitoring",
          icon: "mdi-chart-line ",
          link: `/user/${username}/monitoring`,
        },
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

const defaultContactLinks: NavigationLink[] = [
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
];

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
      drawerLinks: [] as NavigationLink[],
      contactLinks: defaultContactLinks,
    } as LinkStoreState),

  actions: {
    addDrawerLink(link: NavigationLink) {
      console.log("runnnnn");
      this.drawerLinks.push(link);
    },
    generateUserSpecificLinks() {
      this.drawerLinks = defaultDrawerLinks(useUserStore().username);
    },
  },
});
