package com.krystalink.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.krystalink.helper.ValidServiceName;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Data
public class AppointmentEditRequest {
    @ValidServiceName
    private String serviceName;

    @FutureOrPresent(message = "Appointment date must be today or in the future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

}
