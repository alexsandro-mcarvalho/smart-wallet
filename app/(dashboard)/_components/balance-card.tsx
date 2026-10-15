import { formatCurrency } from "@/lib/utils/formatter";
import { LucideIcon } from "lucide-react";

type BalanceCardProps = {
  label: string;
  amount: number;
  icon: LucideIcon;
  variant?: "primary" | "secondary";
};

const balanceCardVariants = {
  primary: "bg-gray-200",
  secondary: "bg-[#363A3F]",
};

const balanceCardIconVariants = {
  primary: "bg-[#EBE8E8] text-[#363A3F]",
  secondary: "bg-[#4E5257] text-lime-300",
};

const balanceCardLabelVariants = {
  primary: "text-gray-600",
  secondary: "text-text-2",
};

const balanceCardAmountVariants = {
  primary: "text-text-1",
  secondary: "text-white",
};

export default function BalanceCard({ label, amount, icon: BalanceCardIcon, variant = "primary" }: BalanceCardProps) {
  return (
    <div className={`flex items-center gap-4 rounded-lg px-5 py-6 ${balanceCardVariants[variant]}`}>
      <div className={`flex h-11 w-11 items-center justify-center rounded-full ${balanceCardIconVariants[variant]}`}>
        <BalanceCardIcon size={20} />
      </div>
      <div className="flex flex-col gap-2">
        <span className={`text-base font-normal ${balanceCardLabelVariants[variant]}`}>{label}</span>
        <span className={`text-2xl font-bold ${balanceCardAmountVariants[variant]}`}>{formatCurrency(amount)}</span>
      </div>
    </div>
  );
}
