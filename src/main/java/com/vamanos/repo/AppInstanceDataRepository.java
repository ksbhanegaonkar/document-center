package com.vamanos.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vamanos.entity.AppInstanceData;

@Repository
@Transactional
public interface AppInstanceDataRepository extends JpaRepository<AppInstanceData,Integer>{
	public AppInstanceData getAppById(int id);

	
}
