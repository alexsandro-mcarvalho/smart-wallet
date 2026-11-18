import type { PaginationRequest } from "./pagination";

type AssetType = "CRYPTO_CURRENCY" | "STOCK";

export type AssetsRequest = PaginationRequest & {
  name?: string;
};

export type AssetSummaryResponse = {
  id: number;
  name: string;
  symbol: string;
  assetType: AssetType;
  logoUrl: string;
};
