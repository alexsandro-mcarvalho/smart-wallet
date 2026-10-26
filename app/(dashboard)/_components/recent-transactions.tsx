"use client";

import { ChevronRight, LoaderCircle } from "lucide-react";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import Image from "next/image";
import { formatCurrency, formatDate } from "@/lib/utils/formatter";
import Link from "next/link";
import useTransactions from "@/hooks/useTransactions";

const headers = ["ATIVO", "TIPO", "QUANTIDADE", "TOTAL TRANSACIONADO", "DATA DE TRANSAÇÃO"];

export default function RecentTransactions() {
  const { transactions, isLoading, error } = useTransactions();

  return (
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
          {isLoading ? (
            <TableRow>
              <TableCell colSpan={headers.length} className="h-32 text-center">
                <div role="status" className="flex items-center justify-center gap-2 text-gray-600">
                  <LoaderCircle className="h-5 w-5 animate-spin" aria-hidden="true" />
                  <span className="sr-only">Carregando transações...</span>
                </div>
              </TableCell>
            </TableRow>
          ) : error ? (
            <TableRow>
              <TableCell colSpan={headers.length} className="h-32 text-center">
                <p role="alert" className="text-red-600">
                  {error instanceof Error && error.message
                    ? error.message
                    : "Não foi possível carregar as transações. Tente novamente mais tarde."}
                </p>
              </TableCell>
            </TableRow>
          ) : transactions.length === 0 ? (
            <TableRow>
              <TableCell colSpan={headers.length} className="h-32 text-center text-gray-600">
                <p role="status">Não há nenhuma transação.</p>
              </TableCell>
            </TableRow>
          ) : (
            transactions.map((transaction) => (
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
            ))
          )}
        </TableBody>
      </Table>
    </div>
  );
}
