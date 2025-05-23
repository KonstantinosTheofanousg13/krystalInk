package com.krystalink.controller;

import com.krystalink.service.AppointmentService;
import com.krystalink.dto.AppointmentRequest;
import com.krystalink.dto.AppointmentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;


    @PostMapping("/create")
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody AppointmentRequest appointmentRequest) {
        AppointmentResponse created = appointmentService.createAppointment(appointmentRequest);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        List<AppointmentResponse> response = appointmentService.fetchAllAppointments();
        return ResponseEntity.ok(response);
    }

    //TODO::Create a PUT api to edit the appointment ( Service , Date , StartTime , EndTime )
    //TODO::Create a Delete api to delete an appointment.
    //TODO::Unit Tests for all the Scenarios


}
