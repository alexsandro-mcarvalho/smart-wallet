type AssetType = "CRYPTO_CURRENCY" | "STOCK";

export type AssetSummaryResponse = {
  id: number;
  name: string;
  symbol: string;
  assetType: AssetType;
  logoUrl: string;
};
