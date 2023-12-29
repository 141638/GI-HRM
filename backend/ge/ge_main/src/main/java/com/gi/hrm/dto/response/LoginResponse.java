package com.gi.hrm.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginResponse {
    private String username;
    private String token;
    private Long extAt;
    private List<String> roles;

    public LoginResponse(String username, String token, List<String> roles, Long extAt) {
        super();
        this.username = username;
        this.token = token;
        this.roles = roles;
        this.extAt = extAt;
    }

    public LoginResponse(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
