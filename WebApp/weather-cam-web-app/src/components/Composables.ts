import vuetify from "@/plugins/vuetify.js";
import { ref } from "vue";

export function useMobile() {
  // state encapsulated and managed by the composable
  const isMobile = ref(vuetify.display.mobile);
  return { isMobile };
}
