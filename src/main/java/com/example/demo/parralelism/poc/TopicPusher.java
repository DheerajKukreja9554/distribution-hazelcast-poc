package com.example.demo.parralelism.poc;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;

public class TopicPusher {
    public static void main(String[] args) {
        HazelcastInstance instance = HazelcastClient.newHazelcastClient();

        ITopic<Integer> sampleTopic = instance.getTopic("sampleTopic");
        for (int i = 0; i < 10; i++) {
            System.out.println("pushing: " + i);
            sampleTopic.publish(i);
        }

    }
}
