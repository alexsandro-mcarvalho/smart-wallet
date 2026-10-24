import BalanceCard from "@/app/(dashboard)/_components/balance-card";
import { BanknoteArrowDown, BanknoteArrowUp, ChevronRight, HandCoins } from "lucide-react";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableFooter,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import type { Transaction } from "@/types/transactions";
import Image from "next/image";
import { formatCurrency, formatDate } from "@/lib/utils/formatter";
import Link from "next/link";

const headers = ["ATIVO", "TIPO", "QUANTIDADE", "TOTAL TRANSACIONADO", "DATA DE TRANSAÇÃO"];
const transactions: Transaction[] = [
  {
    id: 1,
    asset: {
      id: 1,
      name: "Bitcoin",
      symbol: "BTC",
      assetType: "CRYPTO_CURRENCY",
      logoUrl: "/iphone.png",
    },
    type: "BUY",
    quantity: 1,
    price: 50000,
    transactionAt: "2023-01-01T12:00:00Z",
  },
  {
    id: 2,
    asset: {
      id: 2,
      name: "Ethereum",
      symbol: "ETH",
      assetType: "CRYPTO_CURRENCY",
      logoUrl: "/iphone.png",
    },
    type: "BUY",
    quantity: 2,
    price: 8000,
    transactionAt: "2023-01-02T12:00:00Z",
  },
];

export default function DashboardLayout() {
  return (
    <div>
      <div className="mb-6 grid grid-cols-1 gap-6 md:grid-cols-3">
        <BalanceCard label="Saldo da conta" amount={1000} icon={HandCoins} variant="secondary" />
        <BalanceCard label="Total Investido" amount={1000} icon={BanknoteArrowDown} />
        <BalanceCard label="Lucro" amount={1000} icon={BanknoteArrowUp} />
      </div>
      <div className="rounded-lg border border-gray-300 px-6 py-5">
        <div className="flex items-center justify-between">
          <span className="text-lg font-semibold text-text-1">Transações Recentes</span>
          <Link
            href="/transactions"
            className="flex cursor-pointer items-center gap-3 text-sm font-semibold text-green-600 transition-colors duration-300 hover:text-green-700"
          >
            Ver todas
            <ChevronRight size={16} />
          </Link>
        </div>
        <Table>
          <TableHeader>
            <TableRow>
              {headers.map((header, index) => (
                <TableHead key={index}>{header}</TableHead>
              ))}
            </TableRow>
          </TableHeader>
          <TableBody>
            {transactions.map((transaction) => (
              <TableRow key={transaction.id}>
                <TableCell>
                  <div className="flex items-center gap-3">
                    <Image
                      src={transaction.asset.logoUrl}
                      alt={transaction.asset.name}
                      width={40}
                      height={40}
                      className="h-10 w-10 object-contain"
                    />
                    <div className="flex flex-col gap-1">
                      <span className="text-sm font-medium text-text-1">{transaction.asset.name}</span>
                      <span className="text-xs font-light text-gray-600">{transaction.asset.symbol}</span>
                    </div>
                  </div>
                </TableCell>
                <TableCell>{transaction.type}</TableCell>
                <TableCell className="font-semibold text-text-1">{transaction.quantity}</TableCell>
                <TableCell className="font-semibold text-text-1">{formatCurrency(transaction.price)}</TableCell>
                <TableCell>{formatDate(transaction.transactionAt)}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>
    </div>
  );
}
