package com.gi.hrm.database.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gi.hrm.dto.response.DropdownResponse;
import com.gi.hrm.database.entity.Employees;

import jakarta.persistence.LockModeType;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Integer> {
	Optional<Employees> findEmployeeByEmailOrUsernameAndDeleteFlagAndResignDateIsNull(String email, String username,
	        Boolean deleteFlag);

	Optional<Employees> findByUsernameAndDeleteFlagFalse(String strip);

	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("SELECT emp.employeeCode FROM Employees emp "
	        + "WHERE emp.deleteFlag = FALSE AND emp.resignDate IS NULL ORDER BY emp.id DESC LIMIT 1")
	Optional<String> findLastestEmployeeCode();

	@Query("SELECT new com.gi.hrm.dto.response.DropdownResponse(emp.id, pro.fullName) FROM Employees emp "
	        + "JOIN Profiles pro ON pro.employeeId = emp.id AND pro.deleteFlag = FALSE "
	        + "WHERE emp.deleteFlag = FALSE AND emp.resignDate IS NULL ORDER BY emp.id ASC")
	List<DropdownResponse> findAllStaffDropdown();
}
