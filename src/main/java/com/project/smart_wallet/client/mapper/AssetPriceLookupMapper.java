package com.project.smart_wallet.client.mapper;

import com.project.smart_wallet.client.dto.AssetPriceLookUp;
import com.project.smart_wallet.domain.Asset;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AssetPriceLookupMapper {

    public static AssetPriceLookUp toResponse(Asset entity) {
        return new AssetPriceLookUp(entity.getName(), entity.getSymbol());
    }
}
