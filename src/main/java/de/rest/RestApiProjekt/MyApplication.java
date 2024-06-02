package de.rest.RestApiProjekt;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class MyApplication extends ResourceConfig {
    public MyApplication() {
        register(JacksonFeature.class);
        register(BestellungResource.class);
        register(KundenResource.class);
        register(ArtikelResource.class);
        configureSwagger();
        packages("io.swagger.jaxrs.listing");
    }

    private void configureSwagger() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage("de.rest.RestApiProjekt");
        beanConfig.setPrettyPrint(true);
        beanConfig.setScan(true);
    }
}
