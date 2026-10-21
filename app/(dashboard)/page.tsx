import BalanceCard from "@/app/(dashboard)/_components/balance-card";
import { BanknoteArrowDown, BanknoteArrowUp, HandCoins } from "lucide-react";

export default function DashboardLayout() {
  return (
    <div className="grid grid-cols-1 gap-6 md:grid-cols-3">
      <BalanceCard label="Total Balance" amount={1000} icon={HandCoins} variant="secondary" />
      <BalanceCard label="Total Spending" amount={1000} icon={BanknoteArrowDown} />
      <BalanceCard label="Total Income" amount={1000} icon={BanknoteArrowUp} />
    </div>
  );
}
