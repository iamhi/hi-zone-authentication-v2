package com.github.iamhi.hizone.authentication.v2.out.redis;

import com.github.iamhi.hizone.authentication.v2.out.redis.actions.ExtendTokenAction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
record ExtendTokenActionImpl(
    RedisRepository redisRepository
) implements ExtendTokenAction {

    @Override
    public Mono<String> apply(String tokenId, Long expirationTime) {
        return redisRepository().getReactiveConnection()
            .expire(tokenId, expirationTime)
            .map(expirationSet -> expirationSet ? tokenId : "");
    }
}
