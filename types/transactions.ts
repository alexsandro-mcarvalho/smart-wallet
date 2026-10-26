import { AssetSummaryResponse } from "./assets";

type TransactionType = "BUY" | "SELL";

export type TransactionResponse = {
  id: number;
  asset: AssetSummaryResponse;
  type: TransactionType;
  quantity: number;
  price: number;
  transactionAt: string;
};
