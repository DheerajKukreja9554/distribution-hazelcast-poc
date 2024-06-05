package com.example.demo.parralelism.poc;

import com.example.demo.parralelism.poc.util.QueueCache;
import com.example.demo.parralelism.poc.util.QueueEvent;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.internal.util.UuidUtil;
import reactor.core.publisher.Flux;

public class QueuePusher {
    public static void main(String[] args) {
        HazelcastInstance instance = HazelcastClient.newHazelcastClient();

        QueueCache<QueueEvent> sampleTopic = new QueueCache<>(instance, "sampleQueue");
        Flux.range(1, 1000)
                .map(i -> new QueueEvent(i, UuidUtil.newSecureUuidString()))
                .doOnNext(sampleTopic::add)
                .subscribe(e -> System.out.println("Pushed " + e));



    }
}
