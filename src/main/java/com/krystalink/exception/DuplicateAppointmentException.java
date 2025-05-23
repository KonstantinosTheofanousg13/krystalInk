package com.krystalink.exception;

public class DuplicateAppointmentException extends RuntimeException{
    public DuplicateAppointmentException() {
        super("An appointment is already exists in this date and time");
    }
}
