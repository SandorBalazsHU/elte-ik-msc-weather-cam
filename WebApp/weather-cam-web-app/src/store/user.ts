import { defineStore } from "pinia";
import {
  UserApi,
  type LoginUserRequest,
  type User,
  Configuration,
  type ConfigurationParameters,
} from "@/api/openapi/index.js";
import { throwErrorByResponse } from "@/api/errors/CustomErrors.js";
import { useAlertStore } from "./alert.js";
import router from "@/router/index.js";

const configOptions: ConfigurationParameters = {
  basePath: "http://127.0.0.1:4010",
};
const userApi = new UserApi(new Configuration(configOptions));

interface UserState {
  userData: User;
  bearerToken: string | null;
}

export const useUserStore = defineStore("user", {
  state: () =>
    ({
      userData: { username: "Noratan" },
      bearerToken: "dev",
    } as UserState),

  getters: {
    username: ({ userData }) => userData?.username,
    password: ({ userData }) => userData?.password,
    id: ({ userData }) => userData?.userId,
  },

  actions: {
    async login(loginData: LoginUserRequest, propagateError: boolean = false) {
      try {
        const loginResult = await userApi.loginUserRaw({
          username: loginData.username,
          password: loginData.password,
        });
        if (!loginResult.raw.ok) {
          const result = await loginResult.value();
          useAlertStore().addAlert("login-errors", result);
          throwErrorByResponse(loginResult.raw.status, result);
        }
        this.bearerToken = loginResult.raw.headers.get("Authorization");
        this.bearerToken && (configOptions.accessToken = this.bearerToken);
        const getUserResult = await userApi.getCurrentUserRaw();
        if (!getUserResult.raw.ok) {
          throwErrorByResponse(getUserResult.raw.status, await getUserResult.raw.json());
        }
        this.userData = await getUserResult.value();
        router.replace({ path: `user/${this.userData.username}/stations` });
      } catch (error) {
        if (error instanceof Error) {
          useAlertStore().addAlert("login-errors", {
            code: 500,
            message: "Connection refused by server!",
            type: "error",
          });
        }
        if (propagateError) throw error;
      }
    },
  },
});
