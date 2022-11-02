package com.github.iamhi.hizone.authentication.v2.out.redis;

import io.lettuce.core.api.reactive.RedisReactiveCommands;

interface RedisRepository {

    RedisReactiveCommands<String, String> getReactiveConnection();
}
