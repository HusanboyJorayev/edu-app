package org.example.eduapp.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class Utils {

    public boolean checkPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.trim().isEmpty())
            return phoneNumber.matches("^[0-9]{9}$");
        else return false;
    }

    public boolean checkEmail(String email) {
        if (email == null || email.trim().isEmpty())
            return false;
        return email.contains("@");
    }
}
