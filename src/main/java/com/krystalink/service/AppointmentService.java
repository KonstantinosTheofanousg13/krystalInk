package com.krystalink.service;

import com.krystalink.dto.AppointmentRequest;
import com.krystalink.dto.AppointmentResponse;

import java.util.List;

public interface AppointmentService {

    AppointmentResponse createAppointment(AppointmentRequest appointmentRequest);

    List<AppointmentResponse> fetchAllAppointments();
}
