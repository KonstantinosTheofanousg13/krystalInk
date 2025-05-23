package com.krystalink.controller;

import com.krystalink.dto.AppointmentEditRequest;
import com.krystalink.dto.AppointmentRequest;
import com.krystalink.dto.AppointmentResponse;
import com.krystalink.service.AppointmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;


    @PostMapping("/create")
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody AppointmentRequest appointmentCreateRequest) {
        AppointmentResponse created = appointmentService.createAppointment(appointmentCreateRequest);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        List<AppointmentResponse> response = appointmentService.fetchAllAppointments();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/edit/{appointmentId}")
    public ResponseEntity<AppointmentResponse> editAppointment(@PathVariable @NotNull @Valid Long appointmentId, @Valid @RequestBody AppointmentEditRequest appointmentEditRequest) {
        AppointmentResponse updatedAppointment = appointmentService.updateAppointment(appointmentId, appointmentEditRequest);
        return ResponseEntity.ok(updatedAppointment);
    }

}
