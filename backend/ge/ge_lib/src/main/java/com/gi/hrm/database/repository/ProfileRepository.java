package com.gi.hrm.database.repository;

import com.gi.hrm.database.entity.Profiles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profiles, Integer> {

}
