# Vertx demo
Vertx demo application with a couple of asynchronous services: User service and Video service.

Building the application:
1. cd {project_dir}
2. mvn clean compile package
3. java -jar target/vertxBasicApp-1.0-SNAPSHOT.jar

Or use scripts *./buildjar.sh* and *./run.sh*.

# Web app URLs

Open one of the URLs in browser:

*Videos by owner*: http://localhost:8080/appcontext/video/?userId=123456
*Search for videos*: http://localhost:8080/appcontext/video/?q=music
