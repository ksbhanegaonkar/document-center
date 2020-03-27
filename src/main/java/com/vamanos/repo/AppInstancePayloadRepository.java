package com.vamanos.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vamanos.entity.AppInstancePayload;

public interface AppInstancePayloadRepository extends JpaRepository<AppInstancePayload,Integer>{
	public AppInstancePayload getAppPayloadByAppId(int appId);
}
