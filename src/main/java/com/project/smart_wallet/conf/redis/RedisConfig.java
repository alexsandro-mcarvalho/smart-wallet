package com.project.smart_wallet.conf.redis;

import com.project.smart_wallet.dto.redis.AssetPriceCache;
import com.project.smart_wallet.dto.redis.IdempotencyCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, AssetPriceCache> assetPriceRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, AssetPriceCache> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JacksonJsonRedisSerializer<>(AssetPriceCache.class));
        return template;
    }

    @Bean
    public RedisTemplate<String, IdempotencyCache> idempotencyTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, IdempotencyCache> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JacksonJsonRedisSerializer<>(IdempotencyCache.class));
        return template;
    }
}
