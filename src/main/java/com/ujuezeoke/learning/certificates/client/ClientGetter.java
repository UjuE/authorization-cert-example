package com.ujuezeoke.learning.certificates.client;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.*;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

/**
 * Created by Obianuju Ezeoke on 10/04/2017.
 */
public class ClientGetter {
    public static void main(String[] args) throws Exception {
        final String file = ClassLoader.getSystemClassLoader().getResource("clientstore.jks").getFile();
        final String defaultType = KeyStore.getDefaultType();
        KeyStore keyStore  = KeyStore.getInstance(defaultType);
        try(FileInputStream instream = new FileInputStream(file)) {
            keyStore.load(instream, "123456".toCharArray());
        }


        final SSLContextBuilder sslContextBuilder = SSLContexts.custom()
                .loadKeyMaterial(keyStore, "123456".toCharArray())
                .loadTrustMaterial((chain, authType) -> {
                    final String collect = Stream.of(chain).map(it -> it.getSubjectDN().getName()).collect(joining(","));
                    System.out.println("collect = " + collect);
                    return true;
                });
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLContext(sslContextBuilder.build())
                .setSSLHostnameVerifier((s, sslSession) -> true)
                .build();
        final HttpGet httpGet = new HttpGet("https://localhost:9823/access");

        try(CloseableHttpResponse execute = httpclient.execute(httpGet)){
            System.out.println(execute.getStatusLine());
        }
    }
}
