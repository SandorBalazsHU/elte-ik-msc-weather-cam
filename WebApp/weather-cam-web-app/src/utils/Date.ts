// in miliseconds
export const unixToDateTime = (unixTimestamp: number) => new Date(unixTimestamp * 1000);

export const getRelativeTime = (d1: number, d2: number = Date.now() / 1000) => {
  const rtf = new Intl.RelativeTimeFormat("en", { numeric: "auto" });
  const elapsed = d1 - d2;
  console.log();
  return rtf.format(Math.floor(elapsed / 60), "minutes");
};

export const formatTimezone = (timezone: number) => (timezone < 0 ? timezone : `+${timezone}`);
