package com.gi.gateway.dto.request.employee;

import java.util.Date;

import com.gi.gateway.dto.request.common.CommonPaginatorRequest;

import lombok.Getter;

@Getter
public class EmployeeListSearchRequest extends CommonPaginatorRequest {
	private String name;
	private String email;
	private Integer insystemRole;
	private Integer department;
	private String employeeCode;
	private Date dateOfBirth;
	private String generalKeys;
}
