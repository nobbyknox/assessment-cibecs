package com.nobbyknox.cibecs.commons.communications;

/**
 * Socket receive handler allows for custom handling of incoming messages
 *
 * @param <T> any supported message type. Currently on {@link Message} interface
 *           is supported.
 */
@FunctionalInterface
public interface ReceiveHandler<T> {
    void handle(T t) throws Exception;
}
