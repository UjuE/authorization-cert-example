package com.ujuezeoke.learning.certificates.filter;

import com.ujuezeoke.learning.certificates.X509CertificateSecurityContext;
import org.apache.http.entity.StringEntity;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Obianuju Ezeoke on 09/04/2017.
 */

public class ClientCertificateOnlyRequestFilter implements ContainerRequestFilter {

    private static final String REQUEST_X509_CERTIFICATE_PROPERTY_NAME = "javax.servlet.request.X509Certificate";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        final Optional<Object> optionalCertificates = Optional.ofNullable(requestContext.getProperty(REQUEST_X509_CERTIFICATE_PROPERTY_NAME));
        if (optionalCertificates.isPresent()) {
            final X509Certificate[] attribute = (X509Certificate[]) optionalCertificates.get();
            requestContext.setSecurityContext(new X509CertificateSecurityContext(attribute));
        } else {
            requestContext.abortWith(Response.status(Response.Status.NOT_FOUND).entity("Not Found").build());
        }
    }
}
