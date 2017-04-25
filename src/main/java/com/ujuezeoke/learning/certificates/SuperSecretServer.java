package com.ujuezeoke.learning.certificates;

import com.ujuezeoke.learning.certificates.filter.InspectTheCertificateFilter;
import com.ujuezeoke.learning.certificates.resourceconfig.MainResourceConfig;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import java.net.URL;

/**
 * Created by Obianuju Ezeoke on 09/04/2017.
 */
public class SuperSecretServer {

    static {
        Logger root = Logger.getRootLogger();
        root.setLevel(Level.DEBUG);
        root.addAppender(new ConsoleAppender(
                new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));

        System.setProperty("org.eclipse.jetty.server.Server.LEVEL", "DEBUG");
    }

    private static final int PORT = 9823;
    private final Server server;

    public SuperSecretServer() {
        server = new Server();
        createHttpConnector();
        addHandlers();
    }

    public void start(){
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addHandlers() {
        server.setHandler(getServletHandler());
    }

    private ServletContextHandler getServletHandler() {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setContextPath("");

        contextHandler.addServlet(new ServletHolder(new ServletContainer(resourceConfigs())), "/*");
//        contextHandler.addFilter(new FilterHolder(new InspectTheCertificateFilter()), "/", null);
        return contextHandler;
    }

    private ResourceConfig resourceConfigs() {
        return new MainResourceConfig();
    }

    private void createHttpConnector() {
        SslContextFactory sslContextFactory = new SslContextFactory(true);
        final URL resource = ClassLoader.getSystemClassLoader().getResource(
                "serverkeystore.jks");
        sslContextFactory.setKeyStorePath(resource.toExternalForm());
        sslContextFactory.setKeyStorePassword("123456");
        sslContextFactory.setKeyManagerPassword("1234");
        sslContextFactory.setWantClientAuth(true);

        final HttpConfiguration config = new HttpConfiguration();
        config.addCustomizer(new SecureRequestCustomizer());

        config.setSecureScheme("https");
        config.setSecurePort(PORT);
        ServerConnector sslConnector = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory, "http/1.1"),
                new HttpConnectionFactory(config));
        sslConnector.setPort(PORT);
        server.addConnector(sslConnector);
    }

    public static void main(String[] args) {
        new SuperSecretServer().start();
    }
}
