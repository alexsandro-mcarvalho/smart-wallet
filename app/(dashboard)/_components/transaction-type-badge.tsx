import type { TransactionResponse } from "@/types/transactions";

type TransactionTypeBadgeProps = {
  type: TransactionResponse["type"];
};

const transactionTypeVariants = {
  BUY: { label: "Compra", className: "bg-[#D9FFE9] text-[#29A073]" },
  SELL: { label: "Venda", className: "bg-[#FFEFEF] text-[#EB5757]" },
};

export default function TransactionTypeBadge({ type }: TransactionTypeBadgeProps) {
  const { label, className } = transactionTypeVariants[type];

  return <span className={`inline-block rounded-sm px-4 py-2 ${className}`}>{label}</span>;
}
