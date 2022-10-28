import type { NavDrawerContacts, NavDrawerLinks } from "@/types/types.js";
import { defineStore } from "pinia";

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

const contacts: NavDrawerContacts = [
  { icon: "mdi-github", name: "Github" },
  { icon: "mdi-api", name: "API" },
];

export const useLinkStore = defineStore("links", {
  state: () => ({
    navDrawerLinks,
    contacts,
  }),
});
