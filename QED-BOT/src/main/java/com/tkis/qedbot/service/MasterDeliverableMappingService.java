package com.tkis.qedbot.service;

import java.util.List;

import org.json.simple.JSONObject;

public interface MasterDeliverableMappingService {
	
	public String saveMapping(int projectId, String masterTable, String masterField, String deliverableTable,	String deliverableField, String userId) throws Exception;

	public String getMasterDeliverableMapping(int projectId) throws Exception;

	public String deactiveMasterDeliverableMapping(String mappingIdList, String userId) throws Exception;

	public boolean isMappingPresent(int projectId, String masterTable, String masterField, String deliverableTable,	String deliverableField);

	//public String getJSONMappingDataByProjectId(int projectId, String userId) throws Exception;
}
