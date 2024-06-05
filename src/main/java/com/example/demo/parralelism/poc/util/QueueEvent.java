package com.example.demo.parralelism.poc.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QueueEvent extends BaseEvent {

    private Integer element;

    public QueueEvent(int element, String uuid) {
        this.element = element;
        this.setUuid(uuid);
    }

}
