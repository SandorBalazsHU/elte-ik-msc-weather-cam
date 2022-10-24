import vuetify from "@/plugins/vuetify.js";
import { defineStore } from "pinia";

export type ColorThemes = "dark" | "light";
const themes: ColorThemes[] = ["dark", "light"];

export const useThemeStore = defineStore("theme", {
  state: () => ({ theme: themes[0] }),
  getters: {
    current: ({ theme }) => theme,
    nextTheme: ({ theme }) => {
      return (
        themes[
          themes.findIndex((currentTheme) => currentTheme === theme) + 1
        ] || themes[0]
      );
    },
  },
  actions: {
    change(theme: ColorThemes) {
      this.theme = theme;
    },
  },
});
