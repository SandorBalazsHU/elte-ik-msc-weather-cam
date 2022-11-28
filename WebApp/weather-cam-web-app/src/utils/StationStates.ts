import type HttpStatusCode from "./HttpStatusCode.js";
const STATION_STATES = new Map<HttpStatusCode, { color: string; text: string }>();
STATION_STATES.set(200, { color: "green", text: "Online" });
STATION_STATES.set(400, { color: "red", text: "Offline" });

export default STATION_STATES;
