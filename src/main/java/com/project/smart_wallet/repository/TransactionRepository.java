package com.project.smart_wallet.repository;

import com.project.smart_wallet.domain.Asset;
import com.project.smart_wallet.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
            SELECT SUM(t.quantity)
            FROM Transaction t
            WHERE t.type = 'SELL'
            AND t.user.id = :userId
            AND t.asset.id = :assetId
    """)
    BigDecimal getTotalQuantityByUserIdAndAssetId(@Param("userId") long UserId, @Param("assetId") long assetId);
}
