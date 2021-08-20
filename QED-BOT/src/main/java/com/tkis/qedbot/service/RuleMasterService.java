package com.tkis.qedbot.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface RuleMasterService {
	
	public String save(int projectId, int repositoryId, String shortDesc, String ruleType, String userId) throws Exception;
	
	public String updateRuleDesc(int rule, String shortDesc, String userId) throws Exception;
	
	public String updateRuleStatus(int rule, String status, String userId) throws Exception;
	
	public List<Object[]> getRuleById(int projectId) throws Exception;
	
	public String getJSONRuleById(int projectId) throws Exception;

	

}
