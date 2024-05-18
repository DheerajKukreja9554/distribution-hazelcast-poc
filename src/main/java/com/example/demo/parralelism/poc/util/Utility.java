package com.example.demo.parralelism.poc.util;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.cp.IAtomicLong;
import com.hazelcast.cp.lock.FencedLock;
import com.hazelcast.topic.MessageListener;
import org.slf4j.Logger;
import reactor.core.publisher.FluxSink;

public class Utility {

    public static MessageListener<Integer> newListener(FluxSink<Integer> sink, HazelcastInstance hazelcastInstance, Logger log) {
        return message -> {
            IAtomicLong atomicLong = hazelcastInstance.getCPSubsystem().getAtomicLong("sampleTopicCounter");

            FencedLock lock = hazelcastInstance.getCPSubsystem().getLock("sampletopicLock: " + atomicLong.get());
            log.info("acquiring lock {} for element {}",lock.getName(),message.getMessageObject());
            if (lock.tryLock()) {
                atomicLong.incrementAndGet();
                try {
                    sink.next(message.getMessageObject());
//                    atomicLong.incrementAndGet();
                } catch (Exception e) {
                    log.info("error while acquiring lock {} for element {}",lock.getName(),message.getMessageObject(),e);
                } finally {
                    lock.unlock();
                }
            } else
                log.info("unable to acquire lock {} on for element {}",lock.getName(), message.getMessageObject());
        };
    }
}
