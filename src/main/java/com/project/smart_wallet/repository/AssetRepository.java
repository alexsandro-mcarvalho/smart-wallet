package com.project.smart_wallet.repository;

import com.project.smart_wallet.domain.Asset;
import com.project.smart_wallet.domain.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    boolean existsByNameAndSymbolAllIgnoringCase(String name, String symbol);

    List<Asset> findAllByAssetType(AssetType assetType);
}
