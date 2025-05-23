package com.krystalink.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClientWithAppointmentsResponse {
    private Long clientId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<AppointmentResponse> appointments;
}

