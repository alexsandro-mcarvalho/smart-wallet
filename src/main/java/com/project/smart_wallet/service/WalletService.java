package com.project.smart_wallet.service;

import com.project.smart_wallet.domain.AssetType;
import com.project.smart_wallet.domain.User;
import com.project.smart_wallet.dto.AssetPosition;
import com.project.smart_wallet.client.dto.AssetPriceLookUp;
import com.project.smart_wallet.dto.redis.AssetPriceCache;
import com.project.smart_wallet.dto.response.BalanceResponse;
import com.project.smart_wallet.repository.TransactionRepository;
import com.project.smart_wallet.repository.HoldingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.project.smart_wallet.utils.redis.AssetPriceRedisKey.buildKey;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final TransactionRepository transactionRepository;

    private final HoldingRepository holdingRepository;

    private final UserService userService;

    private final RedisTemplate<String, AssetPriceCache> redisTemplate;

    private static final int MONEY_SCALE = 2;

    private static final RoundingMode MONEY_ROUNDING = RoundingMode.HALF_EVEN;

    public BalanceResponse getBalance() {
        User user = userService.getAuthenticatedUser();

        List<AssetPosition> assetsBalance = holdingRepository.getHoldingsByUserId(user.getId());

        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalSpending = BigDecimal.ZERO;

        for (AssetPosition assetPosition : assetsBalance) {
            String identifier = switch (assetPosition.assetType()) {
                case CRYPTO_CURRENCY -> assetPosition.assetName();
                case STOCK -> assetPosition.assetSymbol();
            };
            String redisKey = buildKey(identifier, assetPosition.assetType());

            AssetPriceCache assetPriceCache = redisTemplate.opsForValue().get(redisKey);

            BigDecimal currentSpending = assetPosition.quantity().multiply(assetPosition.averagePrice());
            totalSpending = totalSpending.add(currentSpending);

            if (assetPriceCache != null) {
                BigDecimal currentAmount = assetPosition.quantity().multiply(assetPriceCache.price());
                totalAmount = totalAmount.add(currentAmount);
            } else {
                // provisioriamente adiciona com fallback o valor investido no ativo
                totalAmount = totalAmount.add(currentSpending);
            }
        }

        return new BalanceResponse(
                totalAmount.setScale(MONEY_SCALE, MONEY_ROUNDING),
                totalSpending.setScale(MONEY_SCALE, MONEY_ROUNDING),
                totalAmount.subtract(totalSpending).setScale(MONEY_SCALE, MONEY_ROUNDING)
        );
    }
//
//        List<AssetPriceLookUp> cryptoAssetsName = filterByAssetType(assetsBalance, CRYPTO_CURRENCY);
//
//        List<AssetPriceLookUp> stocksAssetsSymbol = filterByAssetType(assetsBalance, STOCK);
//
//        CompletableFuture<Map<String, BigDecimal>> cryptoAssetsPriceFuture = cryptoAssetsName.isEmpty()
//                ? CompletableFuture.completedFuture(Collections.emptyMap())
//                : coingeckoCryptoClient.getPricePerAsset(cryptoAssetsName);
//
//        CompletableFuture<Map<String, BigDecimal>> stocksAssetsPriceFuture = stocksAssetsSymbol.isEmpty()
//                ? CompletableFuture.completedFuture(Collections.emptyMap())
//                : brapiStockClient.getPricePerAsset(stocksAssetsSymbol);
//
//        CompletableFuture.allOf(cryptoAssetsPriceFuture, stocksAssetsPriceFuture).join();
//
//        Map<String, BigDecimal> cryptoAssetsPrice = cryptoAssetsPriceFuture.join();
//        Map<String, BigDecimal> stocksAssetsPrice = stocksAssetsPriceFuture.join();
//
//        BigDecimal totalBalance = BigDecimal.ZERO;
//
//        for (AssetPosition assetBalance : assetsBalance) {
//            BigDecimal currentAssetPrice = null;
//
//             switch (assetBalance.assetType()) {
//                case CRYPTO_CURRENCY -> currentAssetPrice = cryptoAssetsPrice.get(assetBalance.assetName());
//                case STOCK -> currentAssetPrice = stocksAssetsPrice.get(assetBalance.assetSymbol());
//            }
//
//            if (currentAssetPrice == null) {
//                currentAssetPrice = assetBalance.averagePrice();
//            }
//
//            totalBalance = totalBalance.add(currentAssetPrice.multiply(assetBalance.quantity()));
//        }
//
//        BigDecimal totalSpending = transactionRepository.getTotalSpending(user.getId())
//                .setScale(2, RoundingMode.HALF_EVEN);
//        totalBalance = totalBalance.setScale(2, RoundingMode.HALF_EVEN);
//
//
//        return new BalanceResponse(
//                totalBalance,
//                totalSpending,
//                totalBalance.subtract(totalSpending)
//        );


    private List<AssetPriceLookUp> filterByAssetType(List<AssetPosition> assets, AssetType type) {
        return assets.stream()
                .filter(asset -> asset.assetType() == type)
                .map(asset -> new AssetPriceLookUp(asset.assetName(), asset.assetSymbol()))
                .toList();
    }

}
