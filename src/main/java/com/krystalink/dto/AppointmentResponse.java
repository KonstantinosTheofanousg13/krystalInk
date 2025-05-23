package com.krystalink.dto;

import com.krystalink.constants.ServiceEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponse {
    private Long appointmentId;
    private ServiceEnum serviceName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
