<template>
  <div class="center">
    <v-card :loading="loading" elevation="24" :width="calcFormSize" variant="tonal">
      <v-card-title
        ><v-icon class="mr-2" icon="mdi-cloud"></v-icon
        >{{ login ? "Login" : "Register" }}</v-card-title
      >
      <v-divider class="mx-2 mb-2"></v-divider>
      <v-form class="pa-2" ref="form" v-model="valid">
        <v-text-field
          :rules="login ? usernameLoginRules : usernameRegisterRules"
          label="Username"
          v-model="username"
          type="text"
          required
        ></v-text-field>

        <v-text-field
          type="password"
          :rules="login ? passwordLoginRules : passwordAgainRegisterRules(password)"
          v-model="password"
          label="Password"
          @input="validate"
          required
        ></v-text-field>

        <v-text-field
          v-if="!login"
          type="password"
          :rules="passwordAgainRegisterRules(password)"
          v-model="passwordAgain"
          label="Password again"
          required
          @input="validate"
        ></v-text-field>

        <v-card-actions class="justify-end">
          <v-btn
            class="w-50"
            @click="validate, valid && loginUser()"
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
        <v-tab
          class="tab-widen"
          @click="router.replace({ path: '/login' }), (login = !login), resetInputs()"
          :disabled="login"
        >
          Login
        </v-tab>
        <v-tab
          class="tab-widen"
          @click="router.replace({ path: '/register' }), (login = !login), resetInputs()"
          :disabled="!login"
        >
          Register
        </v-tab>
      </v-tabs>
    </v-card>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref, type Ref } from "vue";
import { VForm } from "vuetify/components/VForm";
import router from "@/router/index.js";
import { useUserStore } from "@/store/user.js";
import { storeToRefs } from "pinia";
import { useAlertStore } from "@/store/alert.js";
import WsAlertContainer from "@/components/WsAlertContainer.vue";
import calcFormSize from "@/utils/FormSizing.js";
import {
  passwordAgainRegisterRules,
  passwordLoginRules,
  usernameLoginRules,
  usernameRegisterRules,
} from "@/utils/FormValidators.js";

const valid = ref(false);
const login = ref(false);
const loading = ref(false);
const username = ref("");
const password = ref("");
const passwordAgain = ref("");
const form: unknown = ref(null);

const userStore = useUserStore();
const alertStore = useAlertStore();
const { userData } = storeToRefs(userStore);

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
  try {
    await userStore
      .login({
        username: username.value,
        password: password.value,
      })
      .then(() => {
        router.replace({ path: `user/${userData.value?.username}/stations` });
      });
  } catch (error) {
    console.error("An error occured during the login process!");
  }
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
