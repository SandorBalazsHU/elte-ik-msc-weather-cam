<script setup lang="ts">
import { useUserStore } from "@/store/user.js";
import { storeToRefs } from "pinia";
import { ref } from "vue";
import WsUpdateUserDialog from "@/components/WsUpdateUserDialog.vue";
const { userData } = storeToRefs(useUserStore());
const passwordInput = ref<HTMLInputElement | null>(null);
const showPassword = ref(true);

function showPasswordHandler() {
  console.log(passwordInput);
  const input = passwordInput.value;
  if (input) {
    input.type = input.type === "password" ? "text" : "password";
    showPassword.value = input.type === "password";
    input.type === "password" &&
      (passwordInput.value!.size = Math.ceil(userData.value.password.length * 0.65));
    input.type === "text" && (passwordInput.value!.size = userData.value.password.length);
  }
}
</script>

<template>
  <header class="text-h4 mb-2 d-flex justify-space-between">
    <p>User details</p>
    <ws-update-user-dialog></ws-update-user-dialog>
  </header>
  <v-divider class="my-1"></v-divider>
  <main class="mt-2">
    <p class="text-h6">Username: {{ userData.username }}</p>
    <p class="text-h6">
      Password:
      <input
        disabled
        class="mr-2"
        style="width: auto"
        type="password"
        :value="userData.password"
        id="password"
        ref="passwordInput"
        :size="(userData.password?.length ?? 1) * 0.65"
        oninput="this.size = this.value.length"
      /><v-btn size="small" @click="showPasswordHandler" prepend-icon="mdi-eye">{{
        showPassword ? "Show" : "Hide"
      }}</v-btn>
    </p>
    <v-alert class="my-2" closable type="warning"
      >Changing the credentials will log you out!</v-alert
    >
  </main>
</template>
