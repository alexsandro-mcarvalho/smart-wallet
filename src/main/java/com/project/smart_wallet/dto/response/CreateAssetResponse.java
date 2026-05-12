package com.project.smart_wallet.dto.response;

import com.project.smart_wallet.domain.AssetType;
import com.project.smart_wallet.domain.InterestRatePeriod;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateAssetResponse(
        Long id,
        String name,
        AssetType assetType,
        String symbol,
        String logoUrl,
        BigDecimal interestRate,
        InterestRatePeriod interestRatePeriod,
        Instant createdAt
) {}
