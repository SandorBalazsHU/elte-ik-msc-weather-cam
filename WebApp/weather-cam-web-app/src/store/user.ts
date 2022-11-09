import { defineStore } from "pinia";
import {
  UserApi,
  type LoginUserRequest,
  type User,
  Configuration,
  type ConfigurationParameters,
} from "@/api/openapi/index.js";
import { throwErrorByResponse } from "@/api/errors/CustomErrors.js";
import { useErrorStore, StationError, UserError } from "./errors.js";

const configOptions: ConfigurationParameters = {
  basePath: "http://127.0.0.1:4010",
};
const userApi = new UserApi(new Configuration(configOptions));

const errorStore = useErrorStore();

interface UserState {
  userData: User | null;
  bearerToken: string | null;
}

export const useUserStore = defineStore("user", {
  state: () =>
    ({
      userData: null,
      bearerToken: null,
    } as UserState),

  getters: {
    username: ({ userData }) => userData?.username,
    password: ({ userData }) => userData?.password,
    id: ({ userData }) => userData?.userId,
  },

  actions: {
    async login(loginData: LoginUserRequest) {
      try {
        const loginResult = await userApi.loginUserRaw({
          username: loginData.username,
          password: loginData.password,
        });
        if (!loginResult.raw.ok) {
          const result = await loginResult.value();
          errorStore.addError(UserError.LOGIN, result);
          throwErrorByResponse(loginResult.raw.status, result);
        }
        this.bearerToken = loginResult.raw.headers.get("Authorization");
        this.bearerToken && (configOptions.accessToken = this.bearerToken);
        const getUserResult = await userApi.getCurrentUserRaw();
        if (!getUserResult.raw.ok) {
          throwErrorByResponse(getUserResult.raw.status, await getUserResult.raw.json());
        }
        this.userData = await getUserResult.value();
      } catch (error) {
        if (error instanceof Error) {
          errorStore.addError(UserError.LOGIN, {
            code: 500,
            message: "Connection refused by server!",
          });
        }
        throw error;
      }
    },
  },
});
