package com.project.smart_wallet.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record AssetPriceResponse(

        @JsonProperty("brl")
        BigDecimal price
) {
}
