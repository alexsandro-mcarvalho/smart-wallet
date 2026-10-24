type AssetType = "CRYPTO_CURRENCY" | "STOCK";

type TransactionType = "BUY" | "SELL";

export type AssetSummaryResponse = {
  id: number;
  name: string;
  symbol: string;
  assetType: AssetType;
  logoUrl: string;
};

export type Transaction = {
  id: number;
  asset: AssetSummaryResponse;
  type: TransactionType;
  quantity: number;
  price: number;
  transactionAt: string;
};
