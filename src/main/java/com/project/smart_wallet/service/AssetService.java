package com.project.smart_wallet.service;

import com.project.smart_wallet.domain.Asset;
import com.project.smart_wallet.domain.AssetType;
import com.project.smart_wallet.dto.request.AssetTypeRequest;
import com.project.smart_wallet.dto.request.CreateAssetRequest;
import com.project.smart_wallet.dto.response.AssetTypeResponse;
import com.project.smart_wallet.dto.response.CreateAssetResponse;
import com.project.smart_wallet.mapper.AssetTypeMapper;
import com.project.smart_wallet.mapper.CreateAssetMapper;
import com.project.smart_wallet.repository.AssetRepository;
import com.project.smart_wallet.repository.AssetTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.project.smart_wallet.mapper.AssetTypeMapper.toEntity;
import static com.project.smart_wallet.mapper.AssetTypeMapper.toResponse;
import static com.project.smart_wallet.mapper.CreateAssetMapper.toEntity;
import static com.project.smart_wallet.mapper.CreateAssetMapper.toResponse;


@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetTypeRepository assetTypeRepository;

    private final AssetRepository assetRepository;

    public CreateAssetResponse create(CreateAssetRequest request) {
        AssetType assetType = assetTypeRepository.findById(request.assetTypeId())
                .orElseThrow(() -> new RuntimeException("AssetType não existe"));

        if (assetRepository.findBySymbol(request.symbol()).isPresent()) {
            throw new RuntimeException("Asset já cadastrado");
        }

        Asset asset = toEntity(request, assetType);

        assetRepository.save(asset);

        return toResponse(asset);
    }

    public AssetTypeResponse createAssetType(AssetTypeRequest request) {
        if (assetTypeRepository.findByName(request.name()).isPresent()) {
            throw new RuntimeException("AssetType já cadastrado");
        }

        AssetType assetType = toEntity(request);

        assetTypeRepository.save(assetType);

        return toResponse(assetType);
    }
}
