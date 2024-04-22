package com.gi.hrm.database.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gi.hrm.database.entity.Departments;

public interface DepartmentRepository extends JpaRepository<Departments, Integer> {

	List<Departments> findAllByStatusAndDeleteFlagFalse(Integer status);

}
