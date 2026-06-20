package com.project.smart_wallet.client;

import com.project.smart_wallet.client.dto.AssetPriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class CoingeckoClient {

    private static final String CURRENCY = "brl";

    private final WebClient webClient;

    public CoingeckoClient(@Qualifier("coingeckoWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public CompletableFuture<Map<String, AssetPriceResponse>> getPricePerAsset(String assets) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/simple/price")
                        .queryParam("ids", assets)
                        .queryParam("vs_currencies", CURRENCY)
                        .build())
                .retrieve()
                .bodyToMono(
                        new ParameterizedTypeReference<Map<String, AssetPriceResponse>>() {
                        })
                .toFuture();
    }
}
