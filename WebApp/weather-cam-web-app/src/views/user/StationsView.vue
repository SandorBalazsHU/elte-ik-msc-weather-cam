<template>
  <v-expansion-panels>
    <header class="text-h4 w-100 mb-2">
      <span>Your stations</span>
      <v-icon class="ml-1">mdi-access-point-network</v-icon>
      <v-divider class="mt-2"></v-divider>
    </header>

    <v-expansion-panel :elevation="3"
      ><v-expansion-panel-title class="text-h5">
        <v-icon class="active-icon">mdi-chevron-down</v-icon>
        <span class="name-text text-truncate">Home_station_01</span>
        <template v-slot:actions>
          <template v-if="!isMobile">
            <v-btn class="mx-2" size="large" color="secondary" prepend-icon="mdi-wrench"
              >Edit</v-btn
            >
            <v-btn color="error" size="large" prepend-icon="mdi-delete">Delete</v-btn>
          </template>
          <template v-else>
            <template v-if="!xs">
              <v-btn class="mx-2" color="secondary" icon="mdi-wrench"></v-btn>
              <v-btn color="error" icon="mdi-delete"></v-btn>
            </template>
          </template>
        </template>
      </v-expansion-panel-title>
      <v-expansion-panel-text class="text-h6 text-wrap">
        <ws-status-indicator
          prepend-text="Status:"
          :status-to-style-map="states"
          :status="200"
        ></ws-status-indicator>
        <div>ID: 63af90ba-6f34-11ed-a1eb-0242ac120002</div>
        <div>Timezone: GMT+1</div>
        <div>API Key: 63af90ba-6f34-11ed-a1eb-0242ac120002</div>
        <div v-if="xs" class="text-right mt-2">
          <v-btn class="mx-2" color="secondary" icon="mdi-wrench"></v-btn>
          <v-btn color="error" icon="mdi-delete"></v-btn>
        </div>
      </v-expansion-panel-text>
    </v-expansion-panel>
    <v-expansion-panel :elevation="3"
      ><v-expansion-panel-title class="text-h5">
        <v-icon class="active-icon">mdi-chevron-down</v-icon>
        <span class="name-text text-truncate">Home_station_01</span>
        <template v-slot:actions>
          <template v-if="!isMobile">
            <v-btn class="mx-2" size="large" color="secondary" prepend-icon="mdi-wrench"
              >Edit</v-btn
            >
            <v-btn color="error" size="large" prepend-icon="mdi-delete">Delete</v-btn>
          </template>
          <template v-else>
            <template v-if="!xs">
              <v-btn class="mx-2" color="secondary" icon="mdi-wrench"></v-btn>
              <v-btn color="error" icon="mdi-delete"></v-btn>
            </template>
          </template>
        </template>
      </v-expansion-panel-title>
      <v-expansion-panel-text class="text-h6 text-wrap">
        <ws-status-indicator
          prepend-text="Status:"
          :status-to-style-map="states"
          :status="200"
        ></ws-status-indicator>
        <div>ID: 63af90ba-6f34-11ed-a1eb-0242ac120002</div>
        <div>Timezone: GMT+1</div>
        <div>API Key: 63af90ba-6f34-11ed-a1eb-0242ac120002</div>
        <div v-if="xs" class="text-right mt-2">
          <v-btn class="mx-2" color="secondary" icon="mdi-wrench"></v-btn>
          <v-btn color="error" icon="mdi-delete"></v-btn>
        </div>
      </v-expansion-panel-text>
    </v-expansion-panel>
    <v-expansion-panel :elevation="3"
      ><v-expansion-panel-title class="text-h5">
        <v-icon class="active-icon">mdi-chevron-down</v-icon>
        <span class="name-text text-truncate">Home_station_01</span>
        <template v-slot:actions>
          <template v-if="!isMobile">
            <v-btn class="mx-2" size="large" color="secondary" prepend-icon="mdi-wrench"
              >Edit</v-btn
            >
            <v-btn color="error" size="large" prepend-icon="mdi-delete">Delete</v-btn>
          </template>
          <template v-else>
            <template v-if="!xs">
              <v-btn class="mx-2" color="secondary" icon="mdi-wrench"></v-btn>
              <v-btn color="error" icon="mdi-delete"></v-btn>
            </template>
          </template>
        </template>
      </v-expansion-panel-title>
      <v-expansion-panel-text class="text-h6 text-wrap">
        <ws-status-indicator
          prepend-text="Status:"
          :status-to-style-map="states"
          :status="200"
        ></ws-status-indicator>
        <div>ID: 63af90ba-6f34-11ed-a1eb-0242ac120002</div>
        <div>Timezone: GMT+1</div>
        <div>API Key: 63af90ba-6f34-11ed-a1eb-0242ac120002</div>
        <div v-if="xs" class="text-right mt-2">
          <v-btn class="mx-2" color="secondary" icon="mdi-wrench"></v-btn>
          <v-btn color="error" icon="mdi-delete"></v-btn>
        </div>
      </v-expansion-panel-text>
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script setup lang="ts">
import states from "@/utils/StationStates.js";
import WsStatusIndicator from "@/components/WsStatusIndicator.vue";
import { isMobile, xs } from "@/utils/Sizing.js";
</script>
<style scoped>
.rounded {
  border-radius: 0.25rem !important;
}

.v-expansion-panel-title[aria-expanded="true"] > .active-icon::before {
  content: "\F0143";
}

.v-expansion-panel-title[aria-expanded="false"] > .inactive-icon::before {
  content: "\F0142";
}
</style>
