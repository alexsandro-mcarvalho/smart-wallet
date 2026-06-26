package com.project.smart_wallet.client;

import com.project.smart_wallet.client.dto.brapi.BrapiStockResponse;
import com.project.smart_wallet.client.mapper.BrapiStockMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class BrapiClient implements AssetPriceProvider {

    private final WebClient webClient;

    public BrapiClient(@Qualifier("brapiWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public CompletableFuture<Map<String, BigDecimal>> getPricePerAsset(List<String> assets) {
        String symbols = String.join(",", assets);

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
