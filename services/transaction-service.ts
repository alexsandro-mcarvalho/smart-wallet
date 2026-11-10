import { apiFetch } from "@/lib/utils/api-request";
import { PaginationRequest, PaginationResponse } from "@/types/pagination";
import { TransactionResponse } from "@/types/transactions";

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
