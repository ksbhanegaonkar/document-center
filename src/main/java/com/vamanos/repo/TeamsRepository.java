package com.vamanos.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vamanos.entity.Teams;

import java.util.List;

public interface TeamsRepository extends JpaRepository<Teams, Integer>{

}
