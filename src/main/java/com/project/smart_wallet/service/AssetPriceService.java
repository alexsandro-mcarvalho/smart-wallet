package com.project.smart_wallet.service;

import com.project.smart_wallet.client.AssetPriceProvider;
import com.project.smart_wallet.client.dto.AssetPriceLookUp;
import com.project.smart_wallet.domain.Asset;
import com.project.smart_wallet.domain.AssetType;
import com.project.smart_wallet.dto.redis.AssetPriceCache;
import com.project.smart_wallet.client.mapper.AssetPriceLookupMapper;
import com.project.smart_wallet.repository.AssetRepository;
import com.project.smart_wallet.utils.BatchUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class AssetPriceService {

    private final List<AssetPriceProvider> assetPriceProviders;

    private final AssetRepository assetRepository;

    private final RedisTemplate<String, AssetPriceCache> redisTemplate;

    public void refreshPrices(AssetType assetType) {
        List<Asset> assets = assetRepository.findAllByAssetType(assetType);
        Map<String, BigDecimal> assetsPrices = getAssetsPrice(assets, assetType);
    }

    @SneakyThrows
    private Map<String, BigDecimal> getAssetsPrice(List<Asset> assets, AssetType assetType){
        AssetPriceProvider provider = getProvider(assetType);

        List<AssetPriceLookUp> assetPriceLookUps = assets.stream()
                .map(AssetPriceLookupMapper::toResponse)
                .toList();

        List<List<AssetPriceLookUp>> assetsBatches = BatchUtils.partition(
                assetPriceLookUps,
                provider.getQuantityPerRequest()
        );

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

            List<Future<Map<String, BigDecimal>>> futures = new ArrayList<>();

            for (var batches : assetsBatches) {
                futures.add(executor.submit(() -> provider.getPricePerAsset(batches)));
            }

            Map<String, BigDecimal> prices = new HashMap<>();

            for (var future : futures) {
                prices.putAll(future.get());
            }

            return prices;
        }

    }

    private AssetPriceProvider getProvider(AssetType assetType) {
        return assetPriceProviders.stream()
                .filter(provider -> provider.getAssetType() == assetType)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhuma provider encontrado"));
    }

}
