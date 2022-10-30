import LoggedInViewVue from "@/views/LoggedInView.vue";
import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/user/:username/",
      component: LoggedInViewVue,
      children: [
        {
          path: "home",
          component: HomeView,
        },
        {
          path: "about",

          component: () => import("@/views/AboutView.vue"),
        },
        {
          path: "stations",

          component: () => import("@/views/StationsView.vue"),
        },
        {
          path: "account",

          component: () => import("@/views/AccountView.vue"),
        },
        {
          path: "settings",
          component: () => import("@/views/SettingsView.vue"),
        },
        {
          path: ":pathMatch(.*)*",

          component: () => import("@/views/NotFoundView.vue"),
        },
      ],
    },
  ],
});

export default router;
