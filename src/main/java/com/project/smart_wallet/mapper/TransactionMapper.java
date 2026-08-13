package com.project.smart_wallet.mapper;

import com.project.smart_wallet.domain.Transaction;
import com.project.smart_wallet.dto.response.TransactionListResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TransactionMapper {

    public static TransactionListResponse toResponse(Transaction entity) {
        return new TransactionListResponse(
                entity.getId(),
                entity.getAsset().getName(),
                entity.getAsset().getAssetType(),
                entity.getType(),
                entity.getQuantity(),
                entity.getPrice(),
                entity.getTransactionAt()
        );
    }
}
