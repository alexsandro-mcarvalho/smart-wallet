package com.project.smart_wallet.scheduler;

import com.project.smart_wallet.domain.AssetType;
import com.project.smart_wallet.service.AssetPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class AssetPriceScheduler {

    private final AssetPriceService assetPriceService;

    @Scheduled(fixedRate = 20, timeUnit = TimeUnit.MINUTES)
    public void refreshCryptoPrices() {
        assetPriceService.refreshPrices(AssetType.CRYPTO_CURRENCY);
    }
}
