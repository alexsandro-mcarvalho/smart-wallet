package com.project.smart_wallet.testDataBuilder.dto;

import com.project.smart_wallet.domain.AssetType;
import com.project.smart_wallet.domain.InterestRatePeriod;
import com.project.smart_wallet.dto.request.CreateAssetRequest;

import java.math.BigDecimal;

import static com.project.smart_wallet.domain.AssetType.CRYPTO_CURRENCY;

public class CreateAssetRequestBuilder {

    private String name = "bitcoin";
    private AssetType assetType = CRYPTO_CURRENCY;
    private String symbol = "BTC";
    private String logoUrl = "https://example.com/btc.png";
    private BigDecimal interestRate = null;
    private InterestRatePeriod interestRatePeriod = null;

    private CreateAssetRequestBuilder () {}

    public static CreateAssetRequestBuilder aCreateAssetRequest() {
        return new CreateAssetRequestBuilder();
    }

    public CreateAssetRequestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CreateAssetRequestBuilder withAssetType(AssetType assetType) {
        this.assetType = assetType;
        return this;
    }

    public CreateAssetRequestBuilder withSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public CreateAssetRequestBuilder withLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
        return this;
    }

    public CreateAssetRequestBuilder withInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public CreateAssetRequestBuilder withInterestRatePeriod(InterestRatePeriod interestRatePeriod) {
        this.interestRatePeriod = interestRatePeriod;
        return this;
    }

    public CreateAssetRequest build() {
        return new CreateAssetRequest(
                name,
                assetType,
                symbol,
                logoUrl,
                interestRate,
                interestRatePeriod
        );
    }
}
