package com.project.smart_wallet.repository;

import com.project.smart_wallet.domain.Asset;
import com.project.smart_wallet.domain.User;
import com.project.smart_wallet.domain.Holding;
import com.project.smart_wallet.dto.AssetPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HoldingRepository extends JpaRepository<Holding, Long> {

    Optional<Holding> findByUserAndAsset(User user, Asset asset);

    @Query("""
            SELECT
                h.asset.name AS assetName,
                h.asset.symbol AS assetSymbol,
                h.asset.assetType AS assetType,
                h.quantity,
                h.averagePrice
            FROM Holding h
            WHERE user.id = :userId
            AND h.quantity > 0
            """)
    List<AssetPosition> getHoldingsByUserId(@Param("userId") long userId);
}
