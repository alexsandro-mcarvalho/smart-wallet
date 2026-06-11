package com.project.smart_wallet.service;

import com.project.smart_wallet.client.CoingeckoClient;
import com.project.smart_wallet.client.dto.AssetPriceResponse;
import com.project.smart_wallet.domain.AssetType;
import com.project.smart_wallet.domain.User;
import com.project.smart_wallet.dto.projection.AssetBalanceProjection;
import com.project.smart_wallet.dto.response.BalanceResponse;
import com.project.smart_wallet.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final TransactionRepository transactionRepository;

    private final UserService userService;

    private final CoingeckoClient coingeckoClient;

    public BalanceResponse getBalance() {
        User user = userService.getAuthenticatedUser();

        List<AssetBalanceProjection> assetsBalance = transactionRepository.getBalance(user.getId());

        String cryptoAssets = assetsBalance.stream()
                .filter(asset -> asset.assetType() == AssetType.CRYPTO_CURRENCY)
                .map(AssetBalanceProjection::assetName)
                .collect(Collectors.joining(","));

        Map<String, AssetPriceResponse> cryptoAssetsPrice = coingeckoClient.getPricePerAsset(cryptoAssets);

        BigDecimal totalBalance = BigDecimal.ZERO;

        for (AssetBalanceProjection assetBalance : assetsBalance) {
            AssetPriceResponse currentAsset = null;
            BigDecimal currentAssetPrice = assetBalance.averagePrice();

            if (assetBalance.assetType() == AssetType.CRYPTO_CURRENCY) {
                currentAsset = cryptoAssetsPrice.get(assetBalance.assetName());
            }

            if (currentAsset != null) {
                currentAssetPrice = currentAsset.price();
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
}
