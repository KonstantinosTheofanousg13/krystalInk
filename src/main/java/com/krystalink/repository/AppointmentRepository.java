package com.krystalink.repository;

import com.krystalink.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {


    @Query("SELECT a FROM Appointment a WHERE a.date = :date AND a.startTime < :endTime AND a.endTime > :startTime")
    Optional<Appointment> findConflictingAppointments(LocalDate date, LocalTime startTime, LocalTime endTime);
}
