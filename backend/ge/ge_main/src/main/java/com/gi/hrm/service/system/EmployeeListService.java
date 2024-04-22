package com.gi.hrm.service.system;

import com.gi.hrm.database.entity.Employees;
import com.gi.hrm.database.entity.Profiles;
import com.gi.hrm.database.entity.RoleGroup;
import com.gi.hrm.database.entityManager.system.EmployeeListEM;
import com.gi.hrm.database.repository.EmployeeRepository;
import com.gi.hrm.database.repository.ProfileRepository;
import com.gi.hrm.database.repository.RoleGroupRepository;
import com.gi.hrm.dto.request.system.EmployeeAddRequest;
import com.gi.hrm.dto.request.system.EmployeeListSearchRequest;
import com.gi.hrm.dto.response.ApiResponse;
import com.gi.hrm.exception.RecordNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@AllArgsConstructor
public class EmployeeListService {
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
                String prefix = empCodeArr[0].trim();
                String postfix = empCodeArr[1].trim();
                postfix = String.valueOf("00" + (Integer.parseInt(postfix) + 1));
                postfix = postfix.substring(postfix.length() - 3);
                employeeCode = prefix + "-" + postfix;
            }
            RoleGroup roleGroup = roleGroupRepository.findById(request.getRole())
                    .orElseThrow(RecordNotFoundException::new);

            employee.setEmail(request.getEmail());
            employee.setEmployeeCode(employeeCode);
            employee.setUsername(request.getUsername());
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
