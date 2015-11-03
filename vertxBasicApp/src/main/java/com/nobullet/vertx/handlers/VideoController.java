package com.nobullet.vertx.handlers;

import com.nobullet.vertx.entities.User;
import com.nobullet.vertx.entities.Video;
import com.nobullet.vertx.services.UserService;
import com.nobullet.vertx.services.VideoService;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Video controller.
 */
public class VideoController implements Handler<RoutingContext> {

    private final UserService userService;
    private final VideoService videoService;

    /**
     * Constructs controller (handler).
     *
     * @param userService User service.
     * @param videoService Video service.
     */
    public VideoController(UserService userService, VideoService videoService) {
        this.userService = userService;
        this.videoService = videoService;
    }

    /**
     * Starts asynchronous fetching for videos (by owner or by query) and suspends current request.
     *
     * @param context Request context.
     */
    @Override
    public void handle(RoutingContext context) {
        if (context.request().getParam("userId") != null) {
            // If request contains userId, then return videos by owner with id == userId.
            getVideosByOwner(context.request().getParam("userId"), context);
        } else {
            // Otherwise search for videos by query parameter q.
            searchVideos(context.request().getParam("q"), context);
        }
        context.request().pause();
    }

    /**
     * Suspends current request, retrieves concurrently logged in user and searches for videos by given query.
     *
     * @param query Query for the video.
     * @param context Request context to resume later.
     */
    private void searchVideos(String query, RoutingContext context) {
        query = query == null || query.isEmpty() ? "new" : query;

        CompletableFuture<User> userFuture = userService.getUserById("loggedIn");
        CompletableFuture<List<Video>> videosFuture = videoService.search(query);

        userFuture.thenCombine(videosFuture, (user, videos) -> renderSearchVideos(user, videos, context));
    }

    /**
     * Suspends current request, request a user and passes user completable future to video service. Example of tho
     * subsequent usages of asynchronous services: after the user is loaded, video service loads his videos.
     *
     * @param ownerId User id of the owner.
     * @param context Request context to resume later.
     */
    private void getVideosByOwner(String ownerId, RoutingContext context) {
        CompletableFuture<User> userFuture = userService.getUserById(ownerId);

        videoService.getByOwner(userFuture)
                .thenAccept(videos -> renderVideosByOwner(userFuture.getNow(null), videos, context));
    }

    /**
     * Resumes request and renders videos searched by query plus currently logged in user.
     *
     * @param loggedInUser Logged in user.
     * @param videos Videos to render.
     * @param context Request context to resume.
     * @return Void (null) for
     * {@link java.util.concurrent.CompletionStage#thenCombine(java.util.concurrent.CompletionStage, java.util.function.BiFunction)}.
     */
    private static Void renderSearchVideos(User loggedInUser, List<Video> videos, RoutingContext context) {
        context.request().resume();
        context.response().putHeader("content-type", "text/plain");
        StringBuilder result = new StringBuilder("User: ")
                .append(loggedInUser)
                .append("\nVideos:\n\n");
        videos.stream().forEach(video -> result.append(video).append("\n"));
        context.response().end(result.toString());
        return null;
    }

    /**
     * Resumes request and renders videos by the owner.
     *
     * @param videos Videos to render.
     * @param context Request context.
     */
    private static void renderVideosByOwner(User loggedInUser, List<Video> videos, RoutingContext context) {
        context.request().resume();
        context.response().putHeader("content-type", "text/plain");
        StringBuilder result = new StringBuilder("Videos (owner id ")
                .append(loggedInUser.getId())
                .append("):\n\n");
        videos.stream().forEach(video -> result.append(video).append("\n"));
        context.response().end(result.toString());
    }
}
