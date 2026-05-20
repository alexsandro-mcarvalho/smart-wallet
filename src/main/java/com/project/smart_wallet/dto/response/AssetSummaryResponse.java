package com.project.smart_wallet.dto.response;

import com.project.smart_wallet.domain.AssetType;

public record AssetSummaryResponse(
        Long id,
        String name,
        String symbol,
        AssetType assetType,
        String logoUrl
) {
}
