package com.github.iamhi.hizone.authentication.v2.out.redis;

import com.github.iamhi.hizone.authentication.v2.config.RedisConfig;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.pubsub.api.reactive.RedisPubSubReactiveCommands;
import org.springframework.stereotype.Service;

@Service
class RedisClientWrapper implements RedisRepository {

    RedisReactiveCommands<String, String> reactiveCommands;

    RedisPubSubReactiveCommands<String, String> reactivePubSubCommands;

    public RedisClientWrapper(RedisConfig redisConfig) {
        RedisClient redisClient = RedisClient.create("redis://" + redisConfig.getHost() + ":" + redisConfig.getPort());
        StatefulRedisConnection<String, String> connection = redisClient.connect();

        reactivePubSubCommands = redisClient.connectPubSub().reactive();
        reactiveCommands = connection.reactive();
    }

    @Override
    public RedisReactiveCommands<String, String> getReactiveConnection() {
        return reactiveCommands;
    }
}
