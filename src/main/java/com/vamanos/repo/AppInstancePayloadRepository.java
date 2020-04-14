package com.vamanos.repo;

import com.vamanos.entity.AppInstancePayload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppInstancePayloadRepository extends JpaRepository<AppInstancePayload,Integer>{
	List<AppInstancePayload> getAppPayloadByAppId(int appId);
	List<AppInstancePayload> getAppPayloadByAppIdAndIsActiveVersion(int appId, boolean isActiveVersion);
	List<AppInstancePayload> getAppPayloadByAppIdAndVersionNumber(int appId, int versionNumber);
}
