package com.ujuezeoke.learning.certificates;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Obianuju Ezeoke on 25/04/2017.
 */
public class X509CertificateSecurityContext implements SecurityContext {
    private final List<X509Certificate> certificates;

    public X509CertificateSecurityContext(X509Certificate[] attribute) {
        this(Arrays.asList(attribute));
    }

    public X509CertificateSecurityContext(List<X509Certificate> certificates){
        this.certificates = certificates;
    }

    @Override
    public Principal getUserPrincipal() {
        return certificates.get(0).getSubjectX500Principal();
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public boolean isSecure() {
        return !certificates.isEmpty();
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.CLIENT_CERT_AUTH;
    }
}
