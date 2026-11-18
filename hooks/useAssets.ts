"use client";

import { useEffect, useState } from "react";
import useSWRInfinite from "swr/infinite";

import { getAssets } from "@/services/asset-service";
import type { AssetsRequest, AssetSummaryResponse } from "@/types/assets";
import type { PaginationResponse } from "@/types/pagination";

type UseAssetsOptions = AssetsRequest & {
  enabled?: boolean;
};

export default function useAssets({ page, size, name = "", enabled = true }: UseAssetsOptions) {
  const query = name.trim();
  const [debouncedSearch, setDebouncedSearch] = useState(query);

  useEffect(() => {
    const timeout = setTimeout(() => setDebouncedSearch(query), 300);
    return () => clearTimeout(timeout);
  }, [query]);

  const isSearching = query !== debouncedSearch;
  const {
    data,
    error,
    isLoading,
    isValidating,
    size: pageCount,
    setSize: setPageCount,
    mutate,
  } = useSWRInfinite<PaginationResponse<AssetSummaryResponse>, Error>(
    (pageIndex, previousPage: PaginationResponse<AssetSummaryResponse> | null) => {
      if (!enabled || isSearching || (previousPage && !previousPage.hasNext)) return null;

      return { key: "assets", page: page + pageIndex, size, name: debouncedSearch };
    },
    getAssets,
    { revalidateFirstPage: false },
  );

  const assets = data?.flatMap((page) => page.data) ?? [];
  const paginationResponse = data?.[data.length - 1];
  const hasNext = paginationResponse?.hasNext ?? false;
  const isLoadingMore = isLoading || (!error && !!data && data.length < pageCount);

  function loadMore() {
    if (!enabled || !hasNext || isSearching || isLoadingMore || isValidating || error) return;

    return setPageCount((currentCount) => currentCount + 1);
  }

  return {
    assets,
    paginationResponse,
    hasNext,
    isLoading: enabled && (isLoading || isSearching),
    isLoadingMore,
    error,
    loadMore,
    retry: () => mutate(),
  };
}
