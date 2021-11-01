package com.tkis.qedbot.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkis.qedbot.dao.CustomTableDao;
import com.tkis.qedbot.entity.RuleMaster;
import com.tkis.qedbot.repo.RepositoryDetailsRepo;
import com.tkis.qedbot.repo.RuleMasterRepo;

@Service
public class RuleMasterServiceImpl implements RuleMasterService 
{
	@Autowired
	private CustomTableDao customTableDao;
	
	@Autowired
	RepositoryDetailsRepo repositoryDetailsRepo;
	
	@Autowired
	private RuleMasterRepo ruleMasterRepo;
	
	@Autowired
	private RuleExecutionService ruleExecutionService;
	
	private static final Logger log = LoggerFactory.getLogger(RuleMasterServiceImpl.class);

	@PersistenceContext
	private EntityManager entityManager;
	
	//RuleMaster ruleMaster = null;
	@Override
	public String save(int projectId, int repositoryId, String shortDesc, String ruleType,String userId)  throws Exception
	{
		String response = "";		
		
		RuleMaster ruleMaster = new RuleMaster();		
		
		ruleMaster.setRepositoryId(repositoryId);
		ruleMaster.setRuleType(ruleType);
		ruleMaster.setRuleDesc(shortDesc);
		ruleMaster.setExecutionSequence(0);
		ruleMaster.setStatus("Active");
		ruleMaster.setCreatedBy(userId);//get It from session or send via method call
		ruleMaster.setCreationDate(new Timestamp(new Date().getTime()));		
		
		if(ruleMasterRepo.save(ruleMaster) != null) {
			response ="sucess";
		}
		
		return response;
	}
	
	
	/*
	 * @Override public List<Object[]> getRuleById(int projectId) throws Exception {
	 * 
	 * return ruleMasterRepo.getRuleById(projectId);
	 * 
	 * }
	 */
	  
	/*
	 * @Override public String getJSONRuleById(int projectId) throws Exception {
	 * 
	 * List<Object[]> ruleList = ruleMasterRepo.getRuleById(projectId);
	 * 
	 * JSONArray arr = new JSONArray(); JSONObject obj = null;
	 * 
	 * for(Object[] rules : ruleList) { int Id = 0, repositoryId = 0; String status
	 * = "", desc = "", ruleType = "";
	 * 
	 * Id = (Integer) rules[0]; repositoryId = (Integer) rules[1]; desc = (String)
	 * rules[2]; ruleType = (String) rules[3]; status = (String) rules[4];
	 * 
	 * obj = new JSONObject(); obj.put("ruleId",Id);
	 * obj.put("repositoryId",repositoryId); obj.put("ruleDesc",desc);
	 * obj.put("ruleType",ruleType); obj.put("status",status);
	 * 
	 * arr.add(obj); } return arr.toString(); }
	 */
	 
	
	@Override
	public List<Object[]> getRuleById(int projectId, boolean isView) throws Exception {
		
		List<Object[]> ruletList = null;
		String sql = "";
		try {
				if(isView) {
					
				sql = "select rm.rule_id, rm.repository_id, rm.rule_desc, rm.rule_type, rm.status from rules_master rm,repository_details rd where rd.repository_id = rm.repository_id and rd.project_id = "+projectId+"";
					
				}else {
					sql = "select rm.rule_id, rm.repository_id, rm.rule_desc, rm.rule_type, rm.status from rules_master rm,repository_details rd where rd.repository_id = rm.repository_id and rd.project_id = "+projectId+" and rm.status='Active'";
				}
				

				Session session = null;
				if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
					throw new NullPointerException();
				}
				ruletList= session.createNativeQuery(sql).getResultList();
				
				
			} catch (Exception e) {

			log.error("exception in getRuleById():", e);
			return null;
		}
		return ruletList;
	}
	
	@Override
	public String getJSONRuleById(int projectId, boolean isView) throws Exception {
		
		List<Object[]> ruleList = getRuleById(projectId, isView );
		
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
	
	/*
	 * @Override public String getJSONRuleByIdForExecute(int projectId) throws
	 * Exception {
	 * 
	 * List<Object[]> ruleList = ruleMasterRepo.getRuleByIdForExecute(projectId);
	 * 
	 * JSONArray arr = new JSONArray(); JSONObject obj = null;
	 * 
	 * for(Object[] rules : ruleList) { int Id = 0, repositoryId = 0; String status
	 * = "", desc = "", ruleType = "";
	 * 
	 * Id = (Integer) rules[0]; repositoryId = (Integer) rules[1]; desc = (String)
	 * rules[2]; ruleType = (String) rules[3]; status = (String) rules[4];
	 * 
	 * obj = new JSONObject(); obj.put("ruleId",Id);
	 * obj.put("repositoryId",repositoryId); obj.put("ruleDesc",desc);
	 * obj.put("ruleType",ruleType); obj.put("status",status);
	 * 
	 * arr.add(obj); } return arr.toString(); }
	 */
	
		@Override
		public String executeRules(String ruleIdList) {
			
			JSONArray arr = new JSONArray();
			JSONObject obj = null;
			
			String ruleIdListArray [] = ruleIdList.split(",");
			for(String ruleId : ruleIdListArray) 
			{
				String ruleDesc = "";
				int Id = 0;
				int row = 0;
				try 
				{
					Id = Integer.valueOf(ruleId);
					obj = new JSONObject();
		            obj.put("ruleId",Id);
					if(isTableNotEmpty(Id)) {
						
						ruleDesc = ruleMasterRepo.getRuleDescById(Id);
						System.out.println("#### ruleDesc "+ruleDesc);
						row = ruleExecutionService.executeRule(ruleDesc);
					}else {
						obj.put("message","No Data in Table");
					}
					
					System.out.println("#### row"+row);
					
				} catch (Exception e) {					
					
					obj.put("message",e.toString());
					System.out.println("#### Exception :: ruleId"+ruleId+" Error : "+e.toString());
				}
				
				arr.add(obj);
			}
			
			return arr.toString();
			
			
		}
		
		private boolean isTableNotEmpty(int id) throws Exception {
			
			int repositoryId = 0;
			String tableName = "";
			repositoryId = ruleMasterRepo.getRepoIdById(id);
			tableName = repositoryDetailsRepo.getTableNameFromRepositoryId(repositoryId);
			if(customTableDao.getRowCount(tableName) > 0) {
				
				return true;
			}
			
			return false;
		}
		


}
