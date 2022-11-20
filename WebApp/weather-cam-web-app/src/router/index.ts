import LoggedInView from "@/views/user/LoggedInView.vue";
import NotFoundView from "@/views/errors/NotFoundView.vue";
import { createRouter, createWebHistory } from "vue-router";
import navigation from "@/router/user/username/navigation.js";
import { useUserStore } from "@/store/user.js";
const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/",
      component: () => import("@/views/LandingView.vue"),
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
    {
      path: "/user/:username/",
      component: LoggedInView,
      children: [...navigation],
    },
  ],
});

router.beforeEach((to, from) => {
  if (!useUserStore().bearerToken && "username" in to.params) {
    router.replace("/login");
  }
  return true;
});

export default router;
