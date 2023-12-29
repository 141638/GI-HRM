package com.gi.hrm.dto.request.system;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
