package com.krystalink.exception;

import com.krystalink.dto.AppointmentRequest;

public class DuplicateAppointmentException extends RuntimeException{
    public DuplicateAppointmentException(AppointmentRequest appointmentRequest) {
        super("An appointment is already exists in this date and time: "
                + appointmentRequest.getDate() + " "
                + appointmentRequest.getStartTime() + " "
                + appointmentRequest.getEndTime());
    }
}
