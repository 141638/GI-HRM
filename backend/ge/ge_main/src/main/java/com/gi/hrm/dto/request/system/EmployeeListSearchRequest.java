package com.gi.hrm.dto.request.system;

import java.util.Date;

import dto.request.CommonPaginatorRequest;

import lombok.Getter;

@Getter
public class EmployeeListSearchRequest extends CommonPaginatorRequest {
	private String name;
	private String email;
	private Integer department;
	private String employeeCode;
	private Date dateOfBirth;
	private String generalKeys;
}
