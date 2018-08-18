package com.example.omg;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.Arrays;

@Configuration
public class BeanDefs {


    @Bean
    public AsyncClientHttpRequestFactory asyncHttpRequestFactory() {
        return new HttpComponentsAsyncClientHttpRequestFactory(
                asyncHttpClient());
    }

    @Bean
    public AsyncRestTemplate asyncRestTemplate() {

        AsyncRestTemplate restTemplate = new AsyncRestTemplate(
                asyncHttpRequestFactory());
        restTemplate.setInterceptors(Arrays.asList(new MyAsyncClientHttpRequestInterceptor(), new DebugRequestInterceptor()));
        return restTemplate;

    }

    @Bean
    public CloseableHttpAsyncClient asyncHttpClient() {
        try {
            PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(
                    new DefaultConnectingIOReactor(IOReactorConfig.DEFAULT));
            connectionManager.setMaxTotal(1024);
            connectionManager
                    .setDefaultMaxPerRoute(32);
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(3000)
                    .build();
            CloseableHttpAsyncClient httpclient = HttpAsyncClientBuilder
                    .create()
                    .setConnectionManager(connectionManager)
                    .setDefaultRequestConfig(config).build();
            return httpclient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
