package com.project.smart_wallet.dto.request;

import com.project.smart_wallet.domain.AssetType;
import com.project.smart_wallet.domain.InterestRatePeriod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateAssetRequest(
        @NotBlank String name,
        @NotNull AssetType assetType,
        @NotBlank String symbol,
        @NotBlank String logoUrl,
        @Positive BigDecimal interestRate,
        InterestRatePeriod interestRatePeriod
) { }