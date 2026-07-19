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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

@Entity
@Table(name = "holdings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Holding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private BigDecimal quantity;

    private BigDecimal averagePrice;

    @UpdateTimestamp
    private Instant updatedAt;

    @CreationTimestamp
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Asset asset;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Holding(Transaction transaction) {
        if (transaction.getType() == TransactionType.SELL) {
            throw new BusinessException("Saldo de ativo insuficiente");
        }

        this.user = transaction.getUser();
        this.asset = transaction.getAsset();
        this.quantity = transaction.getQuantity();
        this.averagePrice = transaction.getPrice().divide(transaction.getQuantity(), 8, RoundingMode.HALF_EVEN);
    }

    public Holding applyTransaction(Transaction transaction) {
        if (transaction.getType() == TransactionType.SELL && transaction.getQuantity().compareTo(quantity) > 0) {
            throw new BusinessException("Saldo de ativo insuficiente");
        }

        updateAveragePrice(transaction);
        quantity = transaction.getType() == TransactionType.SELL ? quantity.subtract(transaction.getQuantity())
                : quantity.add(transaction.getQuantity());

        return this;
    }

    private void updateAveragePrice(Transaction transaction) {
        if (transaction.getType() == TransactionType.BUY) {
            BigDecimal currentAmount = quantity.multiply(averagePrice);
            BigDecimal quantityUpdated = quantity.add(transaction.getQuantity());

            averagePrice = currentAmount.add(transaction.getPrice()).divide(quantityUpdated, 8, RoundingMode.HALF_EVEN);
        }

        if (transaction.getType() == TransactionType.SELL && transaction.getQuantity().compareTo(quantity) == 0) {
            averagePrice = BigDecimal.ZERO;
        }
    }
}
