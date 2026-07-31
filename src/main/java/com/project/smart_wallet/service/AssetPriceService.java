package com.project.smart_wallet.service;

import com.project.smart_wallet.client.AssetPriceProvider;
import com.project.smart_wallet.client.BrapiStockClient;
import com.project.smart_wallet.client.CoingeckoCryptoClient;
import com.project.smart_wallet.client.dto.PriceLookupAsset;
import com.project.smart_wallet.domain.Asset;
import com.project.smart_wallet.domain.AssetType;
import com.project.smart_wallet.dto.AssetPosition;
import com.project.smart_wallet.dto.redis.AssetPriceCache;
import com.project.smart_wallet.repository.AssetRepository;
import com.project.smart_wallet.utils.BatchUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AssetPriceService {

    private final List<AssetPriceProvider> assetPriceProviders;

    private final AssetRepository assetRepository;

    private final RedisTemplate<String, AssetPriceCache> redisTemplate;

    public void refreshPrices(AssetType assetType) {
        List<Asset> assets = assetRepository.findAllByAssetType(assetType);
    }

    private void getAssetsPrice(List<PriceLookupAsset> assets, AssetType assetType) {
        AssetPriceProvider provider = getProvider(assetType);
        List<List<PriceLookupAsset>> assetsBatches = BatchUtils.partition(assets, provider.getQuantityPerRequest());

        List<CompletableFuture<Map<String, BigDecimal>>> assetFutures =
                assetsBatches.stream()
                        .map(provider::getPricePerAsset)
                        .toList();

        CompletableFuture.allOf(assetFutures.toArray(CompletableFuture[]::new));
    }

    private AssetPriceProvider getProvider(AssetType assetType) {
        return assetPriceProviders.stream()
                .filter(provider -> provider.getAssetType() == assetType)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhuma provider encontrado"));
    }

}
