package com.tkis.qedbot.service;

import java.util.List;


public interface RuleMasterService 
{
	
	public String save(int projectId, int repositoryId, String shortDesc, String ruleType, String userId) throws Exception;
	
	public String updateRuleDesc(int rule, String shortDesc, String userId) throws Exception;
	
	public String updateRuleStatus(int rule, String status, String userId) throws Exception;
	
	//public List<Object[]> getRuleById(int projectId) throws Exception;
	
	public List<Object[]> getRuleById (int projectId, boolean isView) throws Exception;
	
	public String getJSONRuleById(int projectId,  boolean isView) throws Exception;

	public String executeRules(String ruleIdList);

	//public String getJSONRuleByIdForExecute(int projectId) throws Exception;
	
}
