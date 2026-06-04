package com.project.smart_wallet.service;

import com.project.smart_wallet.domain.Asset;
import com.project.smart_wallet.domain.AssetType;
import com.project.smart_wallet.domain.InterestRatePeriod;
import com.project.smart_wallet.dto.request.CreateAssetRequest;
import com.project.smart_wallet.dto.response.CreateAssetResponse;
import com.project.smart_wallet.exception.ConflictException;
import com.project.smart_wallet.repository.AssetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AssetServiceTest {

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private AssetService assetService;

    @Test
    @DisplayName("Should create an asset when the assetRequest is valid")
    void shouldCreateAnAsset() {

        CreateAssetRequest request = new CreateAssetRequest(
                "bitcoin",
                AssetType.CRYPTO_CURRENCY,
                "btc",
                "https://example.com/bitcoin.png",
                new BigDecimal("5.25"),
                InterestRatePeriod.ANNUAL
        );

        when(assetRepository.findBySymbol(anyString())).thenReturn(Optional.empty());

        CreateAssetResponse response = assetService.create(request);

        verify(assetRepository, times(1)).findBySymbol(request.symbol().toUpperCase());
        verify(assetRepository, times(1)).save(any(Asset.class));

        assertEquals(request.symbol().toUpperCase(), response.symbol());
    }

    @Test
    @DisplayName("Should throw a ConflictException when the asset already exists")
    void shouldThrowConflictException() {

        CreateAssetRequest request = new CreateAssetRequest(
                "bitcoin",
                AssetType.CRYPTO_CURRENCY,
                "btc",
                "https://example.com/bitcoin.png",
                new BigDecimal("5.25"),
                InterestRatePeriod.ANNUAL
        );

        Asset asset = new Asset(
                "bitcoin",
                AssetType.CRYPTO_CURRENCY,
                "BTC",
                "https://example.com/bitcoin.png",
                new BigDecimal("5.25"),
                InterestRatePeriod.ANNUAL
        );

        when(assetRepository.findBySymbol(anyString())).thenReturn(Optional.of(asset));

        Assertions.assertThrows(ConflictException.class, () -> assetService.create(request));
    }
}