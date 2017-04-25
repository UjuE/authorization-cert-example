package com.ujuezeoke.learning.certificates.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Obianuju Ezeoke on 09/04/2017.
 */
public class InspectTheCertificateFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filterConfig = " + filterConfig.getServletContext().getServerInfo());

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("InspectTheCertificateFilter.doFilter");
    }

    @Override
    public void destroy() {
        System.out.println("InspectTheCertificateFilter.destroy");
    }
}
