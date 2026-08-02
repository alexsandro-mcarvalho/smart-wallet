package com.project.smart_wallet.client;

import com.project.smart_wallet.client.dto.AssetPriceLookUp;
import com.project.smart_wallet.domain.AssetType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AssetPriceProvider {

    int getQuantityPerRequest();

    AssetType getAssetType();

    Map<String, BigDecimal> getPricePerAsset(List<AssetPriceLookUp> assets);
}
