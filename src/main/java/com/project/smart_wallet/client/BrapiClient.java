package com.project.smart_wallet.client;

import com.project.smart_wallet.client.dto.PriceLookupAsset;
import com.project.smart_wallet.client.dto.brapi.BrapiStockResponse;
import com.project.smart_wallet.client.mapper.BrapiStockMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class BrapiClient implements AssetPriceProvider {

    private final WebClient webClient;

    public BrapiClient(@Qualifier("brapiWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public CompletableFuture<Map<String, BigDecimal>> getPricePerAsset(List<PriceLookupAsset> assets) {

        String symbols = assets.stream()
                .map(PriceLookupAsset::assetSymbol)
                .collect(Collectors.joining(","));

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stocks/quote")
                        .queryParam("symbols", symbols)
                        .build())
                .retrieve()
                .bodyToMono(BrapiStockResponse.class)
                .map(BrapiStockMapper::toAssetResponse)
                .toFuture();
    }

}
