package com.project.smart_wallet.dto.response;

import com.project.smart_wallet.domain.AssetType;
import com.project.smart_wallet.domain.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionListResponse(
        Long id,
        String asset,
        AssetType assetType,
        TransactionType type,
        BigDecimal quantity,
        BigDecimal price,
        Instant transactionAt
) {
}
