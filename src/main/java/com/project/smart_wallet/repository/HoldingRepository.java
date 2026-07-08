package com.project.smart_wallet.repository;

import com.project.smart_wallet.domain.Asset;
import com.project.smart_wallet.domain.User;
import com.project.smart_wallet.domain.Holding;
import com.project.smart_wallet.dto.projection.AssetBalanceProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HoldingRepository extends JpaRepository<Holding, Long> {

    Optional<Holding> findByUserAndAsset(User user, Asset asset);

    @Query("""
            SELECT
                w.asset.name AS assetName,
                w.asset.symbol AS assetSymbol,
                w.asset.assetType AS assetType,
                quantity
            FROM WalletHolding w
            WHERE user.id = :userId
            """)
    List<AssetBalanceProjection> getHoldingsByUserId(@Param("userId") long userId);
}
