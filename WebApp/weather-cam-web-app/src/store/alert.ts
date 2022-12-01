import type { HttpError } from "@/api/errors/CustomErrors.js";
import type { ModelApiResponse } from "@/api/openapi/index.js";
import { defineStore } from "pinia";

export type AlertTypes = "success" | "info" | "error" | "warning";
type AlertData = ModelApiResponse;
export interface AlertRecord {
  alertId: number;
  alertType: AlertTypes;
  payload: AlertData | HttpError;
}

interface AlertState {
  alertId: number;
  alertContainers: Map<string, AlertRecord[]>;
  inactiveAlertContainers: Map<string, AlertRecord[]>;
}

export const useAlertStore = defineStore("alert", {
  state: () =>
    ({
      alertId: 0,
      alertContainers: new Map(),
      inactiveAlertContainers: new Map(),
    } as AlertState),
  actions: {
    addAlert(
      containerId: string,
      payload: ModelApiResponse | HttpError,
      alertType: AlertTypes = "error"
    ) {
      const exists = this.alertContainers.get(containerId);
      if (!exists) return false;
      exists?.push({ alertType, alertId: this.alertId++, payload });
      this.alertContainers.set(containerId, exists);
      return exists;
    },

    forgetLastAlerts(containerId: string, numberOfAlertsToForget: number) {
      if (numberOfAlertsToForget < 1) return false;
      const exists = this.alertContainers.get(containerId);
      if (exists) {
        this.alertContainers.set(containerId, exists.slice(numberOfAlertsToForget));
        return true;
      }
      return false;
    },

    clearAlerts(containerId: string) {
      const exists = this.alertContainers.has(containerId);
      if (!exists) return false;
      this.alertContainers.set(containerId, []);
      return true;
    },

    registerAlertContainer(containerId: string) {
      if (this.alertContainers.has(containerId)) {
        console.warn(`Alert container with id: ${containerId} already exists!\n`);
        return false;
      } else {
        const exists = this.inactiveAlertContainers.get(containerId);
        this.alertContainers.set(containerId, exists ? exists : []);
        return true;
      }
    },

    unRegisterAlertContainer(containerId: string, keep_alerts = false) {
      const exists = this.alertContainers.get(containerId);
      exists && keep_alerts && this.inactiveAlertContainers.set(containerId, exists);
      return this.alertContainers.delete(containerId);
    },
    removeAlertById(containerId: string, alertId: number): boolean {
      const container = this.alertContainers.get(containerId);
      if (!container) return false;
      const alerts = container?.filter((alert) => alert.alertId !== alertId);
      this.alertContainers.set(containerId, alerts);
      return true;
    },
  },
});
