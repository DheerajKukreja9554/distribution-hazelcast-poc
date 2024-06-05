package com.example.demo.parralelism.poc;

import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.Executors;

import com.example.demo.parralelism.poc.util.QueueCache;
import com.example.demo.parralelism.poc.util.QueueEvent;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class QueueClient1 {

    public static void main(String[] args) {
        String filename = "sample";
        if (args.length > 0) {
            filename = args[0];
        }
        startPusher(filename);
    }

    public static void startPusher(String filename) {
        HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient();


        QueueCache<QueueEvent> sampleQueue = new QueueCache<>(hazelcastInstance, "sampleQueue");
        hazelcastInstance.getQueue("sampleQueue");

        Flux<QueueEvent> firstFlux = sampleQueue.getFlux();

        firstFlux
                .parallel()
                .runOn(Schedulers.newBoundedElastic(5, Integer.MAX_VALUE, "executor"))
                .doOnNext(i -> log.info("Element {} received on flux", i.getElement()))
                .map(i -> {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                    }
                    return i;
                })
                // .parallel()
                // .runOn(Schedulers.newBoundedElastic(5, Integer.MAX_VALUE, "exec"))
                // .map(i -> i)
                .doOnNext(i -> {
                    try (FileWriter writer = new FileWriter(new File(filename), true)) {
                        writer.append("Element " + i.getElement() + " subscribed on flux on thread " + Thread.currentThread().getName());
                        writer.append("\n");
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                })
                // .collectSortedList((a,b)->Integer.compare(a.getElement(), b.getElement()))
                .subscribe(i -> {
                    log.info("Element {} subscribed on flux", i.getElement());
                });
    }

}
