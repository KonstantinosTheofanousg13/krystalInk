package com.krystalink.util;

import com.krystalink.constants.ServiceEnum;
import com.krystalink.dto.*;
import com.krystalink.model.Appointment;
import com.krystalink.model.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Getter
public class EntityDtoMapper {

    public Appointment toAppointment(AppointmentRequest request, Client client) {
        return Appointment.builder()
                .client(client)
                .serviceName(request.getServiceName())
                .date(request.getDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();
    }

    public AppointmentResponse toAppointmentResponse(Appointment appointment) {
        ServiceEnum serviceEnum = Optional.ofNullable(appointment.getServiceName())
                .filter(name -> !name.isBlank())
                .map(ServiceEnum::valueOf)
                .orElse(null);

        return AppointmentResponse.builder()
                .appointmentId(appointment.getId())
                .serviceName(serviceEnum)
                .date(appointment.getDate())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .build();
    }

    public Client toClient(ClientRequest request) {
        return Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    public ClientResponse toClientResponse(Client client) {
        return ClientResponse.builder()
                .clientId(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }

    public ClientWithAppointmentsResponse toClientWithAppointments(Client client) {
        return ClientWithAppointmentsResponse.builder()
                .clientId(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .phoneNumber(client.getPhoneNumber())
                .appointments(mapAppointments(client.getAppointments()))
                .build();
    }

    public ClientWithAppointmentsResponse toClientWithAppointments(Client client, List<Appointment> appointments) {
        List<AppointmentResponse> appointmentResponses = mapAppointments(appointments);
        return ClientWithAppointmentsResponse.builder()
                .clientId(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .phoneNumber(client.getPhoneNumber())
                .appointments(appointmentResponses)
                .build();
    }

    private List<AppointmentResponse> mapAppointments(List<Appointment> appointments) {
        if (appointments == null)
            return List.of();

        return appointments.stream()
                .map(this::toAppointmentResponse)
                .collect(Collectors.toList());
    }

}
