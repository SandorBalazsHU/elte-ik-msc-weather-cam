<template>
  <v-list rounded="rounded" lines="three">
    <v-list-item>
      <v-list-item-title>Home_station_1</v-list-item-title>
      <v-list-item-action class="justify-end"
        ><v-btn prepend-icon="mdi-wrench" class="mr-2" color="primary">Edit</v-btn>
        <v-btn prepend-icon="mdi-delete" color="error">Delete</v-btn>
      </v-list-item-action>
      <v-list-item-media></v-list-item-media>
    </v-list-item>
  </v-list>
</template>

<script setup lang="ts"></script>

<style scoped></style>
