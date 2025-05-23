package com.krystalink.service.impl;

import com.krystalink.dto.AppointmentEditRequest;
import com.krystalink.dto.AppointmentRequest;
import com.krystalink.dto.AppointmentResponse;
import com.krystalink.exception.AppointmentNotFoundException;
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

import java.time.LocalDate;
import java.time.LocalTime;
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
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        log.debug("createAppointment() started... {}", request);

        Client client = getClientByPhoneNumber(request.getPhoneNumber());
        validateTimeRange(request);
        validateDuplicateTime(request.getDate(), request.getStartTime(), request.getEndTime());

        Appointment appointment = entityDtoMapper.toAppointment(request, client);
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

    @Override
    public AppointmentResponse updateAppointment(Long appointmentId, AppointmentEditRequest request) {
        log.debug("updateAppointment() started for appointmentId={} with {}", appointmentId, request);

        Appointment appointment = fetchAppointmentById(appointmentId);
        validateDuplicateTime(request.getDate(), request.getStartTime(), request.getEndTime());
        setAppointmentWithRequestInput(request, appointment);

        Appointment saved = appointmentRepository.save(appointment);
        return entityDtoMapper.toAppointmentResponse(saved);
    }

    private static void setAppointmentWithRequestInput(AppointmentEditRequest request, Appointment appointment) {
        if (request.getDate() != null) {
            appointment.setDate(request.getDate());
        }
        if (request.getStartTime() != null) {
            appointment.setStartTime(request.getStartTime());
        }
        if (request.getEndTime() != null) {
            appointment.setEndTime(request.getEndTime());
        }
        if (request.getServiceName() != null) {
            appointment.setServiceName(request.getServiceName());
        }
    }

    private Appointment fetchAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException(appointmentId));
    }


    private void validateDuplicateTime(LocalDate date, LocalTime startTime, LocalTime endTime) {
        appointmentRepository.findConflictingAppointments(date, startTime, endTime)
                .ifPresent(a -> {
                    throw new DuplicateAppointmentException();
                });
    }

    private void validateTimeRange(AppointmentRequest appointmentCreateRequest) {
        if (!appointmentCreateRequest.isValidTimeRange())
            throw new IllegalArgumentException("End time must be after start time");
    }

    private Client getClientByPhoneNumber(String phoneNumber) {
        return clientRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ClientNotFoundException(Collections.singletonList(phoneNumber)));
    }
}
