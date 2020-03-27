package com.vamanos.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vamanos.entity.GlobalApps;

public interface GlobalAppsRepository extends JpaRepository<GlobalApps,Integer>{
	public boolean existsByAppId(int appId);
}
