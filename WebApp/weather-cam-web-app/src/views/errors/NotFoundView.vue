<script setup lang="ts">
import notFoundCatPic from "@/misc/CatLoader.js";
</script>

<template>
  <div class="mt-2 d-flex flex-column">
    <p class="text-center justify-center text-h1">404</p>
    <p class="text-center justify-center text-h2">Not found!</p>
  </div>
  <main class="fullscreen pa-2 justify-center">
    <div>
      <v-img
        id="cat"
        class="justify-center"
        alt="Your cat is being loaded!"
        :src="notFoundCatPic.src"
        rel="preload"
      ></v-img>
      <p class="text-center justify-center text-h4">
        Here is a cat to cheer you up!
      </p>
    </div>
  </main>
</template>

<style>
.fullscreen {
  display: flex;
  width: 100%;
  height: 100%;
}

.center {
  justify-content: center;
}

#cat {
  margin: auto;
  max-width: 500px;
  max-height: 500px;
}
</style>
