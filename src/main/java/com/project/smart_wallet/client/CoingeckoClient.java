package com.project.smart_wallet.client;

import com.project.smart_wallet.client.dto.AssetPriceResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class CoingeckoClient {

    private static final String API_URL = "https://api.coingecko.com/api/v3";

    private static final String CURRENCY = "brl";

    private final WebClient webClient;

    public CoingeckoClient() {
       this.webClient = WebClient.builder().baseUrl(API_URL).build();
    }

    public Map<String, AssetPriceResponse> getPricePerAsset(String assets) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/simple/price")
                        .queryParam("ids", assets)
                        .queryParam("vs_currencies", CURRENCY)
                        .build())
                .retrieve()
                .bodyToMono(
                        new ParameterizedTypeReference<Map<String, AssetPriceResponse>>() {
                        }
                ).block();


    }
}
