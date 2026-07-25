package com.project.smart_wallet.dto.redis;

import java.math.BigDecimal;
import java.time.Instant;

public record AssetPriceCache(BigDecimal price, Instant updatedAt) {
}
