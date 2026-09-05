package com.project.smart_wallet.utils.redis;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IdempotencyRedisKey {

    private final static String PREFIX = "idempotency";

    public static String buildKey(String method, String path, String userId, String idempotencyKey) {
        return PREFIX + ":" +  method + ":" + path + ":" + userId + ":" + idempotencyKey;
    }
}
