package util;

import org.apache.commons.codec.digest.DigestUtils;
import redis.clients.jedis.Jedis;

public class JedisClient {
    private static JedisClient jedisClient;
    private final Jedis client;

    private JedisClient() {
        client = new Jedis("localhost", 9090);
        client.auth(DigestUtils.sha256Hex("DC"));
    }

    public static JedisClient getInstance() {
        return (jedisClient == null) ? jedisClient = new JedisClient() : jedisClient;
    }

    public Jedis getClient() {
        return client;
    }
}
