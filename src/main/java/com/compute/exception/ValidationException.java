package com.compute.exception;

public class ValidationException extends RuntimeException{

	private static final long serialVersionUID = 5747090596855981720L;

    public ValidationException(String message) {
        super(message);
    }
}
