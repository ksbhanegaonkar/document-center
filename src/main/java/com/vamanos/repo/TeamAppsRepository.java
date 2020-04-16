package com.vamanos.repo;

import com.vamanos.entity.PersonalApps;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vamanos.entity.TeamApps;

import java.util.List;

public interface TeamAppsRepository extends JpaRepository<TeamApps, Integer>{
    List<TeamApps> findTeamAppsByTeamId(List<Integer> teamId);
}
