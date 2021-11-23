package com.tkis.qedbot.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkis.qedbot.entity.InconsistencyLogs;
import com.tkis.qedbot.repo.InconsistencyLogsRepo;

@Service
public class InconsistencyLogsServiceImpl implements InconsistencyLogsService {

	private static final Logger log = LoggerFactory.getLogger(InconsistencyLogsServiceImpl.class);
	
	@Autowired
	InconsistencyLogsRepo inconsistencyLogsRepo;
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Override
	public int getInconsistencyLogsTableCountByProjectId(int projectId) {
		int count = 0;
		try {
			count = inconsistencyLogsRepo.getInconsistencyLogsTableCountByProjectId(projectId);
		}catch(Exception e) {
			System.out.println("#### Exception InconsistencyLogsServiceImpl :: getInconsistencyLogsTableCountByProjectId : "+e);
		}
		return count;
	}
	
	@Override
	public int getInconsistencyLogsTableCount(int projectId) {
		int count = 0;
		try {
			count = inconsistencyLogsRepo.getInconsistencyLogsTableCount(projectId);
		}catch(Exception e) {
			System.out.println("#### Exception InconsistencyLogsServiceImpl :: getInconsistencyLogsTableCount : "+e);
		}
		return count;
	}

	//Commented on 19-11-2021
	/*
	@Override
	public int getLastResolvedCountByProjectId(int projectId) {
		
		int count = 0;
		try {
			count = inconsistencyLogsRepo.getLastResolvedCountByProjectId(projectId);
		}catch(Exception e) {
			System.out.println("#### Exception InconsistencyLogsServiceImpl :: getLastResolvedCountByProjectId : "+e);
		}
		return count;
	}
	 */
	@Override
	public String getProjectWiseReportData(int deliverableTypeId, int projectId, String userId)
	{
		String response = "", projectIdStr ="", projectName = "", initialCount = "", latestPendingInconsistency = "";
		int logTableProjectId = 0,batchId =0, latestInitialCount =0, latestSumOfResolvedCount;
		
		JSONObject mainJsonObject = new JSONObject();
		List<Object[]> projectWiseDataList = null;
		
		projectWiseDataList = inconsistencyLogsRepo.getPIdNameTotcalInconsistencyData(deliverableTypeId);
		
		if(projectWiseDataList != null && projectWiseDataList.size() >0) {
			
			for(Object [] data : projectWiseDataList) {
				
				logTableProjectId = (int)data[0];
				
				projectIdStr = projectIdStr+","+logTableProjectId;
				projectName = projectName+","+(String)data[1];
				initialCount = initialCount+","+(int)data[2];
				
				batchId = inconsistencyLogsRepo.getMaxBatchIdByProjectId(logTableProjectId);
				try {
					latestInitialCount = inconsistencyLogsRepo.findTopByProjectIdAndBatchId(logTableProjectId,batchId).getInitialCount();
					
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				latestSumOfResolvedCount = inconsistencyLogsRepo.getSumOfResolvedCountByProjectIdAndBatchId(logTableProjectId, batchId);
				
				latestPendingInconsistency = latestPendingInconsistency+","+(latestInitialCount-latestSumOfResolvedCount);
			}
			
			
			mainJsonObject.put("PROJECT_ID", projectIdStr.substring(1));
			mainJsonObject.put("PROJECT_NAME", projectName.substring(1));
			mainJsonObject.put("INITIAL_DATA", initialCount.substring(1));
			mainJsonObject.put("PENDING_DATA", latestPendingInconsistency.substring(1));
			
		}
		
		response = mainJsonObject.toString();
		System.out.println("#### getFieldWiseReportData "+response);
		return response;
	}
	
	@Override
	public String getProjectAndDateWiseReportData(int deliverableTypeId, String userId) {
		
		List<Object[]> projectList = null;
		List<Object[]> projectWiseLogData = null;
		List<Object[]> projectInconsistencyChkDateByDeliverable = null;
		
		int projectId = 0;
		String response = "", projectName = "";
		String projectInconChkDate = "";
		JSONObject mainJsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		JSONArray jsonLogDataArray = null;
		JSONObject jsonLogData = null;
		
		try {
			projectInconsistencyChkDateByDeliverable = inconsistencyLogsRepo.getAllProjectInconsistencyCheckDateByDeliverableType(deliverableTypeId);
			
			for(Object[] pIChkDate : projectInconsistencyChkDateByDeliverable){
				projectInconChkDate = projectInconChkDate+","+(java.util.Date) pIChkDate[0];
			}
			
			if(checkNull(projectInconChkDate).length() > 0) {
				
				projectInconChkDate = projectInconChkDate.substring(1);//remove initial comma.
				
				projectList = projectMasterService.getProjectIdAndName(deliverableTypeId);
				
				for(Object[] pL : projectList){
					projectId = (int) pL[0];
					projectWiseLogData = inconsistencyLogsRepo.getProjectAndDateWiseReportData(projectId);
					projectName = (String) pL[1];				
					System.out.println("#### projectId "+projectId);
					System.out.println("#### projectWiseLogData "+projectWiseLogData.size());
					if(projectWiseLogData.size() > 0) {
						
						jsonLogDataArray = new JSONArray();
						int initailCount = 0, resolvedCount =0;
						String projectData = "", dateOfEntry = "";
						for(Object[] logData :projectWiseLogData) {			
							
							dateOfEntry = dateOfEntry+","+(java.util.Date) logData[1];
							initailCount = (int) logData[2];
							resolvedCount = (int) logData[5];//
							projectData = projectData+","+resolvedCount;												
							
						}
						
						projectData = initailCount+","+projectData.substring(1);
						
						jsonObject = new JSONObject();				
						jsonObject.put("PROJECT_ID", projectId);
						jsonObject.put("PROJECT_NAME", projectName);
						jsonObject.put("DATE_OF_ENTRY", dateOfEntry.substring(1));
						jsonObject.put("PROJECT_DATA", projectData);
						
						jsonArray.add(jsonObject);
					}
					
					
				}
			}
			mainJsonObject.put("INCONSISTENCY_DATE", projectInconChkDate);
			mainJsonObject.put("INCONSISTENCY_DATEWISE_DATE", jsonArray);
			
			response = mainJsonObject.toString();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		System.out.println("#### getProjectAndDateWiseReportData :: response : "+response);
		return response;
	}
	
	private String checkNull(String input)
    {
		System.out.println("#### Input String ["+input+"]");
        if(input == null || "null".equalsIgnoreCase(input) || "undefined".equalsIgnoreCase(input))
        input = "";
        return input.trim();    
    }

	@Override
	public int getMaxBatchIdByProjectId(int projectId) {
		
		return inconsistencyLogsRepo.getMaxBatchIdByProjectId(projectId);
	}

	@Override
	public void saveInconsistencyLogsTableData(int projectId, int totolConsistency, int iterationBatchId) {
		
		InconsistencyLogs inconsistencyLogs = new InconsistencyLogs();
		inconsistencyLogs.setProjectId(projectId);
		inconsistencyLogs.setInitialCount(totolConsistency);
		inconsistencyLogs.setResolvedCount(0); // Check for this...
		inconsistencyLogs.setDateOfEntry(new Timestamp(new Date().getTime()));
		inconsistencyLogs.setBatchId(iterationBatchId);//Added on 19-11-2021
		inconsistencyLogsRepo.save(inconsistencyLogs);
	}

	@Override
	public InconsistencyLogs findTopByProjectIdAndBatchId(int projectId, int logTableBatchId) throws Exception {
		
		return inconsistencyLogsRepo.findTopByProjectIdAndBatchId(projectId, logTableBatchId);
	}

	@Override
	public int getSumOfResolvedCountByProjectIdAndBatchId(int projectId, int logTableBatchId) {
		
		return inconsistencyLogsRepo.getSumOfResolvedCountByProjectIdAndBatchId(projectId, logTableBatchId);
	}

}
