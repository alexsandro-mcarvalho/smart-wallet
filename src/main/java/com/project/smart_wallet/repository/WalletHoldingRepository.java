package com.project.smart_wallet.repository;

import com.project.smart_wallet.domain.Asset;
import com.project.smart_wallet.domain.User;
import com.project.smart_wallet.domain.WalletHolding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletHoldingRepository extends JpaRepository<WalletHolding, Long> {

    Optional<WalletHolding> findByUserAndAsset(User user, Asset asset);
}
