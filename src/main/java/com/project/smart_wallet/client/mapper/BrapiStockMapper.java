package com.project.smart_wallet.client.mapper;

import com.project.smart_wallet.client.dto.brapi.BrapiStockResponse;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

public class BrapiStockMapper {

    public static Map<String, BigDecimal> toAssetResponse(BrapiStockResponse response) {
        return response.results()
                .stream()
                .collect(Collectors.toMap(
                        result -> result.symbol(),
                        result -> result.data().regularMarketPrice()
                ));
    }
}
