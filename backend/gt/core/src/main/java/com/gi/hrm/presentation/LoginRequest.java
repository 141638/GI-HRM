package com.gi.hrm.presentation;

import lombok.Data;

@Data
public class LoginRequest {
    /** email */
    private String email;
    /** password */
    private String password;
}
