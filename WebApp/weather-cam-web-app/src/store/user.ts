import { defineStore } from "pinia";

import { throwErrorByResponse, unifyError } from "@/api/errors/CustomErrors.js";
import router from "@/router/index.js";
import type { LoginUserRequest, User } from "@/api/openapi/index.js";
import { changeApiConfig, userApi } from "@/api/apis.js";
import type { FetchCallbacks } from "@/types/types.js";

interface UserState {
  userData: User;
  bearerToken: string | null;
}

export const useUserStore = defineStore("user", {
  state: () =>
    ({
      userData: {},
      bearerToken: null,
    } as UserState),

  getters: {
    username: ({ userData }) => userData?.username,
    password: ({ userData }) => userData?.password,
    id: ({ userData }) => userData?.userId,
  },

  actions: {
    async login(loginData: LoginUserRequest, callback?: FetchCallbacks) {
      try {
        const loginResult = await userApi.loginUserRaw({
          username: loginData.username,
          password: loginData.password,
        });
        if (!loginResult.raw.ok) {
          const result = await loginResult.value();
          throwErrorByResponse(loginResult.raw.status, result);
        }
        this.bearerToken = loginResult.raw.headers.get("Authorization");
        this.bearerToken && changeApiConfig({ accessToken: this.bearerToken });
        const getUserResult = await userApi.getCurrentUserRaw();
        if (!getUserResult.raw.ok) {
          throwErrorByResponse(getUserResult.raw.status, await getUserResult.raw.json());
        }
        this.userData = await getUserResult.value();
        callback?.onSuccess?.call(this);
        router.replace({ path: `user/${this.userData.username}/home` });
      } catch (error) {
        callback?.onError?.call(this, unifyError(error));
      }
    },

    async logout(callback?: FetchCallbacks) {
      try {
        const result = await userApi.logoutUserRaw();
        if (!result.raw.ok) {
          throwErrorByResponse(result.raw.status);
        }
        this.$reset();
        callback?.onSuccess?.call(this);
        router.push({ path: "/" });
      } catch (error) {
        callback?.onError?.call(this, unifyError(error));
      }
    },
  },
});
