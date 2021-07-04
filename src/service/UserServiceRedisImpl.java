package service;

import model.User;
import org.apache.commons.codec.digest.DigestUtils;
import redis.clients.jedis.Jedis;
import service.exception.DuplicateEntryException;
import service.exception.NotFoundException;
import util.JedisClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserServiceRedisImpl {

    private static final String DB_PREFIX = "u#";
    private final Jedis client;

    public UserServiceRedisImpl() {
        client = JedisClient.getInstance().getClient();
    }

    public void addUser(User user) throws DuplicateEntryException {

        if (client.exists(DB_PREFIX + user.getUsername())) {
            throw new DuplicateEntryException();
        }

        client.hset(DB_PREFIX + user.getUsername(), user.toMap());
    }

    public void updateUser(User userToUpdate, String previousUsername) throws NotFoundException {

        if (!client.exists(DB_PREFIX + previousUsername)) {
            throw new NotFoundException();
        }

        client.rename(DB_PREFIX + previousUsername, DB_PREFIX + userToUpdate.getUsername());
        client.hset(DB_PREFIX + userToUpdate.getUsername(), userToUpdate.toMap());
    }

    public User searchUser(String username) throws NotFoundException {

        if (!client.exists(DB_PREFIX + username)) {
            throw new NotFoundException();
        }

        return User.fromMap(username, client.hgetAll(DB_PREFIX + username));
    }

    public User searchUserByNic(String nic) throws NotFoundException {
        Set<String> usernameList = client.keys(DB_PREFIX + "*");

        for (String username : usernameList) {
            if (client.hget(username, "nic").equals(nic)) {
                return User.fromMap(username.substring(2), client.hgetAll(DB_PREFIX + username));
            }
        }
        throw new NotFoundException();
    }

    public List<User> searchUsersByKeyword(String keyword) {

        if (keyword.equals("")) {
            return searchAllUsers();
        }

        keyword = keyword.toLowerCase();
        List<User> searchResult = new ArrayList();
        Set<String> usernameList = client.keys(DB_PREFIX + "*");

        for (String username : usernameList) {
            for (Map.Entry<String, String> entry : client.hgetAll(username).entrySet()) {
                if (entry.getKey().equals("password")) {
                    continue;
                }

                if (entry.getValue().toLowerCase().contains(keyword)) {
                    searchResult.add(User.fromMap(username.substring(2), client.hgetAll(username)));
                    break;
                }
            }
        }

        return searchResult;
    }

    public List<User> searchAllUsers() {
        List<User> userList = new ArrayList<>();
        Set<String> usernameList = client.keys(DB_PREFIX + "*");

        for (String username : usernameList) {
            userList.add(User.fromMap(username.substring(2), client.hgetAll(username)));
        }

        return userList;
    }

    public boolean authenticate(String username, String password) {
        if (!client.exists(DB_PREFIX + username)) {
            return false;
        }

        String originalPwdHash = client.get(DB_PREFIX + username);
        String pwdHash = DigestUtils.sha256Hex(password);

        return originalPwdHash.equals(pwdHash);
    }
}
