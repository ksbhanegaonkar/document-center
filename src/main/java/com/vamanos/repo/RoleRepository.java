package com.vamanos.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vamanos.entity.Roles;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Roles, Integer> {

	Roles findByName(String username);

}
