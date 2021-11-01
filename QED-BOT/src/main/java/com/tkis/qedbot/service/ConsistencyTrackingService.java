package com.tkis.qedbot.service;

import java.util.List;

import com.tkis.qedbot.entity.ConsistencyTracking;

public interface ConsistencyTrackingService {

	void saveConsistency(ConsistencyTracking consistencyTracking, int projectId, String userId) throws Exception;

	void saveConsistency(List<ConsistencyTracking> consistencyTracking);

	public String getJSONMappingDataByProjectId(int projectId, String userId, String filterKeyField) throws Exception;

	public String getConsistencyCountForAllProjects(String userId, String role);

	
}
