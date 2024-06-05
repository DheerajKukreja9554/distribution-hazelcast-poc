package com.example.demo.parralelism.poc;

import com.example.demo.parralelism.poc.util.Utility;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Slf4j
public class TopicClient2 {

    public static void main(String[] args) {
        HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient();

        ITopic<Integer> sampleTopic = hazelcastInstance.getTopic("sampleTopic");
        Flux<Integer> firstFlux = Flux.create(sink -> {
            sampleTopic.addMessageListener(Utility.newListener(sink, hazelcastInstance, log));
        }, FluxSink.OverflowStrategy.BUFFER);

        firstFlux
                // .parallel()
                // .runOn(Schedulers.fromExecutor(Executors.newFixedThreadPool(2)))
                .doOnNext(i -> log.info("Element {} received on flux", i))
                .doOnNext(i -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("Element {} slept on first flux", i);
                })
                .map(i -> i)
                .subscribe(i -> log.info("Element {} subscribed on flux", i));
    }

}
