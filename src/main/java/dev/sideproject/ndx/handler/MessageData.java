package dev.sideproject.ndx.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageData<T> {
    String correlationDataId;
    T data;
    String exchangeKey;
    String routingKey;
}
