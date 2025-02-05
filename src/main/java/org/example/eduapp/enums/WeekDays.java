package org.example.eduapp.enums;

import lombok.Getter;

@Getter
public enum WeekDays {
    MONDAY("DUSHANBA"),
    TUESDAY("SESHANBA"),
    WEDNESDAY("CHORSHANBA"),
    THURSDAY("PAYSHANBA"),
    FRIDAY("JUMA"),
    SATURDAY("SHANBA"),
    SUNDAY("YAKSHANBA");

    private final String description;


    WeekDays(String description) {
        this.description = description;
    }
}
