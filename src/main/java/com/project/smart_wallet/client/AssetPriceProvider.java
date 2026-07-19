package com.project.smart_wallet.client;

import com.project.smart_wallet.client.dto.PriceLookupAsset;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface AssetPriceProvider {

    CompletableFuture<Map<String, BigDecimal>> getPricePerAsset(List<PriceLookupAsset> assets);
}
