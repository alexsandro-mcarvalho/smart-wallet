package com.project.smart_wallet.client;

import com.project.smart_wallet.client.dto.PriceLookupAsset;
import com.project.smart_wallet.domain.AssetType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.project.smart_wallet.client.mapper.CoingeckoMapper.toAssetResponse;

@Component
public class CoingeckoCryptoClient implements AssetPriceProvider {

    private static final String CURRENCY = "brl";

    private final WebClient webClient;

    public CoingeckoCryptoClient(@Qualifier("coingeckoWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public int getQuantityPerRequest() {
        return 10;
    }

    @Override
    public AssetType getAssetType() {
        return AssetType.CRYPTO_CURRENCY;
    }

    @Override
    public CompletableFuture<Map<String, BigDecimal>> getPricePerAsset(List<PriceLookupAsset> assets) {

        String ids = assets.stream()
                .map(PriceLookupAsset::assetName)
                .collect(Collectors.joining(","));

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
