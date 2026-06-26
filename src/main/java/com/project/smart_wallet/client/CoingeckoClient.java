package com.project.smart_wallet.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.project.smart_wallet.client.mapper.CoingeckoMapper.toAssetResponse;

@Component
public class CoingeckoClient implements AssetPriceProvider {

    private static final String CURRENCY = "brl";

    private final WebClient webClient;

    public CoingeckoClient(@Qualifier("coingeckoWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public CompletableFuture<Map<String, BigDecimal>> getPricePerAsset(List<String> assets) {
        String ids = String.join(",", assets);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/simple/price")
                        .queryParam("ids", ids)
                        .queryParam("vs_currencies", CURRENCY)
                        .build())
                .retrieve()
                .bodyToMono(
                        new ParameterizedTypeReference<Map<String, Map<String, BigDecimal>>>() {
                        })
                .map(response -> toAssetResponse(response, CURRENCY))
                .toFuture();
    }
}
