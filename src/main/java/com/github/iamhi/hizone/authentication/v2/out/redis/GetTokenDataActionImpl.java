package com.github.iamhi.hizone.authentication.v2.out.redis;

import com.github.iamhi.hizone.authentication.v2.out.redis.actions.GetTokenDataAction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
record GetTokenDataActionImpl(
    RedisRepository redisRepository
) implements GetTokenDataAction {
    @Override
    public Mono<String> apply(String tokenId) {
        return Mono.justOrEmpty(tokenId).flatMap(redisRepository.getReactiveConnection()::get);
    }
}
