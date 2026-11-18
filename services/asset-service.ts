import { apiFetch } from "@/lib/utils/api-request";
import type { AssetsRequest, AssetSummaryResponse } from "@/types/assets";
import type { PaginationResponse } from "@/types/pagination";

export async function getAssets({
  page,
  size,
  name,
}: AssetsRequest): Promise<PaginationResponse<AssetSummaryResponse>> {
  const params = new URLSearchParams({
    page: page.toString(),
    size: size.toString(),
  });

  if (name?.trim()) params.set("name", name.trim());

  return apiFetch<PaginationResponse<AssetSummaryResponse>>(`/api/v1/assets?${params}`, {
    method: "GET",
    credentials: "include",
  });
}
