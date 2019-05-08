package com.nobbyknox.cibecs.commons.communications;

/**
 * Socket receive handler allows for custom handling of incoming messages
 *
 * @param <T> message type
 */
@FunctionalInterface
public interface ReceiveHandler<T> {
    void handle(T t) throws Exception;
}
