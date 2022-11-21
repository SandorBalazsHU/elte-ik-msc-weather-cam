import type { VitePWAOptions } from "vite-plugin-pwa";

const pwaConfig: Partial<VitePWAOptions> | undefined = {
  registerType: "autoUpdate",
  includeAssets: [
    "favicon.ico",
    "img/icons/pwa-cloud-192x192.png",
    "img/icons/pwa-cloud-512x512.png",
  ],
  workbox: {
    navigationPreload: false,
    skipWaiting: false,
    cleanupOutdatedCaches: true,
    clientsClaim: true,
    sourcemap: false,
  },
  manifest: {
    name: "Weather Camera",
    display: "standalone",
    short_name: "WCA",
    theme_color: "#ffffff",
    icons: [
      {
        src: "img/icons/pwa-cloud-192x192.png",
        sizes: "192x192",
        type: "image/png",
      },
      {
        src: "img/icons/pwa-cloud-512x512.png",
        sizes: "512x512",
        type: "image/png",
      },
    ],
  },
};

export default pwaConfig;
