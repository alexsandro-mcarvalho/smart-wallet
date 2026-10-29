"use client";

import BalanceCard from "@/app/(dashboard)/_components/balance-card";
import RecentTransactions from "@/app/(dashboard)/_components/recent-transactions";
import { getBalance } from "@/services/wallet-service";
import { BanknoteArrowDown, BanknoteArrowUp, HandCoins } from "lucide-react";
import useSWR from "swr";
import { BalanceEvolutionChart } from "./_components/balance-evolution-chart";

export default function DashboardPage() {
  const { data, isLoading, error } = useSWR("/api/v1/wallet", getBalance);

  return (
    <div className="flex flex-col gap-6">
      <div className="grid grid-cols-1 gap-6 md:grid-cols-3">
        <BalanceCard label="Saldo da conta" amount={data?.totalBalance || 0} icon={HandCoins} variant="secondary" />
        <BalanceCard label="Total Investido" amount={data?.totalSpending || 0} icon={BanknoteArrowDown} />
        <BalanceCard label="Lucro" amount={data?.profit || 0} icon={BanknoteArrowUp} />
      </div>

      <BalanceEvolutionChart />
      <RecentTransactions />
    </div>
  );
}
