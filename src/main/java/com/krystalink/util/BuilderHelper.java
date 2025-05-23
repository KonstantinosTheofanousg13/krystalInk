package com.krystalink.util;

import com.krystalink.dto.GlobalSearchRequest;
import com.krystalink.model.Appointment;
import com.krystalink.model.Client;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.krystalink.constants.Constants.*;

@Component
@AllArgsConstructor
@Getter
public class BuilderHelper {

    public record NameParts(String firstName, String lastName) {
    }

    private final Validator validator;


    public ExampleMatcher buildClientMatcher() {
        return ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true)
                .withIgnoreNullValues()
                .withMatcher(PHONE_NUMBER, ExampleMatcher.GenericPropertyMatcher::exact)
                .withMatcher(FIRST_NAME, ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher(LAST_NAME, ExampleMatcher.GenericPropertyMatcher::contains);
    }

    public ExampleMatcher buildAppointmentMatcher() {
        return ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true)
                .withIgnoreNullValues()
                .withMatcher(SERVICE_NAME, ExampleMatcher.GenericPropertyMatcher::exact)
                .withMatcher(APPOINTMENT_DATE, ExampleMatcher.GenericPropertyMatcher::contains);
    }


    public Client buildClientFromRequest(GlobalSearchRequest request) {
        Client.ClientBuilder builder = Client.builder();

        if (request.getFullName() != null && !request.getFullName().isBlank()) {
            NameParts nameParts = parseFullName(request.getFullName());
            builder.firstName(nameParts.firstName());
            builder.lastName(nameParts.lastName());
        }

        if (request.getPhoneNumber() != null && !request.getPhoneNumber().isBlank())
            builder.phoneNumber(request.getPhoneNumber());

        return builder.build();
    }

    public Appointment buildAppointmentFromRequest(GlobalSearchRequest request) {
        Appointment.AppointmentBuilder builder = Appointment.builder();

        if (request.getServiceName() != null && !request.getServiceName().isBlank())
            builder.serviceName(request.getServiceName());

        if (request.getAppointmentDate() != null)
            builder.date(request.getAppointmentDate());

        return builder.build();
    }


    public NameParts parseFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty())
            return new NameParts(null, null);

        String[] nameParts = fullName.trim().split("\\s+");
        String firstName = nameParts[0];
        String lastName = (nameParts.length > 1)
                ? String.join(" ", Arrays.copyOfRange(nameParts, 1, nameParts.length))
                : null;

        return new NameParts(firstName, lastName);
    }

}
