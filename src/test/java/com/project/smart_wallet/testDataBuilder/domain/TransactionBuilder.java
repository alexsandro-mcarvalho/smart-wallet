package com.project.smart_wallet.testDataBuilder.domain;

import com.project.smart_wallet.domain.Asset;
import com.project.smart_wallet.domain.Transaction;
import com.project.smart_wallet.domain.TransactionType;
import com.project.smart_wallet.domain.User;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionBuilder {

    private final TransactionType type;

    private BigDecimal quantity = new BigDecimal("10.00");
    private BigDecimal price = new BigDecimal("50000.00");
    private Instant transactionAt = Instant.parse("2026-07-12T22:40:00Z");

    private User user = UserBuilder.aUser().build();
    private Asset asset = AssetBuilder.anAsset().build();

    private TransactionBuilder(TransactionType type) {
        this.type = type;
    }

    public static TransactionBuilder aBuy() {
        return new TransactionBuilder(TransactionType.BUY);
    }

    public static TransactionBuilder aSell() {
        return new TransactionBuilder(TransactionType.SELL);
    }

    public TransactionBuilder withQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public TransactionBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public TransactionBuilder withTransactionAt(Instant transactionAt) {
        this.transactionAt = transactionAt;
        return this;
    }

    public TransactionBuilder withUser(User user) {
        this.user = user;
        return this;
    }

    public TransactionBuilder withAsset(Asset asset) {
        this.asset = asset;
        return this;
    }

    public Transaction build() {
        return new Transaction(quantity, price, transactionAt, type, user, asset);
    }
}
