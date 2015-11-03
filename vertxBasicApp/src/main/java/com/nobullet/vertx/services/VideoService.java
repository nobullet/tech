package com.nobullet.vertx.services;

import com.nobullet.vertx.entities.User;
import com.nobullet.vertx.entities.Video;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Asynchronous video service.
 */
public class VideoService {

    private static final Random random = new Random();
    // Scheduler to emulate async operation result.
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Returns videos by user. Shows example of the composing two subsequent asynchronous operations: at first, the user
     * is retrieved, then his videos are retrieved.
     *
     * @param userFuture Completable future of the user.
     * @return User future.
     */
    public CompletableFuture<List<Video>> getByOwner(CompletableFuture<User> userFuture) {
        return userFuture.thenCompose(this::getByOwner);
    }

    /**
     * Returns videos by the user. This could be easily replaced by any real data access operation.
     *
     * @param user User.
     * @return Completable future of the list of the videos by the given user.
     */
    public CompletableFuture<List<Video>> getByOwner(User user) {
        CompletableFuture<List<Video>> videoFuture = new CompletableFuture<>();
        scheduler.schedule(() -> {
            videoFuture.complete(generateDummyVideos(user, "Videos of user " + user.getEmail(), 5));
        }, random.nextInt(2000), TimeUnit.MILLISECONDS);
        return videoFuture;
    }

    /**
     * Returns a list of videos. This could be easily replaced by any asynchronous real data access operation.
     *
     * @param query Search query.
     * @return Future of the list of the videos.
     */
    public CompletableFuture<List<Video>> search(String query) {
        CompletableFuture<List<Video>> videoFuture = new CompletableFuture<>();
        scheduler.schedule(() -> {
            videoFuture.complete(generateDummyVideos(null, query, 5));
        }, random.nextInt(2000), TimeUnit.MILLISECONDS);
        return videoFuture;
    }

    /**
     * Generates a list of videos by owner. This is an example of a data in a datasource.
     *
     * @param owner Owner of the video. If null, then owned is generated for each video.
     * @param namePrefix Video name prefix.
     * @param number Number of videos to create.
     * @return List of dummy videos.
     */
    private static List<Video> generateDummyVideos(User owner, String namePrefix, int number) {
        long now = System.currentTimeMillis();
        List<Video> videos = new ArrayList<>(number);
        while (number-- > 0) {
            List<String> sources = new ArrayList<>();
            sources.add("http://url/vid/v" + number + ".mp4");
            sources.add("http://url/vid/v" + number + ".avi");
            User user = owner;
            if (user == null) {
                int rnd = random.nextInt(Integer.MAX_VALUE);
                user = new User("u" + rnd, "email" + rnd + "@somewhere.com", "pwd" + rnd, now, now + number * 2);
            }
            videos.add(new Video("videoId-" + number, user, namePrefix + " - " + number,
                    sources, random.nextInt(Integer.MAX_VALUE), now, now + number * 2));
        }
        return videos;
    }
}
