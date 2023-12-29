package com.gi.hrm.dto.response;

import com.gi.hrm.entity.RoleGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {
    private Integer id;
    private String username;
    private String password;
    private RoleGroup roles;
}
