package com.project.smart_wallet.client.mapper;

import com.project.smart_wallet.client.dto.AssetPriceLookUp;
import com.project.smart_wallet.domain.Asset;

public class AssetPriceLookupMapper {

    public static AssetPriceLookUp toResponse(Asset entity) {
        return new AssetPriceLookUp(entity.getName(), entity.getSymbol());
    }
}
