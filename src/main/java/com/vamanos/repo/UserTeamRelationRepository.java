package com.vamanos.repo;

import com.vamanos.entity.Teams;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vamanos.entity.UserTeamRelation;

import java.util.List;

public interface UserTeamRelationRepository extends JpaRepository<UserTeamRelation, Integer>{
    List<UserTeamRelation> getUserTeamRelatiionByUserId(int userId);
}
