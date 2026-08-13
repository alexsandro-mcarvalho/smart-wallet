package com.project.smart_wallet.mapper;

import com.project.smart_wallet.domain.Asset;
import com.project.smart_wallet.dto.request.CreateAssetRequest;
import com.project.smart_wallet.dto.response.CreateAssetResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CreateAssetMapper {

    public static Asset toEntity(CreateAssetRequest request, String symbol) {
        return new Asset(
                request.name(),
                request.assetType(),
                symbol,
                request.logoUrl(),
                request.interestRate(),
                request.interestRatePeriod()
        );
    }

    public static CreateAssetResponse toResponse(Asset entity) {
        return new CreateAssetResponse(
                entity.getId(),
                entity.getName(),
                entity.getAssetType(),
                entity.getSymbol(),
                entity.getLogoUrl(),
                entity.getInterestRate(),
                entity.getInterestRatePeriod(),
                entity.getCreatedAt()
        );
    }
}
