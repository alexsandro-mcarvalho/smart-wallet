package com.project.smart_wallet.domain;

import com.project.smart_wallet.exception.BusinessException;
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

    public WalletHolding(Transaction transaction) {
        if (transaction.getType() == TransactionType.SELL) {
            throw new BusinessException("Saldo de ativo insuficiente");
        }

        this.user = transaction.getUser();
        this.asset = transaction.getAsset();
        this.quantity = transaction.getQuantity();
    }

    public WalletHolding applyTransaction(Transaction transaction) {
        if (transaction.getType() == TransactionType.SELL && transaction.getQuantity().compareTo(quantity) > 0) {
            throw new BusinessException("Saldo de ativo insuficiente");
        }

        updatedAt = Instant.now();
        quantity = transaction.getType() == TransactionType.SELL ? quantity.subtract(transaction.getQuantity())
                : quantity.add(transaction.getQuantity());

        return this;
    }
}
