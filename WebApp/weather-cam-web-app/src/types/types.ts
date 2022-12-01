import type { HttpError } from "@/api/errors/CustomErrors.js";

export type NavDrawerLinks = Array<{
  icon: string;
  name: string;
  routerLink: string;
}>;

export type NavDrawerContacts = Array<{ icon: string; name: string }>;
export type ColorThemes = "dark" | "light";

export type AlertTypes = "error" | "success" | "warning" | "info" | undefined;
export interface FetchCallbacks {
  onSuccess?: () => any;
  onError?: (error: HttpError) => any;
}
