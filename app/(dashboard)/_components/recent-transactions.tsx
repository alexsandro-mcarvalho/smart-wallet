"use client";

import { ChevronRight } from "lucide-react";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import TableAssetCell from "@/components/table/table-asset-cell";
import TableEmpty from "@/components/table/table-empty";
import TableError from "@/components/table/table-error";
import TableLoading from "@/components/table/table-loading";
import { formatCurrency, formatDate, formatQuantity } from "@/lib/utils/formatter";
import Link from "next/link";
import useTransactions from "@/hooks/useTransactions";
import TransactionTypeBadge from "./transaction-type-badge";

const headers = ["ATIVO", "TIPO", "QUANTIDADE", "TOTAL TRANSACIONADO", "DATA DE TRANSAÇÃO"];

export default function RecentTransactions() {
  const { transactions, isLoading, error } = useTransactions();

  return (
    <div className="rounded-lg border border-gray-500 px-6 py-5">
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
          {isLoading ? (
            <TableLoading colSpan={headers.length} message="Carregando transações..." />
          ) : error ? (
            <TableError
              colSpan={headers.length}
              message={
                error instanceof Error && error.message
                  ? error.message
                  : "Não foi possível carregar as transações. Tente novamente mais tarde."
              }
            />
          ) : transactions.length === 0 ? (
            <TableEmpty colSpan={headers.length} />
          ) : (
            transactions.map((transaction) => (
              <TableRow key={transaction.id}>
                <TableAssetCell
                  logoUrl={transaction.asset.logoUrl}
                  name={transaction.asset.name}
                  symbol={transaction.asset.symbol}
                />
                <TableCell>
                  <TransactionTypeBadge type={transaction.type} />
                </TableCell>
                <TableCell variant="secondary">{formatQuantity(transaction.quantity)}</TableCell>
                <TableCell variant="secondary">{formatCurrency(transaction.price)}</TableCell>
                <TableCell>{formatDate(transaction.transactionAt)}</TableCell>
              </TableRow>
            ))
          )}
        </TableBody>
      </Table>
    </div>
  );
}
