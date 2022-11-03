import LoggedInView from "@/views/LoggedInView.vue";
import NotFoundView from "@/views/NotFoundView.vue";
import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/user/:username/",
      component: LoggedInView,
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

          component: NotFoundView,
        },
      ],
    },
    {
      path: "/login",
      component: () => import("@/views/LogInView.vue"),
    },
    {
      path: "/register",
      component: () => import("@/views/LogInView.vue"),
    },
    {
      path: "/:pathMatch(.*)*",

      component: NotFoundView,
    },
  ],
});

export default router;
