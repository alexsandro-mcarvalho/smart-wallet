package com.project.smart_wallet.service;

import com.project.smart_wallet.client.BrapiClient;
import com.project.smart_wallet.client.CoingeckoClient;
import com.project.smart_wallet.domain.AssetType;
import com.project.smart_wallet.domain.User;
import com.project.smart_wallet.dto.projection.AssetBalanceProjection;
import com.project.smart_wallet.dto.response.BalanceResponse;
import com.project.smart_wallet.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static com.project.smart_wallet.domain.AssetType.CRYPTO_CURRENCY;
import static com.project.smart_wallet.domain.AssetType.STOCK;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final TransactionRepository transactionRepository;

    private final UserService userService;

    private final CoingeckoClient coingeckoClient;

    private final BrapiClient brapiClient;

    public BalanceResponse getBalance() {
        User user = userService.getAuthenticatedUser();

        List<AssetBalanceProjection> assetsBalance = transactionRepository.getBalance(user.getId());

        List<String> cryptoAssetsName = filterByAssetType(
                assetsBalance,
                CRYPTO_CURRENCY,
                AssetBalanceProjection::assetName
        );

        List<String> stocksAssetsSymbol = filterByAssetType(
                assetsBalance,
                STOCK,
                AssetBalanceProjection::assetSymbol
        );

        CompletableFuture<Map<String, BigDecimal>> cryptoAssetsPriceFuture = !cryptoAssetsName.isEmpty()
                ? coingeckoClient.getPricePerAsset(cryptoAssetsName)
                : CompletableFuture.completedFuture(Collections.emptyMap());

        CompletableFuture<Map<String, BigDecimal>> stocksAssetsPriceFuture = !stocksAssetsSymbol.isEmpty()
                ? brapiClient.getPricePerAsset(stocksAssetsSymbol)
                : CompletableFuture.completedFuture(Collections.emptyMap());

        CompletableFuture.allOf(cryptoAssetsPriceFuture, stocksAssetsPriceFuture).join();

        Map<String, BigDecimal> cryptoAssetsPrice = cryptoAssetsPriceFuture.join();
        Map<String, BigDecimal> stocksAssetsPrice = stocksAssetsPriceFuture.join();

        BigDecimal totalBalance = BigDecimal.ZERO;

        for (AssetBalanceProjection assetBalance : assetsBalance) {
            BigDecimal currentAssetPrice = null;

             switch (assetBalance.assetType()) {
                case CRYPTO_CURRENCY -> currentAssetPrice = cryptoAssetsPrice.get(assetBalance.assetName());
                case STOCK -> currentAssetPrice = stocksAssetsPrice.get(assetBalance.assetSymbol());
            }

            if (currentAssetPrice == null) {
                currentAssetPrice = assetBalance.averagePrice();
            }

            totalBalance = totalBalance.add(currentAssetPrice.multiply(assetBalance.quantity()));
        }

        BigDecimal totalSpending = transactionRepository.getTotalSpending(user.getId())
                .setScale(2, RoundingMode.HALF_EVEN);
        totalBalance = totalBalance.setScale(2, RoundingMode.HALF_EVEN);


        return new BalanceResponse(
                totalBalance,
                totalSpending,
                totalBalance.subtract(totalSpending)
        );
    }

    private List<String> filterByAssetType(
            List<AssetBalanceProjection> assets,
            AssetType type,
            Function<AssetBalanceProjection,String> mapMethod
    ) {
        return assets.stream()
                .filter(asset -> asset.assetType() == type)
                .map(mapMethod)
                .toList();
    }

}
