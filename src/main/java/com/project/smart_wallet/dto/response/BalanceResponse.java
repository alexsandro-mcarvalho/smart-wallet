package com.project.smart_wallet.dto.response;

import java.math.BigDecimal;

public record BalanceResponse(
        BigDecimal totalBalance,
        BigDecimal totalSpending,
        BigDecimal profit
) {
}
