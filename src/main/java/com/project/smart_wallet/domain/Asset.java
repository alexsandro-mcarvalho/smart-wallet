package com.project.smart_wallet.domain;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "assets")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String name;

    @Enumerated(EnumType.STRING)
    private AssetType assetType;

    @Setter
    private String symbol;

    @Setter
    private String logoUrl;

    @Setter
    private BigDecimal interestRate;

    @Setter
    @Enumerated(EnumType.STRING)
    private InterestRatePeriod interestRatePeriod;

    @CreationTimestamp
    private Instant createdAt;

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssetPriceHistory> assetPriceHistories;


    public Asset(
            String name,
            AssetType assetType,
            String symbol,
            String logoUrl,
            BigDecimal interestRate,
            InterestRatePeriod interestRatePeriod
    ) {
        this.name = name;
        this.assetType = assetType;
        this.symbol = symbol;
        this.logoUrl = logoUrl;
        this.interestRate = interestRate;
        this.interestRatePeriod = interestRatePeriod;
    }
}
