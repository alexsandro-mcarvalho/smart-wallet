export function formatCurrencyInput(value: string): string {
  if (!value) return "";

  const [integer, decimals = ""] = value.split(".");
  const groupedInteger = integer.replace(/\B(?=(\d{3})+(?!\d))/g, ".");
  return `R$${groupedInteger},${decimals.padEnd(2, "0")}`;
}

export function parseCurrencyInput(displayValue: string): string {
  const digits = displayValue.replace(/\D/g, "").replace(/^0+(?=\d)/, "");
  if (!digits) return "";

  const cents = digits.padStart(3, "0");
  return `${cents.slice(0, -2)}.${cents.slice(-2)}`;
}
