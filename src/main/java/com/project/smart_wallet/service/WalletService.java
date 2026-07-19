package com.project.smart_wallet.service;

import com.project.smart_wallet.client.BrapiClient;
import com.project.smart_wallet.client.CoingeckoClient;
import com.project.smart_wallet.domain.AssetType;
import com.project.smart_wallet.domain.User;
import com.project.smart_wallet.dto.AssetPosition;
import com.project.smart_wallet.client.dto.PriceLookupAsset;
import com.project.smart_wallet.dto.response.BalanceResponse;
import com.project.smart_wallet.repository.TransactionRepository;
import com.project.smart_wallet.repository.HoldingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.project.smart_wallet.domain.AssetType.CRYPTO_CURRENCY;
import static com.project.smart_wallet.domain.AssetType.STOCK;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final TransactionRepository transactionRepository;

    private final HoldingRepository holdingRepository;

    private final UserService userService;

    private final CoingeckoClient coingeckoClient;

    private final BrapiClient brapiClient;

    public BalanceResponse getBalance() {
        User user = userService.getAuthenticatedUser();

        List<AssetPosition> assetsBalance = holdingRepository.getHoldingsByUserId(user.getId());

        List<PriceLookupAsset> cryptoAssetsName = filterByAssetType(assetsBalance, CRYPTO_CURRENCY);

        List<PriceLookupAsset> stocksAssetsSymbol = filterByAssetType(assetsBalance, STOCK);

        CompletableFuture<Map<String, BigDecimal>> cryptoAssetsPriceFuture = cryptoAssetsName.isEmpty()
                ? CompletableFuture.completedFuture(Collections.emptyMap())
                : coingeckoClient.getPricePerAsset(cryptoAssetsName);

        CompletableFuture<Map<String, BigDecimal>> stocksAssetsPriceFuture = stocksAssetsSymbol.isEmpty()
                ? CompletableFuture.completedFuture(Collections.emptyMap())
                : brapiClient.getPricePerAsset(stocksAssetsSymbol);

        CompletableFuture.allOf(cryptoAssetsPriceFuture, stocksAssetsPriceFuture).join();

        Map<String, BigDecimal> cryptoAssetsPrice = cryptoAssetsPriceFuture.join();
        Map<String, BigDecimal> stocksAssetsPrice = stocksAssetsPriceFuture.join();

        BigDecimal totalBalance = BigDecimal.ZERO;

        for (AssetPosition assetBalance : assetsBalance) {
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

    private List<PriceLookupAsset> filterByAssetType(List<AssetPosition> assets, AssetType type) {
        return assets.stream()
                .filter(asset -> asset.assetType() == type)
                .map(asset -> new PriceLookupAsset(asset.assetName(), asset.assetSymbol()))
                .toList();
    }

}
