package com.nobullet.vertx.services;

import com.nobullet.vertx.entities.User;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * User service.
 */
public class UserService {

    private static final Random random = new Random();
    // Scheduler to emulate async operation result.
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Returns a completable future of the user by given user id. This could be easily replaced by any real asynchronous
     * data read/web service execution.
     *
     * @param id User id.
     * @return Completable future of the user.
     */
    public CompletableFuture<User> getUserById(String id) {
        Long created = System.currentTimeMillis();
        CompletableFuture<User> result = new CompletableFuture<>();
        scheduler.schedule(() -> {
            result.complete(dummyWithId(id, created));
        }, random.nextInt(2000), TimeUnit.MILLISECONDS);
        return result;
    }

    /**
     * Generate dummy user id by given parameters.
     *
     * @param id User id.
     * @param created Time of creation (request).
     * @return Dummy user.
     */
    private static User dummyWithId(String id, long created) {
        String safeId = id != null ? id.substring(0, id.length() > 10 ? 10 : id.length()) : "null";
        return new User(safeId, safeId + "@example.com", "pwd", created, System.currentTimeMillis());
    }
}
