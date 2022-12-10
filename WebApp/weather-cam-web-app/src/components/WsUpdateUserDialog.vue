<template>
  <div>
    <v-dialog :width="calcFormSize" v-model="dialog">
      <template v-slot:activator="{ props }">
        <v-btn color="green" size="large" v-bind="props" prepend-icon="mdi-update">Update</v-btn>
      </template>

      <v-card>
        <v-card-title> Update user </v-card-title>
        <v-card-text>
          <v-text-field
            :rules="isRequired('Field username must not be empty!')"
            label="Username"
            validate-on="input"
            ref="usernameInput"
            v-model="newUsername"
          ></v-text-field>
          <v-text-field
            :type="passwordInput?.focused ? 'text' : 'password'"
            :rules="isRequired('Field password name must not be empty!')"
            label="New password"
            validate-on="input"
            ref="passwordInput"
            v-model="newPassword"
          ></v-text-field>
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn :disabled="updateDisabled" color="green" @click="updateUserHandler">Update</v-btn>
          <v-btn color="info" @click="cancelUpdateUserHandler">Cancel</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script lang="ts" setup>
import { computed, ref } from "vue";
import { calcFormSize } from "@/utils/Sizing.js";
import { useUserStore } from "@/store/user.js";
import { isRequired } from "@/utils/FormValidators.js";
import { VTextField } from "vuetify/components";
import { storeToRefs } from "pinia";
import router from "@/router/index.js";
import { useAlertStore } from "@/store/alert.js";

const onUpdateSuccess = () => {
  useUserStore().$reset();
  router.push({ path: "/login", force: true }).then(() => {
    useAlertStore().addAlert(
      "login-errors",
      { message: "Your credentials changed! Please log in again!", type: "", code: 200 },
      "info"
    );
  });
};

async function updateUserHandler() {
  await useUserStore()
    .updateUserData({ password: newPassword.value, username: newUsername.value })
    .then(() => {
      onUpdateSuccess();
    });
}

function cancelUpdateUserHandler() {
  dialog.value = false;
}
const dialog = ref(false);
const { userData } = storeToRefs(useUserStore());
const newUsername = ref(userData.value.username);
const newPassword = ref(userData.value.password);

const usernameInput = ref<VTextField | null>(null);
const passwordInput = ref<VTextField | null>(null);
const updateDisabled = computed(
  () =>
    passwordInput.value?.modelValue === "" ||
    usernameInput.value?.modelValue == "" ||
    (usernameInput.value?.modelValue === userData.value.username &&
      passwordInput.value?.modelValue === userData.value.password)
);
</script>
