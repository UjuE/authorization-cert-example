package com.ujuezeoke.learning.certificates.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Created by Obianuju Ezeoke on 10/04/2017.
 */
public class StuffIAmExperimentingWith implements SecurityContext {
    private final ContainerRequestContext requestContext;

    public StuffIAmExperimentingWith(ContainerRequestContext requestContext) {

        this.requestContext = requestContext;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public boolean isSecure() {
        System.out.println("requestContext.getUriInfo() = " + requestContext.getUriInfo());
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }
}
