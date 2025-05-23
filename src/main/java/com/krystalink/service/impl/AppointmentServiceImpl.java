package com.krystalink.service.impl;

import com.krystalink.dto.AppointmentRequest;
import com.krystalink.dto.AppointmentResponse;
import com.krystalink.exception.ClientNotFoundException;
import com.krystalink.exception.DuplicateAppointmentException;
import com.krystalink.model.Appointment;
import com.krystalink.model.Client;
import com.krystalink.repository.AppointmentRepository;
import com.krystalink.repository.ClientRepository;
import com.krystalink.service.AppointmentService;
import com.krystalink.util.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final EntityDtoMapper entityDtoMapper;

    @Override
    public AppointmentResponse createAppointment(AppointmentRequest appointmentRequest) {
        log.debug("createAppointment() started... {}", appointmentRequest);

        Client client = getClientByPhoneNumber(appointmentRequest.getPhoneNumber());
        validateTimeRange(appointmentRequest);
        validateDuplicateTime(appointmentRequest);

        Appointment appointment = entityDtoMapper.toAppointment(appointmentRequest, client);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        log.debug("createAppointment() completed... {}", savedAppointment);
        return entityDtoMapper.toAppointmentResponse(savedAppointment);
    }

    @Override
    public List<AppointmentResponse> fetchAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(entityDtoMapper::toAppointmentResponse)
                .toList();
    }

    private void validateDuplicateTime(AppointmentRequest appointmentRequest) {
        appointmentRepository.findConflictingAppointments(
                appointmentRequest.getDate(),
                appointmentRequest.getStartTime(),
                appointmentRequest.getEndTime()).ifPresent(a -> {
            throw new DuplicateAppointmentException(appointmentRequest);});
    }

    private void validateTimeRange(AppointmentRequest appointmentRequest) {
        if (!appointmentRequest.isValidTimeRange())
            throw new IllegalArgumentException("End time must be after start time");
    }

    private Client getClientByPhoneNumber(String phoneNumber) {
        return clientRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ClientNotFoundException(Collections.singletonList(phoneNumber)));
    }
}
