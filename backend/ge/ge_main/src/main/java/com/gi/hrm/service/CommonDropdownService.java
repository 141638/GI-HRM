package com.gi.hrm.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dto.response.ApiResponse;
import dto.response.GenericDropdownResponse;
import com.gi.hrm.database.entity.Departments;
import com.gi.hrm.database.entity.Projects;
import com.gi.hrm.database.repository.DepartmentRepository;
import com.gi.hrm.database.repository.EmployeeRepository;
import com.gi.hrm.database.repository.ProjectRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommonDropdownService {
	private final DepartmentRepository departmentRepository;
	private final ProjectRepository projectRepository;
	private final EmployeeRepository employeeRepository;

	public ApiResponse dropdownAllDepartment() {
		List<Departments> departments = departmentRepository.findAllByStatusAndDeleteFlagFalse(0);
		List<GenericDropdownResponse<Integer, String>> mapToDropdown = departments.stream()
		        .map(item -> new GenericDropdownResponse<Integer, String>(item.getId(), item.getName())).collect(Collectors.toList());
		return ApiResponse.apiResponseSuccess(mapToDropdown);
	}

	public ApiResponse dropdownAllProject() {
		List<Projects> project = projectRepository.findAllByDeleteFlagFalse();
		List<GenericDropdownResponse<Integer, String>> mapToDropdown = project.stream()
		        .map(item -> new GenericDropdownResponse<Integer, String>(item.getId(), item.getName())).collect(Collectors.toList());
		return ApiResponse.apiResponseSuccess(mapToDropdown);
	}

	public ApiResponse dropdownAllStaff() {
		return ApiResponse.apiResponseSuccess(employeeRepository.findAllStaffDropdown());
	}
}
