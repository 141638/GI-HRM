package com.gi.gateway.dto.request.employee;

import java.io.Serializable;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EmployeeAddRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String fullName;
	private String email;
	private Long dateOfBirth;
	private Integer systemRole;
	private String username;
	private String password;
	private Integer department;
	private Integer role;
	private String description;
	private String phoneNumber;
	private Integer gender;
	private String address;
}
