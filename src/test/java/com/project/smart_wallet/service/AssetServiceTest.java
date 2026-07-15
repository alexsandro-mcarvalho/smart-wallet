package com.project.smart_wallet.service;

import com.project.smart_wallet.domain.Asset;
import com.project.smart_wallet.domain.AssetType;
import com.project.smart_wallet.domain.InterestRatePeriod;
import com.project.smart_wallet.dto.request.CreateAssetRequest;
import com.project.smart_wallet.dto.response.CreateAssetResponse;
import com.project.smart_wallet.exception.ConflictException;
import com.project.smart_wallet.repository.AssetRepository;
import com.project.smart_wallet.testDataBuilder.domain.AssetBuilder;
import com.project.smart_wallet.testDataBuilder.dto.CreateAssetRequestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static com.project.smart_wallet.testDataBuilder.dto.CreateAssetRequestBuilder.aCreateAssetRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
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
    void shouldCreateAssetAnAsset() {

        CreateAssetRequest request = aCreateAssetRequest()
                .withSymbol("btc")
                .build();

        when(assetRepository.existsByNameAndSymbolAllIgnoringCase(anyString(), anyString())).thenReturn(false);

        CreateAssetResponse response = assetService.createAsset(request);

        verify(assetRepository).existsByNameAndSymbolAllIgnoringCase(
                anyString(), anyString()
        );
        verify(assetRepository).save(any(Asset.class));

        assertEquals(request.symbol().toUpperCase(), response.symbol());
    }

    @Test
    @DisplayName("Should throw a ConflictException when the asset already exists")
    void shouldThrowConflictException() {

        CreateAssetRequest request = aCreateAssetRequest().build();

        when(assetRepository.existsByNameAndSymbolAllIgnoringCase(anyString(), anyString())).thenReturn(true);

        assertThrows(ConflictException.class, () -> assetService.createAsset(request));

        verify(assetRepository, never()).save(any(Asset.class));
    }
}