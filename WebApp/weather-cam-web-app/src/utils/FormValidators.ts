export const usernameLoginRules: Array<(a: string) => boolean | string> = [
  (v) => !!v || "Name is required!",
];

export const passwordLoginRules: Array<(a: string) => boolean | string> = [
  (v) => !!v || "Password is required!",
];

export const usernameRegisterRules: Array<(a: string) => boolean | string> = [
  (v) => !!v || "Name is required!",
];

export const passwordAgainRegisterRules: (pw: string) => Array<(a: string) => boolean | string> = (
  pw
) => [(v) => v === pw || "Passwords do not match", (v) => !!v || "Password is required!"];
