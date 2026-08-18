package com.example.shop.adapter.in.messaging;

/**
 * A message that will never succeed, however often it is redelivered:
 * unreadable JSON or a payload that violates the wire contract.
 */
class InvalidOrderMessageException extends RuntimeException {

    InvalidOrderMessageException(String message) {
        super(message);
    }

    InvalidOrderMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
