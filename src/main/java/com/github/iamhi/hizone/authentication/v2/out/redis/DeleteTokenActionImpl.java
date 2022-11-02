package com.github.iamhi.hizone.authentication.v2.out.redis;

import com.github.iamhi.hizone.authentication.v2.out.redis.actions.DeleteTokenAction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
record DeleteTokenActionImpl(
    RedisRepository redisRepository
) implements DeleteTokenAction {

    @Override
    public Mono<Boolean> apply(String tokenId) {
        return redisRepository.getReactiveConnection().del(tokenId).map(numDeletedKeys -> numDeletedKeys > 0);
    }
}
