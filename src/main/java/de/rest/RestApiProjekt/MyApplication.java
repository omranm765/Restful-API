package de.rest.RestApiProjekt;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

    public class MyApplication extends ResourceConfig {
        public MyApplication() {
            register(JacksonFeature.class);  // Unterstützung für JSON
            register(BestellungResource.class);  // Registrierung der BestellungResource
        }
    }


