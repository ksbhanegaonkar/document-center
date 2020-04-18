package com.vamanos.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vamanos.entity.Users;

import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users, Integer> {

	Users findByUsername(String username);

}


