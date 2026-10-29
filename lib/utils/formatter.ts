const currencyFormatter = new Intl.NumberFormat("pt-BR", {
  style: "currency",
  currency: "BRL",
  useGrouping: true,
});

export function formatCurrency(value: number): string {
  return currencyFormatter.format(value).replace(/\s/g, "");
}

const quantityFormatter = new Intl.NumberFormat("pt-BR", {
  useGrouping: true,
  maximumFractionDigits: 20,
});

export function formatQuantity(value: number): string {
  return quantityFormatter.format(value);
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
