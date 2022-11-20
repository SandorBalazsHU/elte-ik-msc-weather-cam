<template>
  <div class="gap d-flex w-100 justify-center">
    <v-btn class="centered" density="compact" icon>
      <v-icon>mdi-play</v-icon>
    </v-btn>

    <v-progress-linear
      class="centered"
      color="primary"
      :model-value="(current_picture / all_pictures || 0) * 100"
    ></v-progress-linear>
    <div style="white-space: nowrap" class="centered">
      {{ `${current_picture} / ${all_pictures}` }}
    </div>
    <v-btn class="centered" density="compact" icon>
      <v-icon>mdi-fullscreen</v-icon>
    </v-btn>
  </div>
</template>

<script lang="ts" setup>
defineProps<{ current_picture: number; all_pictures: number }>();
</script>

<style scoped>
.gap {
  column-gap: max(1vh, 1vw);
}

.centered {
  margin: auto;
}
</style>
