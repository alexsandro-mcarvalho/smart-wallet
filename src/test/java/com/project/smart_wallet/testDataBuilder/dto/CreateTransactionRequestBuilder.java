package com.project.smart_wallet.testDataBuilder.dto;

import com.project.smart_wallet.domain.TransactionType;
import com.project.smart_wallet.dto.request.CreateTransactionRequest;

import java.math.BigDecimal;
import java.time.Instant;

public class CreateTransactionRequestBuilder {

    private final TransactionType type;

    private Long assetId = 1L;
    private BigDecimal quantity = new BigDecimal("50000.00");
    private BigDecimal price = new BigDecimal("75.25");
    private Instant transactionAt = Instant.parse("2026-07-12T22:40:00Z");

    private CreateTransactionRequestBuilder(TransactionType type) {
        this.type = type;
    }

    public static CreateTransactionRequestBuilder aBuyCreateTransactionRequest() {
        return new CreateTransactionRequestBuilder(TransactionType.BUY);
    }

    public static CreateTransactionRequestBuilder aSellCreateTransactionRequest() {
        return new CreateTransactionRequestBuilder(TransactionType.SELL);
    }

    public CreateTransactionRequestBuilder withAssetId(Long assetId) {
        this.assetId = assetId;
        return this;
    }

    public CreateTransactionRequestBuilder withQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public CreateTransactionRequestBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public CreateTransactionRequestBuilder withTransactionAt(Instant transactionAt) {
        this.transactionAt = transactionAt;
        return this;
    }

    public CreateTransactionRequest build() {
        return new CreateTransactionRequest(assetId, quantity, price, transactionAt, type);
    }
}

