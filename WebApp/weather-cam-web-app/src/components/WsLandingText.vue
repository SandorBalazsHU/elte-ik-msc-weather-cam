<template>
  <header class="text-h2">Welcome {{ name }}!</header>
  <v-divider class="ma-2"></v-divider>
  <article id="introduction-text" class="text-body-1">
    <header class="text-h4 mb-1">Introduction</header>
    <p class="mb-2">
      This web application is part of an ongoing project created by the students of Eötvös Loránd
      University (ELTE). <br />
      The application is a subject to major changes in the future so be aware of this.<br />
      The project is fully open source so all contributions are welcome!
    </p>
    <header class="text-h4 mb-1">The idea behind the project</header>
    <p class="mb-2">
      The purpose of the project is to reuse old phones as full fledged weather stations with
      cameras. <br />
      The avarage lifespan of a mobile phone is 3 to 5 years. Most of them is perfectly usable for
      not computationally intensive tasks. All of them contain GPS, compass, camera, wifi,
      bluetooth, SD card and SIM card. <br />
    </p>

    <header class="text-h4 mb-1">Dataflow</header>
    <p>
      With the use of an ESP8266 we created a specialized hardware that can meausre
      temperature,hudmidity and pressure. These measurements are transmitted to the mobile device.
      <br />
      The mobile device attaches a picture to the measurement and uploads it to our servers.<br />
      From here, this website visualizes the gathered data.
    </p>
  </article>
</template>

<script lang="ts" setup>
defineProps<{ name: string }>();
</script>
