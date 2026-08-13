package com.project.smart_wallet.client.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CoingeckoMapper {

    public static Map<String, BigDecimal> toAssetResponse(Map<String, Map<String, BigDecimal>> response, String currency) {
        return response.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue().get(currency)
                ));
    }
}
