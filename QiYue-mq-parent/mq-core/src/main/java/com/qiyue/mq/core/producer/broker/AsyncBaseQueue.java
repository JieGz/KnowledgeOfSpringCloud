package com.qiyue.mq.core.producer.broker;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AsyncBaseQueue {

    private static final int thread_size = Runtime.getRuntime().availableProcessors();

    private static final int queue_size = 1000;


    private static final ExecutorService executorService = new ThreadPoolExecutor(thread_size, thread_size, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(queue_size), r -> {
        final Thread thread = new Thread(r);
        thread.setName("Rabbit_MQ_client_async_queue");
        return thread;
    }, (r, executor) -> System.out.println("AsyncBaseQueue#.rejectedExecution"));


    public static void submit(Runnable runnable) {
        executorService.submit(runnable);
    }
}
