package com.example.demo.parralelism.poc.util;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import com.hazelcast.collection.IQueue;
import com.hazelcast.collection.ItemEvent;
import com.hazelcast.collection.ItemListener;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.cp.lock.FencedLock;
import com.hazelcast.internal.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class QueueCache<T extends BaseEvent> {

    private final IQueue<T> queue;
    private final Flux<T> elementFlux;
    // private FluxSink<T> emitter;
    // @Getter
    private UUID listenerId;
    boolean complete = false;

    public QueueCache(HazelcastInstance hazelcastInstance, String queueName) {
        log.info("Creating Queue with name: {}", queueName);
        queue = hazelcastInstance.getQueue(queueName);
        this.elementFlux = Flux.create(sink -> {
            this.listenerId = queue.addItemListener(new ItemListener<>() {
                @Override
                public void itemAdded(ItemEvent<T> itemEvent) {

                    T value = queue.poll();
                    if (value != null) {
                        FencedLock lock = hazelcastInstance.getCPSubsystem().getLock(queueName + ":" + value.getUuid());
                        // if (lock.tryLock()) {
                            try {
                                sink.next(value);
                            } catch (Exception e) {
                                log.error("Error which emitting element: ", e);
                            } finally {
                                lock.unlock();
                            }
                        // } else {
                        //     log.info("Could not acquire lock, skipping element of type {}: {}",
                        //             itemEvent.getItem().getClass().getName(), itemEvent.getItem());
                        // }
                    }
                }

                @Override
                public void itemRemoved(ItemEvent<T> itemEvent) {
                    log.info("itemRemoved: {}", itemEvent.getItem());
                    if (complete)
                        sink.complete();
                }

            }, true);
        });


    }

    public Flux<T> getFlux() {

        return this.elementFlux;
    }

    public void add(T value) {
        value.setUuid(UuidUtil.newSecureUuidString());
        queue.add(value);
    }

    public void addAll(List<T> value) {
        queue.addAll(value);
    }

    public void saveElement(T value) throws InterruptedException {
        queue.put(value);
    }

    public void complete() {
        queue.removeItemListener(listenerId);
        this.complete = true;
    }

    public void clear() {
        queue.clear();
    }

    public T poll() {
        return queue.poll();
    }

    public int size() {
        return queue.size();
    }

    public Stream<T> getAllAsStream() {
        return queue.stream();
    }

    public void drainTo(List<T> list) {
        queue.drainTo(list);
    }

}
