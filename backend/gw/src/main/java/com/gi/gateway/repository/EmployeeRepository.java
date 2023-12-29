package com.gi.gateway.repository;

import java.util.Optional;

import com.gi.gateway.dto.response.common.UserDetailsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gi.gateway.entity.Employees;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Integer> {
	@Query(value = "SELECT password FROM employees WHERE delete_flag=false "
			+ "AND resign_date=false AND email=:username OR username=:username", nativeQuery = true)
	public String getPasswordByUsername(String username);

	public Optional<Employees> findEmployeeByEmailOrUsernameAndDeleteFlagFalseAndResignDateIsNull(String email,
			String username);

	@Query(value = "SELECT new com.gi.gateway.dto.response.common.UserDetailsDto(id, email, password, roleGroups) "
			+ "FROM Employees WHERE email =:userName AND deleteFlag=false AND resignDate IS NULL")
	Optional<UserDetailsDto> findToBuildUserDetails(String userName);
}
