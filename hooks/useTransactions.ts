import { getTransactions } from "@/services/transaction-service";
import { PaginationRequest } from "@/types/pagination";
import useSWR from "swr";

export default function useTransactions({ page, size }: PaginationRequest) {
  const request = {
    key: "transactions",
    page,
    size,
  };

  const { data, isLoading, error } = useSWR(request, getTransactions);

  return {
    transactions: data?.data || [],
    isLoading,
    error,
  };
}
