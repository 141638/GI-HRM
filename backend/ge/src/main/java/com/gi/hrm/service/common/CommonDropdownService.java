package com.gi.hrm.service.common;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gi.hrm.dto.response.ApiResponse;
import com.gi.hrm.dto.response.GenericDropdownResponse;
import com.gi.hrm.entity.Departments;
import com.gi.hrm.entity.Projects;
import com.gi.hrm.repository.DepartmentRepository;
import com.gi.hrm.repository.EmployeeRepository;
import com.gi.hrm.repository.ProjectRepository;

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
		        .map(item -> new GenericDropdownResponse<Integer, String>(item.getId(), item.getName())).toList();
		return ApiResponse.apiResponseSuccess(mapToDropdown);
	}

	public ApiResponse dropdownAllProject() {
		List<Projects> project = projectRepository.findAllByDeleteFlagFalse();
		List<GenericDropdownResponse<Integer, String>> mapToDropdown = project.stream()
		        .map(item -> new GenericDropdownResponse<Integer, String>(item.getId(), item.getName())).toList();
		return ApiResponse.apiResponseSuccess(mapToDropdown);
	}

	public ApiResponse dropdownAllStaff() {
		return ApiResponse.apiResponseSuccess(employeeRepository.findAllStaffDropdown());
	}
}
