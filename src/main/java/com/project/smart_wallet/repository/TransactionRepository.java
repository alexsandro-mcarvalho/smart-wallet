package com.project.smart_wallet.repository;

import com.project.smart_wallet.domain.Transaction;
import com.project.smart_wallet.dto.projection.AssetBalanceProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

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

    @Query("""
            SELECT
                t.asset.name AS assetName,
                t.asset.assetType AS assetType,
                COALESCE(SUM(
                    CASE
                        WHEN t.type = 'BUY' THEN t.quantity
                        WHEN t.type = 'SELL' THEN - t.quantity
                        ELSE 0
                    END
                ), 0) AS quantity,
                (
                    SELECT
                        COALESCE(SUM(t2.price), 0) / COALESCE(SUM(t2.quantity), 0)
                    FROM Transaction t2
                    WHERE t2.user.id = :userId
                    AND t2.type = 'BUY'
                    AND t2.asset.name = t.asset.name
                ) AS averagePrice
            FROM Transaction t
            WHERE t.user.id = :userId
            GROUP BY t.asset.name, t.asset.assetType
    """)
    List<AssetBalanceProjection> getBalance(@Param("userId") long userId);
}
