package de.rest.RestApiProjekt;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Main {
    public static final String BASE_URI = "http://localhost:8080/";

    public static void main(String[] args) {
        try {
            final ResourceConfig resourceConfig = new MyApplication();
            HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig);

            StaticHttpHandler staticHttpHandler = new StaticHttpHandler("src/main/resources");
            server.getServerConfiguration().addHttpHandler(staticHttpHandler, "/static");

            System.out.println("Server started. Press Ctrl+C to exit...");
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
