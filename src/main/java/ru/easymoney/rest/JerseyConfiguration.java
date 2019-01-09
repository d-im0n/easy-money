package ru.easymoney.rest;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {
        packages("ru.easymoney.rest.api").register(JacksonFeature.class);
    }
}
