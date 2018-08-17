package com.example.omg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.stream.IntStream;

@Component
public class Fire implements CommandLineRunner {

    @Autowired
    AsyncRestTemplate restTemplate;
    @Override
    public void run(String... args) throws Exception {
        System.out.println(Thread.currentThread().getName()+"  -> "+Thread.currentThread().getId());
        for (int i = 0; i < 1000; i++) {
            IntStream.range(1,10)
                    .forEach(x->{
                        ListenableFuture<ResponseEntity<String>> forEntity = restTemplate.getForEntity("https://cn.bing.com/", String.class);
                        forEntity.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
                            @Override
                            public void onFailure(Throwable throwable) {

                            }

                            @Override
                            public void onSuccess(ResponseEntity<String> stringResponseEntity) {
                                System.out.println(stringResponseEntity.getStatusCodeValue());
                                System.out.println(Thread.currentThread().getName()+"  -> "+Thread.currentThread().getId());
                            }
                        });
                    });
            Thread.sleep(5000);
        }
        System.out.println(restTemplate);
    }
}
