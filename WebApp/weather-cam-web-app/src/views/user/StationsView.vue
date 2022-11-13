import WsMeasuremenContainer from ""
<template>
  <main>
    <div class="w-100 d-flex justify-center">
      <ws-measurements
        :measurement="{ battery: 32, humidity: 66, pressure: 1012, temperature: 24.1 }"
        class="mb-4"
      >
      </ws-measurements>
    </div>
    <v-divider></v-divider>
  </main>
</template>

<script setup lang="ts">
import WsMeasurements from "@/components/dashboard/WsMeasurements.vue";
</script>
