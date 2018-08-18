package com.example.omg;

import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TimedListenableFuture<T> implements ListenableFuture<T> {
    private final ListenableFuture<T> delegate;
    private final long start=System.nanoTime();

    public TimedListenableFuture(ListenableFuture<T> delegate) {
        this.delegate = delegate;
        this.delegate.addCallback(new ListenableFutureCallback<T>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("elasped->"+(System.nanoTime()-start)/1000000);
            }

            @Override
            public void onSuccess(T result) {
                System.out.println("elasped->"+(System.nanoTime()-start)/1000000);
            }
        });
    }

    @Override
    public void addCallback(ListenableFutureCallback<? super T> callback) {
        delegate.addCallback(callback);
    }

    @Override
    public void addCallback(SuccessCallback<? super T> successCallback, FailureCallback failureCallback) {
        delegate.addCallback(successCallback, failureCallback);
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return delegate.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return delegate.isCancelled();
    }

    @Override
    public boolean isDone() {
        return delegate.isDone();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return delegate.get();
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return delegate.get(timeout, unit);
    }
}
