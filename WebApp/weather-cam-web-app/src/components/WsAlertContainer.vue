<template>
  <div class="error-container">
    <v-alert
      class="mb-2"
      :title="payload.type"
      type="error"
      :key="errorId"
      :closable="true"
      v-for="{ payload, errorId } in applyFilters()"
      >{{ payload.message }}</v-alert
    >
  </div>
</template>

<script lang="ts" setup>
import type { ErrorRecord, ErrorType } from "@/store/errors.js";
import { useErrorStore } from "@/store/errors.js";
export interface Props {
  filters: ErrorType[];
  max: number;
}

function applyFilters(): ErrorRecord[] {
  let tmp = errorStore.errors;
  props.filters.forEach((filter) => {
    tmp = tmp.filter((error) => error.type == filter);
  });
  return tmp.slice(-(props.max < 0 ? 0 : props.max));
}

const props = defineProps<Props>();
const errorStore = useErrorStore();
</script>
