package com.project.smart_wallet.mapper;

import com.project.smart_wallet.domain.Asset;
import com.project.smart_wallet.dto.response.AssetSummaryResponse;

public class AssetSummaryMapper {

    public static AssetSummaryResponse toResponse(Asset entity) {
        return new AssetSummaryResponse(
                entity.getId(),
                entity.getName(),
                entity.getSymbol(),
                entity.getAssetType(),
                entity.getLogoUrl()
        );
    }
}
