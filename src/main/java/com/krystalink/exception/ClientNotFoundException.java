package com.krystalink.exception;

import java.util.List;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(List<String> phoneNumber) {
        super("A client with phone number " + phoneNumber + " not exists.");
    }
}
