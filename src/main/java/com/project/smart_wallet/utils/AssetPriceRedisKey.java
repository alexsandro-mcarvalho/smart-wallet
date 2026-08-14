package com.project.smart_wallet.utils;

import com.project.smart_wallet.domain.AssetType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AssetPriceRedisKey {

    private static String PREFIX = "asset-price:";

    public static String getKey(String assetIdentifier, AssetType assetType) {
        return PREFIX + assetType.name() + ":" + assetIdentifier;
    }
}
