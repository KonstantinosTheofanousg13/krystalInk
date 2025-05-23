package com.krystalink.exception;

public class DuplicateClientException extends RuntimeException {
    public DuplicateClientException(String phoneNumber) {
        super("A client with phone number " + phoneNumber + " already exists.");
    }
}