import vuetify from "@/plugins/vuetify.js";
import { computed } from "vue";

export const calcFormSize = computed(() => {
  switch (vuetify.display.name.value) {
    case "xs":
      return 320;
    case "sm":
      return 450;
    case "md":
      return 580;
    case "lg":
      return 650;
    case "xl":
      return 720;
  }
  return 800;
});

export const calcDrawerSize = computed(() => {
  switch (vuetify.display.name.value) {
    case "xs":
      return 256;
    case "sm":
      return 256;
    case "md":
      return 270;
    case "lg":
      return 280;
    case "xl":
      return 340;
  }
  return 370;
});

export default calcFormSize;
