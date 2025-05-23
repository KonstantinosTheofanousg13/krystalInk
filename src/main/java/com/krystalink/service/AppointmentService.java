package com.krystalink.service;

import com.krystalink.dto.AppointmentEditRequest;
import com.krystalink.dto.AppointmentRequest;
import com.krystalink.dto.AppointmentResponse;
import com.krystalink.exception.DuplicateAppointmentException;

import java.util.List;

public interface AppointmentService {

    AppointmentResponse createAppointment(AppointmentRequest appointmentCreateRequest);

    List<AppointmentResponse> fetchAllAppointments();

    AppointmentResponse updateAppointment(Long appointmentId, AppointmentEditRequest request) throws DuplicateAppointmentException;

}
