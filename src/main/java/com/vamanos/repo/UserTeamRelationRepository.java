package com.vamanos.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vamanos.entity.UserTeamRelation;

public interface UserTeamRelationRepository extends JpaRepository<UserTeamRelation, Integer>{

}
