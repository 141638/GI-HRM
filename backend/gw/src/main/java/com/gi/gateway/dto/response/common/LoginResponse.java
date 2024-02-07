package com.gi.gateway.dto.response.common;

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
    private Integer id;
    private String username;
    private String token;
    private Long extAt;
    private List<String> roles;

    public LoginResponse(Integer id, String username, String token, List<String> roles, Long extAt) {
        super();
        this.id = id;
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
