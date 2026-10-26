import { apiFetch } from "@/lib/utils/api-request";
import { PaginationResponse } from "@/types/pagination";
import { TransactionResponse } from "@/types/transactions";

export async function getTransactions(): Promise<PaginationResponse<TransactionResponse>> {
  return apiFetch<PaginationResponse<TransactionResponse>>("/api/v1/transactions", {
    method: "GET",
    credentials: "include",
  });
}
