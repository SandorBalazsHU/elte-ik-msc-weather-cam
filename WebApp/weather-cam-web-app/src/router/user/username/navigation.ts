import NotFoundView from "@/views/errors/NotFoundView.vue";

export const navigation = [
  {
    path: "home",
    component: () => import("@/views/user/HomeView.vue"),
  },
  {
    path: "about",
    component: () => import("@/views/user/AboutView.vue"),
  },
  {
    path: "monitoring",

    component: () => import("@/views/user/MonitoringView.vue"),
  },
  {
    path: "stations",

    component: () => import("@/views/user/StationsView.vue"),
  },
  {
    path: "account",

    component: () => import("@/views/user/AccountView.vue"),
  },
  {
    path: "settings",
    component: () => import("@/views/user/SettingsView.vue"),
  },
  {
    path: ":pathMatch(.*)*",

    component: NotFoundView,
  },
];

export default navigation;
