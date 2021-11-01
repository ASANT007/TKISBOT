package com.tkis.qedbot.service;

import org.springframework.stereotype.Repository;

@Repository
public interface InconsistencyLogsService {

	int getInconsistencyLogsTableCountByProjectId(int projectId);
	
	int getInconsistencyLogsTableCount(int projectId);

	int getLastResolvedCountByProjectId(int projectId);

	

}
