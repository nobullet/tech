package com.nobullet.vertx;

import com.nobullet.vertx.handlers.VideoController;
import com.nobullet.vertx.services.UserService;
import com.nobullet.vertx.services.VideoService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import java.util.logging.Logger;

/**
 * Entry point for the application.
 */
public class Main extends AbstractVerticle {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    @Override
    public void start() throws Exception {
        HttpServerOptions options = new HttpServerOptions();
        options.setPort(8080);
        
        Router moduleRouter = Router.router(vertx);
        moduleRouter.get("/video*").handler(new VideoController(new UserService(), new VideoService()));
        
        Router parentRouter = Router.router(vertx);
        parentRouter.mountSubRouter("/appcontext", moduleRouter);
        
        HttpServer server = vertx.createHttpServer(options);
        server.requestHandler(parentRouter::accept).listen();
        logger.info("Vertx application has been started.");
    }

    @Override
    public void stop() throws Exception {
        logger.info("Vertx application has been stopped.");
    }

    public static void main(String... args) {
        VertxOptions options = new VertxOptions();
        Vertx vertx = Vertx.vertx(options);
        vertx.deployVerticle(new Main());
    }
}
