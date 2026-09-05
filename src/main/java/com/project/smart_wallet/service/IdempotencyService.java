package com.project.smart_wallet.service;

import com.project.smart_wallet.domain.IdempotencyStatus;
import com.project.smart_wallet.dto.redis.IdempotencyCache;
import com.project.smart_wallet.conf.properties.IdempotencyProperties;
import com.project.smart_wallet.utils.redis.IdempotencyRedisKey;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class IdempotencyService {

    private final RedisTemplate<String, IdempotencyCache> redisTemplate;

    private final UserService userService;

    private final IdempotencyProperties idempotencyProperties;

    public String buildKey(HttpServletRequest request, String idempotencyKey) {
        String userId = userService.getAuthenticatedUserId();
        return IdempotencyRedisKey.buildKey(
                request.getMethod(),
                request.getServletPath(),
                userId,
                idempotencyKey
        );
    }

    public boolean markAsProcessing(String redisKey, String payloadHash) {
        Boolean processed = redisTemplate.opsForValue().setIfAbsent(
                redisKey,
                new IdempotencyCache(payloadHash),
                idempotencyProperties.processingTtl()
        );

        if (processed == null) {
            throw new IllegalStateException("Falha ao registrar o estado PROCESSING da idempotência.");
        }

        return processed;
    }

    public void markAsCompleted(String redisKey, HttpStatusCode statusCode, String body) {
        IdempotencyCache idempotencyCache = redisTemplate.opsForValue().get(redisKey);
        idempotencyCache.setStatusCode(statusCode.value());
        idempotencyCache.setIdempotencyStatus(IdempotencyStatus.COMPLETED);
        idempotencyCache.setResponse(body);

        redisTemplate.opsForValue().set(redisKey, idempotencyCache, idempotencyProperties.completedTtl());
    }

    public void markAsFailed(String redisKey) {
        redisTemplate.delete(redisKey);
    }

    public IdempotencyCache getIdempotencyCache(String redisKey) {
        return redisTemplate.opsForValue().get(redisKey);
    }

    public String generatePayloadHash(byte[] body) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(body);

        return HexFormat.of().formatHex(encodedHash);
    }
}
