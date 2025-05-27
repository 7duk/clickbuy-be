package dev.sideproject.ndx2.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class MessageData<T> {
    String correlationDataId;
    T data;
    String exchangeKey;
    String routingKey;
}
