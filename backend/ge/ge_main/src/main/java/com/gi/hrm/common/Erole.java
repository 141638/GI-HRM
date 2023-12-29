package com.gi.hrm.common;

public enum Erole {
    GUEST(0), EMPLOYEE(1), LEADER(2), PROJECT_MANAGER(3), RECRUITER(4), SOURCER(5), ACCOUNTANT(6), HR(7), DIRECTOR(8),
    STAFF(9);

    public int value;

    Erole(int value) {
        this.value = value;
    }

}
