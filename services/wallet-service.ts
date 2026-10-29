import { apiFetch } from "@/lib/utils/api-request";
import { BalanceResponse } from "@/types/wallet";

export async function getBalance(): Promise<BalanceResponse> {
  const response = await apiFetch<BalanceResponse>("/api/v1/wallet", {
    method: "GET",
    credentials: "include",
  });
  return response;
}
