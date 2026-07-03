package com.project.smart_wallet.service;

import com.project.smart_wallet.domain.Asset;
import com.project.smart_wallet.dto.request.CreateAssetRequest;
import com.project.smart_wallet.dto.response.AssetSummaryResponse;
import com.project.smart_wallet.dto.response.CreateAssetResponse;
import com.project.smart_wallet.dto.response.PaginatedResponse;
import com.project.smart_wallet.exception.ConflictException;
import com.project.smart_wallet.mapper.AssetSummaryMapper;
import com.project.smart_wallet.mapper.PageMapper;
import com.project.smart_wallet.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.smart_wallet.mapper.CreateAssetMapper.toEntity;
import static com.project.smart_wallet.mapper.CreateAssetMapper.toResponse;
import static com.project.smart_wallet.mapper.PageMapper.toResponse;


@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;

    public CreateAssetResponse createAsset(CreateAssetRequest request) {

        String symbol = request.symbol().toUpperCase();

        if (assetRepository.findBySymbol(symbol).isPresent()) {
            throw new ConflictException("Asset já cadastrado");
        }

        Asset asset = toEntity(request, symbol);

        assetRepository.save(asset);

        return toResponse(asset);
    }

    public PaginatedResponse<AssetSummaryResponse> listTransactions(Pageable pageable) {
        Page<AssetSummaryResponse> paginatedAssets = assetRepository.findAll(pageable)
                .map(AssetSummaryMapper::toResponse);

        return toResponse(paginatedAssets);
    }

}
