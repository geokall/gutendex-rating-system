package com.morotech.javachallenge.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MoroResponseError {

    private String message;
    private LocalDateTime timestamp;

    public MoroResponseError(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
