package com.gi.hrm.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {
    /** user ID */
    private String id;
    /** username */
    private String username;
    /** password */
    private String password;
}
