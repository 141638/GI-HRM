package com.gi.hrm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gi.hrm.entity.Projects;

@Repository
public interface ProjectRepository extends JpaRepository<Projects, Long> {

	List<Projects> findAllByDeleteFlagFalse();

}
