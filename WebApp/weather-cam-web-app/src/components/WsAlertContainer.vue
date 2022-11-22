<template>
  <div class="error-container">
    <v-alert
      class="mb-2"
      :key="alertId"
      :closable="true"
      :type="alertType"
      @click="() => alertStore.removeAlertById(id, alertId)"
      v-for="{ payload, alertId, alertType } in alerts?.slice(calcDisplayed)"
      >{{ payload.message }}</v-alert
    >
  </div>
</template>

<script lang="ts" setup>
import { useAlertStore } from "@/store/alert.js";
import { computed, onMounted, onUnmounted } from "vue";
import { VAlert } from "vuetify/components";
export interface Props {
  maxDisplayed?: number;
  maxStored?: number;
  id: string;
}
const calcDisplayed = computed(() => {
  const max = props.maxDisplayed;
  return max ? (max < 0 ? 0 : -max) : 0;
});
const props = defineProps<Props>();

const alertStore = useAlertStore();
const alerts = computed(() => alertStore.alertContainers.get(props.id));
onMounted(() => {
  alertStore.registerAlertContainer(props.id);
});
onUnmounted(() => {
  alertStore.unRegisterAlertContainer(props.id);
});

function deleteErrors(name: string) {
  if (name !== "addError") return false;
  const max = props.maxDisplayed || 0;
  const alertLength = alerts.value?.length || 0;
  max < alertLength && alertStore.forgetLastAlerts(props.id, alertLength - max);
}

alertStore.$onAction(({ name, after }) => {
  after(() => {
    deleteErrors(name);
  });
});
</script>
