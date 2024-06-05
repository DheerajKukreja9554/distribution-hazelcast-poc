package com.example.demo.parralelism.poc.util;

import com.hazelcast.collection.ItemEvent;
import com.hazelcast.collection.ItemListener;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.cp.lock.FencedLock;
import com.hazelcast.topic.MessageListener;
import org.slf4j.Logger;
import reactor.core.publisher.FluxSink;

public class Utility {

    public static MessageListener<Integer> newListener(FluxSink<Integer> sink, HazelcastInstance hazelcastInstance, Logger log) {
        return message -> {
            // IAtomicLong atomicLong = hazelcastInstance.getCPSubsystem().getAtomicLong("sampleTopicCounter");

            FencedLock lock =
                    hazelcastInstance.getCPSubsystem()
                            .getLock("sampletopicLock: " + message.getMessageObject());
            log.info("acquiring lock {} for element {}", lock.getName(), message.getMessageObject());
            try {
                if (lock.tryLock()) {
                    // atomicLong.incrementAndGet();
                    try {
                        sink.next(message.getMessageObject());
                    } catch (Exception e) {
                        log.info("error while acquiring lock {} for element {}", lock.getName(), message.getMessageObject(), e);
                    } finally {
                        lock.destroy();
                    }

                } else
                    log.info("unable to acquire lock {} on for element {}", lock.getName(), message.getMessageObject());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    public static ItemListener<QueueEvent> newItemListener(FluxSink<QueueEvent> sink, HazelcastInstance hazelcastInstance, Logger log) {
        return new ItemListener<QueueEvent>() {

            @Override
            public void itemAdded(ItemEvent<QueueEvent> item) {

                FencedLock lock =hazelcastInstance.getCPSubsystem().getLock("sampleQueueLock: " + item.getItem().getUuid());
                log.info("acquiring lock {} for element {}", lock.getName(), item.getItem().getElement());
                try {
                    if (lock.tryLock()) {
                        try {
                            
                            sink.next(item.getItem());
                        } catch (Exception e) {
                            log.info("error while acquiring lock {} for element {}", lock.getName(),item.getItem().getElement(), e);
                        } finally {
                            lock.destroy();
                        }

                    } else
                        log.info("unable to acquire lock {} on for element {}", lock.getName(),item.getItem().getElement());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void itemRemoved(ItemEvent<QueueEvent> item) {
                // TODO Auto-generated method stub
                log.info("Item {} removed ", item.getItem().getElement());
            }

        };
    }
}
