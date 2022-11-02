package com.github.iamhi.hizone.authentication.v2.out.redis;

import com.github.iamhi.hizone.authentication.v2.out.redis.actions.CreateTokenAction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
record CreateTokenActionImpl(
    RedisRepository redisRepository
) implements CreateTokenAction {

    @Override
    public Mono<String> apply(String tokenData, Long expirationTime) {
        return Mono.just(UUID.randomUUID().toString())
            .flatMap(tokenId -> redisRepository.getReactiveConnection()
                .setex(tokenId, expirationTime, tokenData).thenReturn(tokenId));
    }
}
