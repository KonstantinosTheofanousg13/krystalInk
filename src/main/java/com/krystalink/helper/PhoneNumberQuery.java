package com.krystalink.helper;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PhoneNumberQuery {

    @Size(max = 8, message = "Phone number must be exactly 8 digits")
    private List<@Pattern(regexp = "^[0-9]{8}$", message = "Phone number must be exactly 8 digits.") String> phoneNumber;
}
