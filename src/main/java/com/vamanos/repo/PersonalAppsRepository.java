package com.vamanos.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vamanos.entity.PersonalApps;

import java.util.List;

public interface PersonalAppsRepository extends JpaRepository<PersonalApps, Integer>{
        List<PersonalApps> getPersonalAppsByUserId(int userId);
        boolean existsByUserId(int userId);
}
