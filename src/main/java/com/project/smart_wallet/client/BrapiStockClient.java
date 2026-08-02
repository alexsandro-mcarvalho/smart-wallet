package com.project.smart_wallet.client;

import com.project.smart_wallet.client.dto.AssetPriceLookUp;
import com.project.smart_wallet.client.dto.brapi.BrapiStockResponse;
import com.project.smart_wallet.client.mapper.BrapiStockMapper;
import com.project.smart_wallet.domain.AssetType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BrapiStockClient implements AssetPriceProvider {

    private final WebClient webClient;

    public BrapiStockClient(@Qualifier("brapiWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public int getQuantityPerRequest() {
        return 1;
    }

    @Override
    public AssetType getAssetType() {
        return AssetType.STOCK;
    }

    @Override
    public Map<String, BigDecimal> getPricePerAsset(List<AssetPriceLookUp> assets) {

        String symbols = assets.stream()
                .map(AssetPriceLookUp::assetSymbol)
                .collect(Collectors.joining(","));

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stocks/quote")
                        .queryParam("symbols", symbols)
                        .build())
                .retrieve()
                .bodyToMono(BrapiStockResponse.class)
                .map(BrapiStockMapper::toAssetResponse)
                .block();
    }

}
