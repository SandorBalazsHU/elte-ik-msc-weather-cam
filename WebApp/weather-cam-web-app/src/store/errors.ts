import type { ModelApiResponse } from "@/api/openapi/index.js";
import { defineStore } from "pinia";

export enum UserError {
  LOGIN = "login",
  REGISTER = "register",
}

export enum StationError {
  OFFLINE = "offline",
}

export interface ErrorRecord {
  errorId: number;
  type: ErrorType;
  payload: ModelApiResponse;
}
export type ErrorType = StationError | UserError;
interface ErrorState {
  errorId: number;
  errors: ErrorRecord[];
}

export const useErrorStore = defineStore("error", {
  state: () =>
    ({
      errorId: 0,
      errors: [],
    } as ErrorState),
  actions: {
    addError(type: ErrorType, payload: ModelApiResponse) {
      return this.errors.push({ errorId: this.errorId++, type, payload });
    },
    removeErrorById(id: number) {
      this.errors = this.errors.filter((error) => error.errorId !== id);
    },
    removeError(errorObj: ErrorRecord) {
      this.errors = this.errors.filter((error) => error !== errorObj);
    },
    getErrorById(id: number) {
      this.errors.find((error) => error.errorId === id);
    },
    getErrorsByType(type: ErrorType) {
      return this.errors.filter((error) => error.type === type);
    },
    removeErrorsByType(type: ErrorType) {
      this.errors = this.errors.filter((error) => error.type !== type);
    },
  },
});
