package com.tkis.qedbot.service;

import org.springframework.stereotype.Repository;

import com.tkis.qedbot.entity.InconsistencyLogs;

@Repository
public interface InconsistencyLogsService {

	int getInconsistencyLogsTableCountByProjectId(int projectId);
	
	int getInconsistencyLogsTableCount(int projectId);

	//int getLastResolvedCountByProjectId(int projectId);

	String getProjectAndDateWiseReportData(int deliverableTypeId, String userId);
	
	int getMaxBatchIdByProjectId(int projectId);

	void saveInconsistencyLogsTableData(int projectId, int totolConsistency, int iterationBatchId);

	InconsistencyLogs findTopByProjectIdAndBatchId(int projectId, int logTableBatchId) throws Exception;

	int getSumOfResolvedCountByProjectIdAndBatchId(int projectId, int logTableBatchId);

	String getProjectWiseReportData(int deliverableTypeId, int projectId, String userId);

	

}
