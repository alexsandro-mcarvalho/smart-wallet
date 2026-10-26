"use client";

import BalanceCard from "@/app/(dashboard)/_components/balance-card";
import RecentTransactions from "@/app/(dashboard)/_components/recent-transactions";
import { BanknoteArrowDown, BanknoteArrowUp, HandCoins } from "lucide-react";

export default function DashboardPage() {
  return (
    <div>
      <div className="mb-6 grid grid-cols-1 gap-6 md:grid-cols-3">
        <BalanceCard label="Saldo da conta" amount={1000} icon={HandCoins} variant="secondary" />
        <BalanceCard label="Total Investido" amount={1000} icon={BanknoteArrowDown} />
        <BalanceCard label="Lucro" amount={1000} icon={BanknoteArrowUp} />
      </div>
      <RecentTransactions />
    </div>
  );
}
