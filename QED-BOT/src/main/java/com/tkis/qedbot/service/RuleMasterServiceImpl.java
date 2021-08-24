package com.tkis.qedbot.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tkis.qedbot.entity.RuleMaster;
import com.tkis.qedbot.repo.RuleMasterRepo;


@Component
public class RuleMasterServiceImpl implements RuleMasterService 
{
	
	@Autowired
	RuleMasterRepo ruleMasterRepo;
	
	@Autowired
	RuleExecutionService ruleExecutionService;
	
	//RuleMaster ruleMaster = null;
	@Override
	public String save(int projectId, int repositoryId, String shortDesc, String ruleType,String userId)  throws Exception
	{
		String response = "";		
		
		RuleMaster ruleMaster = new RuleMaster();
		
		ruleMaster.setProjectId(projectId);
		ruleMaster.setRepositoryId(repositoryId);
		ruleMaster.setRuleType(ruleType);
		ruleMaster.setRuleDesc(shortDesc);
		ruleMaster.setExecutionSequence(0);
		ruleMaster.setStatus("active");
		ruleMaster.setCreatedBy(userId);//get It from session or send via method call
		ruleMaster.setCreationDate(new Timestamp(new Date().getTime()));		
		
		if(ruleMasterRepo.save(ruleMaster) != null) {
			response ="sucess";
		}
		
		return response;
	}
	@Override
	public List<Object[]> getRuleById(int projectId) throws Exception {
		
		return ruleMasterRepo.getRuleById(projectId);
	}
	
	@Override
	public String getJSONRuleById(int projectId) throws Exception {
		
		List<Object[]> ruleList = ruleMasterRepo.getRuleById(projectId);
		
		JSONArray arr = new JSONArray();
		JSONObject obj = null;
		
		for(Object[] rules : ruleList) {
			int Id = 0, repositoryId = 0;
			String status = "",	desc = "", ruleType = "";
			
			Id = (Integer) rules[0];
			repositoryId = (Integer) rules[1];
			desc = (String) rules[2];
			ruleType = (String) rules[3];
			status = (String) rules[4];
			/*
			 * System.out.println("id "+Id); System.out.println("status "+status);
			 * System.out.println("desc "+desc);
			 */
			obj = new JSONObject();
            obj.put("ruleId",Id);
            obj.put("repositoryId",repositoryId);
            obj.put("ruleDesc",desc);
            obj.put("ruleType",ruleType);
            obj.put("status",status);
            
            arr.add(obj);
		}
		return arr.toString();
	}
	@Override
	public String updateRuleDesc(int rule, String shortDesc, String userId) throws Exception 
	{
		String response = "";

		Optional<RuleMaster> optinal = ruleMasterRepo.findById(rule);
		RuleMaster ruleMaster =  optinal.get();
		ruleMaster.setRuleDesc(shortDesc);
		ruleMaster.setLastUpdatedBy(userId);
		ruleMaster.setLastUpdationDate(new Timestamp(new Date().getTime()));
		
		if(ruleMasterRepo.save(ruleMaster) != null) {
			
			response ="sucess";
		}
		return response;
	}
	@Override
	public String updateRuleStatus(int rule, String status, String userId) throws Exception {
		
		String response = "";

		Optional<RuleMaster> optinal = ruleMasterRepo.findById(rule);
		RuleMaster ruleMaster =  optinal.get();
		ruleMaster.setStatus(status);
		ruleMaster.setLastUpdatedBy(userId);
		ruleMaster.setLastUpdationDate(new Timestamp(new Date().getTime()));
		
		if(ruleMasterRepo.save(ruleMaster) != null) {
			
			response ="sucess";
		}
		
		return response;
	}
	@Override
	public String executeRules(String ruleIdList) {
		
		JSONArray arr = new JSONArray();
		JSONObject obj = null;
		
		String ruleIdListArray [] = ruleIdList.split(",");
		for(String ruleId : ruleIdListArray) 
		{
			String ruleDesc = "";
			int Id = 0;
			try 
			{
				Id = Integer.valueOf(ruleId);
				
				ruleDesc = ruleMasterRepo.getRuleDescById(Id);				
				System.out.println("#### ruleDesc "+ruleDesc);
				
				obj = new JSONObject();
	            obj.put("ruleId",Id);
				
				int row = ruleExecutionService.executeRule(ruleDesc);
				
				System.out.println("#### row"+row);
				
			} catch (Exception e) {
				
				//e.printStackTrace();
				obj.put("message",e.toString());
				System.out.println("#### Exception :: ruleId"+ruleId+" Error : "+e.toString());
			}
			
			arr.add(obj);
		}
		
		return arr.toString();
		
		
	}
	
	

}
