package db.repository;

import db.model.BlacklistItem;
import redis.clients.jedis.Jedis;

import java.time.Instant;

public class BlacklistRepositoryImpl implements BlacklistRepository {

    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;
    private static final String BLACKLIST_KEY = "token_blacklist";

    private final Jedis jedis;

    public BlacklistRepositoryImpl() {
        jedis = new Jedis(REDIS_HOST, REDIS_PORT);
    }

    @Override
    public void addToBlacklist(BlacklistItem item) {
        jedis.hset(BLACKLIST_KEY, item.getTokenId(), String.valueOf(item.getTimestamp().getEpochSecond()));
    }

    @Override
    public boolean isTokenBlacklisted(String tokenId) {
        String timestamp = jedis.hget(BLACKLIST_KEY, tokenId);
        return timestamp != null && Instant.now().isBefore(Instant.ofEpochSecond(Long.parseLong(timestamp)));
    }

    public void close() {
        jedis.close();
    }


}
