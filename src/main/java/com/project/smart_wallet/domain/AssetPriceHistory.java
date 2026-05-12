package com.project.smart_wallet.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "asset_price_histories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AssetPriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private BigDecimal price;

    @Setter
    private Instant collectedAt;

    @CreationTimestamp
    private Instant createdAt;

    @Setter
    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;
}
