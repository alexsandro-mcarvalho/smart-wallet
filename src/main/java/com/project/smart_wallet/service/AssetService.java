package com.project.smart_wallet.service;

import com.project.smart_wallet.domain.Asset;
import com.project.smart_wallet.dto.request.CreateAssetRequest;
import com.project.smart_wallet.dto.response.CreateAssetResponse;
import com.project.smart_wallet.exception.ConflictException;
import com.project.smart_wallet.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.project.smart_wallet.mapper.CreateAssetMapper.toEntity;
import static com.project.smart_wallet.mapper.CreateAssetMapper.toResponse;


@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;

    public CreateAssetResponse create(CreateAssetRequest request) {
        if (assetRepository.findBySymbol(request.symbol()).isPresent()) {
            throw new ConflictException("Asset já cadastrado");
        }

        Asset asset = toEntity(request, request.symbol().toUpperCase());

        assetRepository.save(asset);

        return toResponse(asset);
    }

}
