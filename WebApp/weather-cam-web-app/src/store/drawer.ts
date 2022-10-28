import { defineStore } from "pinia";

export const useDrawerStore = defineStore("drawer", {
  state: () => ({
    drawerOpen: false,
  }),
  actions: {
    changeDrawerState(newState: boolean) {
      this.drawerOpen = newState;
    },
  },
});
