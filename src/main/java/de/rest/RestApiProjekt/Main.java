package de.rest.RestApiProjekt;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Main {
    public static final String BASE_URI = "http://localhost:8080/";

    public static void main(String[] args) {
        try {
            final ResourceConfig resourceConfig = new MyApplication();  // Konfiguration der Anwendung
            GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig);  // Start des Servers
            System.out.println("Server started. Press Ctrl+C to exit...");  // Ausgabe einer Nachricht
            Thread.currentThread().join();  // Server läuft weiter bis zum Abbruch
        } catch (Exception e) {
            e.printStackTrace();  // Ausgabe von Fehlern
        }
    }
}
