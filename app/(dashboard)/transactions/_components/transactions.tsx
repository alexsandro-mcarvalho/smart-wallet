"use client";

import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import useTransactions from "@/hooks/useTransactions";
import TransactionTypeBadge from "../../_components/transaction-type-badge";
import { formatCurrency, formatDate, formatQuantity, formatTime } from "@/lib/utils/formatter";
import TableAssetCell from "@/components/table/table-asset-cell";
import TableEmpty from "@/components/table/table-empty";
import TableError from "@/components/table/table-error";
import TableLoading from "@/components/table/table-loading";
import PaginationControls from "@/components/pagination-controls";
import { useState } from "react";

const headers = ["ATIVO", "TIPO", "QUANTIDADE", "PREÇO UNITÁRIO", "TOTAL TRANSACIONADO", "DATA DE TRANSAÇÃO"];

export default function Transactions() {
  const [currentPage, setCurrentPage] = useState(0);
  const { transactions, paginationResponse, isLoading, error } = useTransactions({ page: currentPage, size: 8 });

  return (
    <div>
      <Table className="mb-5">
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
                <TableCell variant="secondary">{formatCurrency(transaction.unitPrice)}</TableCell>
                <TableCell variant="secondary">{formatCurrency(transaction.price)}</TableCell>
                <TableCell>
                  <div className="flex flex-col gap-1">
                    <span className="text-text-1">{formatDate(transaction.transactionAt)}</span>
                    <span>às {formatTime(transaction.transactionAt)}</span>
                  </div>
                </TableCell>
              </TableRow>
            ))
          )}
        </TableBody>
      </Table>
      {!isLoading && !error && paginationResponse && (
        <PaginationControls paginationResponse={paginationResponse} onPageChange={setCurrentPage} />
      )}
    </div>
  );
}
