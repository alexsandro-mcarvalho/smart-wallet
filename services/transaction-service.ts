import { apiFetch } from "@/lib/utils/api-request";
import type { CreateTransactionFormData } from "@/schemas/transaction.schema";
import { PaginationRequest, PaginationResponse } from "@/types/pagination";
import { CreateTransactionResponse, TransactionResponse } from "@/types/transactions";

export async function createTransaction(
  data: CreateTransactionFormData,
  idempotencyKey: string,
): Promise<CreateTransactionResponse> {
  return apiFetch<CreateTransactionResponse>("/api/v1/transactions", {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
      "Idempotency-Key": idempotencyKey,
    },
    body: JSON.stringify(data),
  });
}

export async function getTransactions({
  page,
  size,
}: PaginationRequest): Promise<PaginationResponse<TransactionResponse>> {
  const params = new URLSearchParams({
    page: page.toString(),
    size: size.toString(),
  });
  return apiFetch<PaginationResponse<TransactionResponse>>(`/api/v1/transactions?${params}`, {
    method: "GET",
    credentials: "include",
  });
}
