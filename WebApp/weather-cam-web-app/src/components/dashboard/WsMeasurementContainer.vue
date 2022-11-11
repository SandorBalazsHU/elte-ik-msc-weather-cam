<template>
  <p class="text-h3">Current</p>
  <div class="w-100 gap flex-wrap d-flex justify-center">
    <div class="test gap d-flex justify-space-around flex-grow-1">
      <v-sheet class="square flex-grow-1"></v-sheet>
      <v-sheet class="square flex-grow-1"></v-sheet>
    </div>
    <div class="test gap d-flex justify-space-around flex-grow-1">
      <v-sheet class="square flex-grow-1"></v-sheet>
      <v-sheet class="square flex-grow-1"></v-sheet>
    </div>
    <v-divider></v-divider>
  </div>
</template>

<script lang="ts" setup></script>

<style scoped>
.square {
  aspect-ratio: 1;
}
.gap {
  gap: max(1vw, 1rem);
}
.test {
  min-width: 200px;
  max-height: 20vh;
}
</style>
