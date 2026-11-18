import { AssetSummaryResponse } from "./assets";

export type TransactionType = "BUY" | "SELL";

export type TransactionResponse = {
  id: number;
  asset: AssetSummaryResponse;
  type: TransactionType;
  quantity: string;
  price: string;
  unitPrice: string;
  transactionAt: string;
};

export type CreateTransactionResponse = {
  id: number;
  asset: AssetSummaryResponse;
  type: TransactionType;
  quantity: string;
  price: string;
  unitPrice: string;
  transactionAt: string;
  createdAt: string;
};
