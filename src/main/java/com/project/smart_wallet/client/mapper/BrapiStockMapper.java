package com.project.smart_wallet.client.mapper;

import com.project.smart_wallet.client.dto.brapi.BrapiStockResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BrapiStockMapper {

    public static Map<String, BigDecimal> toAssetResponse(BrapiStockResponse response) {
        return response.results()
                .stream()
                .collect(Collectors.toMap(
                        result -> result.symbol(),
                        result -> result.data().regularMarketPrice()
                ));
    }
}
