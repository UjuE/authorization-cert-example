package com.ujuezeoke.learning.certificates.resourceconfig;

import com.ujuezeoke.learning.certificates.controllers.AllPassController;
import com.ujuezeoke.learning.certificates.filter.ClientCertificateOnlyRequestFilter;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by Obianuju Ezeoke on 09/04/2017.
 */
public class MainResourceConfig extends ResourceConfig {
    public MainResourceConfig() {
        register(new ClientCertificateOnlyRequestFilter());
        register(new AllPassController());
    }
}
