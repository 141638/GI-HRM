package com.gi.hrm.service.system;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gi.hrm.dto.request.system.EmployeeAddRequest;
import com.gi.hrm.dto.request.system.EmployeeListSearchRequest;
import com.gi.hrm.dto.response.ApiResponse;
import com.gi.hrm.entity.Employees;
import com.gi.hrm.entity.Profiles;
import com.gi.hrm.entity.RoleGroup;
import com.gi.hrm.entityManager.system.EmployeeListEM;
import com.gi.hrm.exception.RecordNotFoundException;
import com.gi.hrm.repository.EmployeeRepository;
import com.gi.hrm.repository.ProfileRepository;
import com.gi.hrm.repository.RoleGroupRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeListService {
	@Autowired
	private PasswordEncoder bCrypt;
	private final EmployeeListEM employeeListEM;
	private final RoleGroupRepository roleGroupRepository;
	private final EmployeeRepository employeeRepository;
	private final ProfileRepository profileRepository;

	public ApiResponse search(EmployeeListSearchRequest request) {
		return ApiResponse.apiResponseSuccess(employeeListEM.searchListEmployee(request));
	}

	public ApiResponse details(Integer id) {
		return ApiResponse.apiResponseSuccess("test gateway details success");
	}

	public ApiResponse delete(Integer id) {
		return ApiResponse.apiResponseSuccess("test gateway delete success");
	}

	@Transactional
	public ApiResponse add(EmployeeAddRequest request, Integer sessionUserId) {
		try {
			Employees employee = new Employees();
			Profiles profile = new Profiles();
			String employeeCode = employeeRepository.findLastestEmployeeCode().orElse(null);
			if (employeeCode == null) {
				employeeCode = "GI-001";
			} else {
				String[] empCodeArr = employeeCode.split("-");
				String prefix = empCodeArr[0].strip();
				String postfix = empCodeArr[1].strip();
				postfix = String.valueOf("00" + (Integer.parseInt(postfix) + 1));
				postfix = postfix.substring(postfix.length() - 3);
				employeeCode = prefix + "-" + postfix;
			}
			RoleGroup roleGroup = roleGroupRepository.findById(request.getRole())
			        .orElseThrow(RecordNotFoundException::new);

			employee.setEmail(request.getEmail());
			employee.setEmployeeCode(employeeCode);
			employee.setUsername(request.getUsername());
			employee.setPassword(bCrypt.encode(request.getPassword()));
			employee.setDepartmentId(request.getDepartment());
			employee.setRoleGroups(roleGroup);
			employee.setDepartmentId(request.getDepartment());
			employee.setCommonRegist(sessionUserId);
			employee = employeeRepository.save(employee);

			profile.setFullName(request.getFullName());
			profile.setDateOfBirth(new Timestamp(request.getDateOfBirth()));
			profile.setPhoneNumber(request.getPhoneNumber());
			profile.setGender(request.getGender());
			profile.setAddress(request.getAddress());
			profile.setDescription(request.getDescription());
			profile.setEmployeeId(employee.getId());
			profile.setCommonRegist(sessionUserId);

			profileRepository.save(profile);
		} finally {

		}

		return ApiResponse.apiResponseSuccess("add employee success");
	}
}
