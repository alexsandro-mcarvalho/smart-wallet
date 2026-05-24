package com.project.smart_wallet.repository;

import com.project.smart_wallet.domain.Asset;
import com.project.smart_wallet.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
            SELECT COALESCE(SUM(
                CASE
                    WHEN t.type = 'BUY' THEN t.quantity
                    WHEN t.type = 'SELL' THEN - t.quantity
                    ELSE 0
                END
            ), 0)
            FROM Transaction t
            WHERE t.user.id = :userId
            AND t.asset.id = :assetId
    """)
    BigDecimal getTotalQuantityByUserIdAndAssetId(@Param("userId") long userId, @Param("assetId") long assetId);
}
