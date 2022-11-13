import { ref, onMounted, onUnmounted } from "vue";

// by convention, composable function names start with "use"
export function useWindow() {
  // state encapsulated and managed by the composable
  const width = ref(0);
  const height = ref(0);

  // a composable can update its managed state over time.
  function update() {
    width.value = window.innerWidth;
    height.value = window.innerHeight;
  }

  // a composable can also hook into its owner component's
  // lifecycle to setup and teardown side effects.
  onMounted(() => {
    width.value = window.innerWidth;
    height.value = window.innerHeight;
    window.addEventListener("resize", update);
  });
  onUnmounted(() => window.removeEventListener("resize", update));

  // expose managed state as return value
  return { width, height };
}
