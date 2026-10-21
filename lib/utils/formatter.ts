const currencyFormatter = new Intl.NumberFormat("pt-BR", {
  style: "currency",
  currency: "BRL",
  useGrouping: false,
});

export function formatCurrency(value: number): string {
  return currencyFormatter.format(value).replace(/\s/g, "");
}
