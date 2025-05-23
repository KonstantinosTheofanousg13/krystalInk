package com.krystalink.exception;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(Long id) {
      super("An appointment with id " + id + " not exists.");
    }
}
