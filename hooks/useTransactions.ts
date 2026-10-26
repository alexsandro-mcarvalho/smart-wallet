import { getTransactions } from "@/services/transaction-service";
import useSWR from "swr";

export default function useTransactions() {
  const { data, isLoading, error } = useSWR("transactions", getTransactions);

  return {
    transactions: data?.data || [],
    isLoading,
    error,
  };
}
