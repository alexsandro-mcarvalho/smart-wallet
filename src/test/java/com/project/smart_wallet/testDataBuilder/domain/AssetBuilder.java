package com.project.smart_wallet.testDataBuilder.domain;

import com.project.smart_wallet.domain.Asset;
import com.project.smart_wallet.domain.AssetType;
import com.project.smart_wallet.domain.InterestRatePeriod;

import java.math.BigDecimal;

public class AssetBuilder {

    private String name = "bitcoin";
    private AssetType assetType = AssetType.CRYPTO_CURRENCY;
    private String symbol = "BTC";
    private String logoUrl = "https://example.com/bitcoin.png";
    private BigDecimal interestRate = null;
    private InterestRatePeriod interestRatePeriod = null;

    private AssetBuilder() {}

    public static AssetBuilder anAsset() {
        return new AssetBuilder();
    }

    public AssetBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public AssetBuilder withAssetType(AssetType assetType) {
        this.assetType = assetType;
        return this;
    }

    public AssetBuilder withSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public AssetBuilder withLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
        return this;
    }

    public AssetBuilder withInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public AssetBuilder withInterestRatePeriod(InterestRatePeriod interestRatePeriod) {
        this.interestRatePeriod = interestRatePeriod;
        return this;
    }

    public Asset build() {
        return new Asset(name, assetType, symbol, logoUrl, interestRate, interestRatePeriod);
    }
}

