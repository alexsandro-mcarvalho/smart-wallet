package com.project.smart_wallet.client.mapper;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

public class CoingeckoMapper {

    public static Map<String, BigDecimal> toAssetResponse(Map<String, Map<String, BigDecimal>> response, String currency) {
        return response.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue().get(currency)
                ));
    }
}
