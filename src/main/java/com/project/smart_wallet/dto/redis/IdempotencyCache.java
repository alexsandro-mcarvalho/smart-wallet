package com.project.smart_wallet.dto.redis;

import com.project.smart_wallet.domain.IdempotencyStatus;
import com.project.smart_wallet.dto.response.CreateTransactionResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
public class IdempotencyCache {

        @Setter
        private IdempotencyStatus idempotencyStatus;

        @Setter
        private Integer statusCode;

        private final String payloadHash;

        @Setter
        private String response;

        public IdempotencyCache(String payloadHash) {
            this.idempotencyStatus = IdempotencyStatus.PROCESSING;
            this.payloadHash = payloadHash;
        }
}