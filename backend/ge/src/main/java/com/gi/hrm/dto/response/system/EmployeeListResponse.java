package com.gi.hrm.dto.response.system;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeListResponse {
	private Long id;
	private String imageUrl;
	private String employeeName;
	private String employeeEmail;
	private String employeeCode;
	private Integer employeeDepartment;
	private String dateOfBirth;
	private Integer insystemRole;
}
