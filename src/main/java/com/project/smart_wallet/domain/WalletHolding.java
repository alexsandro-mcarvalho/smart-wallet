package com.project.smart_wallet.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "wallet_holdings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalletHolding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private BigDecimal quantity;

    @Setter
    @CreationTimestamp
    private Instant updatedAt;

    @CreationTimestamp
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Asset asset;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public WalletHolding(User user, Asset asset, BigDecimal quantity) {
        this.user = user;
        this.asset = asset;
        this.quantity = quantity;
    }

    public WalletHolding applyTransaction(Transaction transaction) {
        updatedAt = Instant.now();
        quantity = transaction.getType() == TransactionType.SELL ? quantity.subtract(transaction.getQuantity())
                : quantity.add(transaction.getQuantity());
        return this;
    }
}
