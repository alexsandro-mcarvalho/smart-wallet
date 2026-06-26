package com.project.smart_wallet.dto.projection;

import com.project.smart_wallet.domain.AssetType;

import java.math.BigDecimal;

public record AssetBalanceProjection(

        String assetName,
        String assetSymbol,
        AssetType assetType,
        BigDecimal quantity,
        BigDecimal averagePrice
) {
}
