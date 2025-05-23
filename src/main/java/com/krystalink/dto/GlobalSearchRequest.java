package com.krystalink.dto;


import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class GlobalSearchRequest {

    @Pattern(regexp = "^[0-9]{8}$", message = "Phone number must be exactly 8 digits")
    private String phoneNumber;

    @Pattern(regexp = "^[a-zA-Z]+(\\s+[a-zA-Z]+)*$", message = "Full name must contain only alphabetic characters and spaces")
    private String fullName;

    private String serviceName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate appointmentDate;
}
