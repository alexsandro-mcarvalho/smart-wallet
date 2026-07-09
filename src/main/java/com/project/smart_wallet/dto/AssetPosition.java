package com.project.smart_wallet.dto;

import com.project.smart_wallet.domain.AssetType;

import java.math.BigDecimal;

public record AssetPosition(

        String assetName,
        String assetSymbol,
        AssetType assetType,
        BigDecimal quantity,
        BigDecimal averagePrice
) {
}
