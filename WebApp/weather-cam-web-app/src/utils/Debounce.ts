export function debounce(
  this: unknown,
  func: (...args: unknown[]) => unknown,
  timeout = 300
) {
  let timer: string | number | NodeJS.Timeout | undefined;
  return (...args: any) => {
    clearTimeout(timer);
    timer = setTimeout(() => {
      func.apply<any, any[], any>(this, args);
    }, timeout);
  };
}

export default debounce;
