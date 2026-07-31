package com.project.smart_wallet.client;

import com.project.smart_wallet.client.dto.PriceLookupAsset;
import com.project.smart_wallet.domain.AssetType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface AssetPriceProvider {

    int getQuantityPerRequest();

    AssetType getAssetType();

    CompletableFuture<Map<String, BigDecimal>> getPricePerAsset(List<PriceLookupAsset> assets);
}
