<template>
  <span v-if="!loading"
    ><span>{{ prependText }}</span>
    <v-badge
      class="status-indicator"
      :color="statusToStyleMap?.get(status)?.color ?? 'yellow'"
      inline
    >
    </v-badge
    ><span>{{ statusToStyleMap?.get(status)?.text ?? "Unknown" }}</span></span
  >
  <span v-else>Status: <v-progress-circular indeterminate></v-progress-circular></span>
</template>

<script lang="ts" setup>
import type HttpStatusCode from "@/utils/HttpStatusCode.js";

defineProps<{
  prependText?: string;
  loading?: boolean;
  status: HttpStatusCode;
  statusToStyleMap?: Map<HttpStatusCode, { color: string; text: string }>;
}>();
</script>

<style scoped>
.status-indicator {
  vertical-align: text-top;
}
</style>
