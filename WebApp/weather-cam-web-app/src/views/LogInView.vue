<template>
  <div class="center">
    <v-card elevation="24" :min-width="calcFormSize" variant="tonal">
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
          :rules="login ? passwordLoginRules : passwordRegisterRules"
          v-model="password"
          label="Password"
          @input="validate"
          required
        ></v-text-field>

        <v-text-field
          v-if="!login"
          type="password"
          :rules="passwordRegisterRules"
          v-model="passwordAgain"
          label="Password again"
          required
          @input="validate"
        ></v-text-field>

        <v-card-actions class="justify-end">
          <v-btn
            class="w-50"
            @click="
              validate,
                valid && router.replace({ path: `user/${username}/stations` })
            "
            color="green"
            :disabled="!valid"
            variant="tonal"
            size="large"
            >{{ login ? "Login" : "Register" }}</v-btn
          >
        </v-card-actions>
      </v-form>
      <v-divider></v-divider>
      <v-tabs class="w-100" fixed-tabs bg-color="black-darken-2">
        <v-tab
          class="tab-widen"
          @click="
            router.replace({ path: '/login' }), (login = !login), resetInputs()
          "
          :disabled="login"
        >
          Login
        </v-tab>
        <v-tab
          class="tab-widen"
          @click="
            router.replace({ path: '/register' }),
              (login = !login),
              resetInputs()
          "
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
import vuetify from "@/plugins/vuetify.js";
import { VForm } from "vuetify/components/VForm";
import { computed } from "vue";
import router from "@/router/index.js";
const valid = ref(false);
const login = ref(false);

const username = ref("");
const password = ref("");
const passwordAgain = ref("");
const form: unknown = ref(null);
function resetInputs() {
  (form as Ref<VForm>).value!.reset();
}
async function validate() {
  valid.value = await (form as Ref<VForm>)
    .value!.validate()
    .then((res) => res.valid)
    .catch((err) => err);
}

onMounted(() => {
  login.value = router.currentRoute.value.path === "/login";
});

const calcFormSize = computed(() => {
  switch (vuetify.display.name.value) {
    case "xs":
      return 320;
    case "sm":
      return 450;
    case "md":
      return 580;
    case "lg":
      return 650;
    case "xl":
      return 720;
  }
  return 800;
});

const usernameLoginRules: Array<(a: string) => boolean | string> = [
  (v) => !!v || "Name is required!",
];

const passwordLoginRules: Array<(a: string) => boolean | string> = [
  (v) => !!v || "Password is required!",
];

const usernameRegisterRules: Array<(a: string) => boolean | string> = [
  (v) => !!v || "Name is required!",
];

const passwordRegisterRules: Array<(a: string) => boolean | string> = [
  (v) => v === password.value || "Passwords do not match",
  (v) => !!v || "Password is required!",
];
</script>

<style scoped>
.center {
  display: flex;
  height: 100%;
  align-items: center;
  justify-content: center;
}

.tab-widen {
  max-width: 50% !important;
}
</style>
