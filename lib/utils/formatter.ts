const currencyFormatter = new Intl.NumberFormat("pt-BR", {
  style: "currency",
  currency: "BRL",
  useGrouping: false,
});

export function formatCurrency(value: number): string {
  return currencyFormatter.format(value).replace(/\s/g, "");
}

const dateFormatter = new Intl.DateTimeFormat("pt-BR", {
  day: "numeric",
  month: "long",
  year: "numeric",
  timeZone: "UTC",
});

export function formatDate(value: string): string {
  return dateFormatter
    .formatToParts(new Date(value))
    .filter((part) => part.type !== "literal")
    .map((part) => part.value)
    .join(" ");
}
