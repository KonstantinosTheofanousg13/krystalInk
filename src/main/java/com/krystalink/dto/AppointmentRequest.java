package com.krystalink.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.krystalink.helper.ValidServiceName;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppointmentRequest {

    private Long id;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{8}$", message = "Phone number must be exactly 8 digits")
    private String phoneNumber;

    @NotNull(message = "Service name is required")
    @ValidServiceName
    private String serviceName;

    @NotNull(message = "Date is required")
    @FutureOrPresent(message = "Appointment date must be today or in the future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @NotNull(message = "Start time is required")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @AssertTrue(message = "End time must be after start time")
    public boolean isValidTimeRange() {
        return endTime != null && startTime != null && endTime.isAfter(startTime);
    }
}
