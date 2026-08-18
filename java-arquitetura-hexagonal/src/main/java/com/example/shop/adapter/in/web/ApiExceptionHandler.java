package com.example.shop.adapter.in.web;

import com.example.shop.application.service.PaymentDeclinedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Translates core failures into HTTP - another adapter responsibility.
 */
@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(PaymentDeclinedException.class)
    ResponseEntity<Map<String, Object>> declined(PaymentDeclinedException e) {
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(error(e));
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    ResponseEntity<Map<String, Object>> badRequest(RuntimeException e) {
        return ResponseEntity.badRequest().body(error(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, Object>> invalidPayload(MethodArgumentNotValidException e) {
        Map<String, String> fields = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(err -> fields.putIfAbsent(err.getField(), err.getDefaultMessage()));
        List<String> global = e.getBindingResult().getGlobalErrors().stream()
                .map(ObjectError::getDefaultMessage).toList();

        Map<String, Object> bodyRetorno = new LinkedHashMap<>();
        bodyRetorno.put("error", "validation failed");

        if (Objects.nonNull(fields) && !fields.isEmpty()) {
            bodyRetorno.put("fields", fields);
        }

        if (Objects.nonNull(global) && !global.isEmpty()) {
            bodyRetorno.put("globalErrors", global);
        }

        return ResponseEntity.badRequest().body(bodyRetorno);
    }

    /**
     * Message is never guaranteed to be set, and Map.of rejects null values.
     */
    private static Map<String, Object> error(RuntimeException e) {
        String message = e.getMessage();
        return Map.of("error", message != null ? message : e.getClass().getSimpleName());
    }
}
