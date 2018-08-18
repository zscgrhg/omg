package com.example.omg;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.AsyncClientHttpRequestExecution;
import org.springframework.http.client.AsyncClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.IOException;

public class MyAsyncClientHttpRequestInterceptor implements AsyncClientHttpRequestInterceptor {
    @Override
    public ListenableFuture<ClientHttpResponse> intercept(HttpRequest request, byte[] body, AsyncClientHttpRequestExecution execution) throws IOException {

        return new TimedListenableFuture(execution.executeAsync(request, body));
    }
}
