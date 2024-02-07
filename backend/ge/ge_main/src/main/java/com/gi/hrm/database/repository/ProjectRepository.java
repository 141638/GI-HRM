package com.gi.hrm.database.repository;

import java.util.List;

import com.gi.hrm.database.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Projects, Long> {

	List<Projects> findAllByDeleteFlagFalse();

}
