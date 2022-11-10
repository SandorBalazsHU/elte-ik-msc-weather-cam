import LoggedInView from "@/views/user/LoggedInView.vue";
import NotFoundView from "@/views/errors/NotFoundView.vue";
import { createRouter, createWebHistory } from "vue-router";
import navigation from "@/router/user/username/navigation.js";
const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/user/:username/",
      component: LoggedInView,
      children: [...navigation],
    },
    {
      path: "/login",
      component: () => import("@/views/authentication/LogInView.vue"),
    },
    {
      path: "/register",
      component: () => import("@/views/authentication/LogInView.vue"),
    },
    {
      path: "/:pathMatch(.*)*",

      component: NotFoundView,
    },
  ],
});

export default router;
