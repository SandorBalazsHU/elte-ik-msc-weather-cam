<template>
  <div class="center">
    <v-card :loading="loading" elevation="24" :width="calcFormSize" variant="tonal">
      <v-card-title>
        <div class="d-flex justify-space-between">
          <span
            ><v-icon class="mr-2" icon="mdi-cloud"></v-icon>{{ login ? "Login" : "Register" }}</span
          >
          <v-btn @click="backToHomeHandler" prepend-icon="mdi-arrow-left-circle" variant="text"
            >Back</v-btn
          >
        </div></v-card-title
      >
      <v-divider class="mx-2 mb-2"></v-divider>
      <v-form class="pa-2" ref="form" v-model="valid">
        <v-text-field
          :rules="login ? usernameLoginRules : usernameRegisterRules"
          label="Username"
          v-model="formData.username"
          type="text"
          required
        ></v-text-field>

        <v-text-field
          type="password"
          :rules="login ? passwordLoginRules : passwordAgainRegisterRules(formData.password)"
          v-model="formData.password"
          label="Password"
          @input="validate"
          required
        ></v-text-field>

        <v-text-field
          v-if="!login"
          type="password"
          :rules="passwordAgainRegisterRules(formData.password)"
          v-model="formData.passwordAgain"
          label="Password again"
          required
          @input="validate"
        ></v-text-field>

        <v-card-actions class="justify-end">
          <v-btn
            class="w-50"
            @click="validate, authHandler()"
            color="green"
            :disabled="!valid"
            variant="tonal"
            size="large"
            >{{ login ? "Login" : "Register" }}</v-btn
          >
        </v-card-actions>
        <ws-alert-container id="login-errors" v-show="login" :max-displayed="1" :max-stored="1">
        </ws-alert-container>
      </v-form>
      <v-divider></v-divider>
      <v-tabs class="w-100" fixed-tabs bg-color="black-darken-2">
        <v-tab class="tab-widen" @click="navToLoginHandler" :disabled="login"> Login </v-tab>
        <v-tab class="tab-widen" @click="navToRegisterHandler" :disabled="!login"> Register </v-tab>
      </v-tabs>
    </v-card>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref, type Ref } from "vue";
import { VForm } from "vuetify/components/VForm";
import router from "@/router/index.js";
import { useUserStore } from "@/store/user.js";
import { useAlertStore } from "@/store/alert.js";
import WsAlertContainer from "@/components/WsAlertContainer.vue";
import calcFormSize from "@/utils/Sizing.js";
import {
  passwordAgainRegisterRules,
  passwordLoginRules,
  usernameLoginRules,
  usernameRegisterRules,
} from "@/utils/FormValidators.js";

const valid = ref(false);
const login = ref(true);
const loading = ref(false);
const form: unknown = ref(null);
const formData = reactive({ valid: false, username: "", password: "", passwordAgain: "" });

const userStore = useUserStore();
const alertStore = useAlertStore();

const backToHomeHandler = () => router.push({ path: "/" });

function authHandler() {
  valid.value && (login.value ? loginUser() : registerUser());
}

function navToRegisterHandler() {
  router.replace({ path: "/register" });
  login.value = !login.value;
  resetInputs();
}

function navToLoginHandler() {
  router.replace({ path: "/login" });
  login.value = !login.value;
  resetInputs();
}

function resetInputs() {
  (form as Ref<VForm>).value!.reset();
}
async function validate() {
  valid.value = await (form as Ref<VForm>)
    .value!.validate()
    .then((res) => res.valid)
    .catch((err) => err);
}

async function loginUser() {
  loading.value = true;
  alertStore.clearAlerts("login-errors");
  await userStore.login({
    username: formData.username,
    password: formData.password,
  });
  loading.value = false;
}

async function registerUser() {
  loading.value = true;

  loading.value = false;
}

onMounted(() => {
  login.value = router.currentRoute.value.path === "/login";
});
</script>

<style scoped>
.center {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.tab-widen {
  max-width: 50% !important;
}
</style>
