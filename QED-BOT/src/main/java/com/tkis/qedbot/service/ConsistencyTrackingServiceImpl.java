package com.tkis.qedbot.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;


import com.tkis.qedbot.entity.ConsistencyTracking;
import com.tkis.qedbot.entity.InconsistencyLogs;
import com.tkis.qedbot.repo.ConsistencyTrackingRepo;
import com.tkis.qedbot.repo.InconsistencyLogsRepo;
import com.tkis.qedbot.repo.MasterDeliverableMappingRepo;

@Service
public class ConsistencyTrackingServiceImpl implements ConsistencyTrackingService {

	private static final Logger log = LoggerFactory.getLogger(ConsistencyTrackingServiceImpl.class);
	
	@Autowired
	InconsistencyLogsRepo inconsistencyLogsRepo;
	
	@Autowired
	InconsistencyLogsService inconsistencyLogsService;
	
	@Autowired
	private ConsistencyTrackingRepo consistencyTrackingRepo;
	
	@Autowired
	private MasterDeliverableMappingRepo masterDeliverableMappingRepo;
	
	private InconsistencyLogs inconsistencyLogs = null;
	private HashMap<String,String> consistencyDataHashMap = null;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void saveConsistency(ConsistencyTracking consistencyTracking, int projectId, String userId) throws Exception {
		
		if(consistencyTrackingRepo.save(consistencyTracking) != null) {			
			
			
			  if("On Hold".equals(consistencyTracking.getConsistencyFlag())) {
			  
			  }else
			  if("Ignore Manually".equals(consistencyTracking.getConsistencyFlag())) {
				
				int initialCount = 0, resolvedCount=0;
				
				try 
				{
					initialCount = inconsistencyLogsRepo.findTopByProjectId(projectId).getInitialCount();
					resolvedCount = inconsistencyLogsRepo.getLastResolvedCountByProjectId(projectId) + 1;
				} catch (Exception e) {					
					System.out.println("#### Exception ConsistencyTrackingService :: saveConsistency 66 : "+e);
				}
				
				inconsistencyLogs = new InconsistencyLogs();
				inconsistencyLogs.setProjectId(projectId); 
				inconsistencyLogs.setInitialCount(initialCount);
				inconsistencyLogs.setResolvedCount(resolvedCount); 
				inconsistencyLogs.setDateOfEntry(new Timestamp(new Date().getTime()));
				
				inconsistencyLogsRepo.save(inconsistencyLogs);
				
				inconsistencyLogs = null;
				
			}else {
				
				inconsistencyLogs = new InconsistencyLogs();
				
				consistencyDataHashMap = new HashMap<>();
				consistencyDataHashMap.put("KeyField", consistencyTracking.getKeyField());
				consistencyDataHashMap.put("MdMappingId", String.valueOf(consistencyTracking.getMdMappingid()));
				consistencyDataHashMap.put("MasterFieldValue", consistencyTracking.getMasterFieldValue());
				consistencyDataHashMap.put("DeliverableFieldValue", consistencyTracking.getDeliverableFieldValue());
				
				getJSONMappingDataByProjectId(projectId,userId, null);
		
				}
			}
		
	}
	
	@Override
	public void saveConsistency(List<ConsistencyTracking> consistencyTracking) {
		
		consistencyTrackingRepo.saveAll(consistencyTracking);
	}
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public String getConsistencyCountForAllProjects(String userId, String role)
	{
		
		String response = "", jsonResponse = "";
		int totolConsistency = 0, projectId = 0, lastResolvedCount = 0;
		JSONObject jsonObject = new JSONObject(); 
	    JSONArray jsonArray = new JSONArray(); 	    
		JSONParser jsonParse = new JSONParser();
		
        try 
        {
			List<Object[]> projectIdNameList = getProjectIdList(userId, role);
			System.out.println("Total Project's for inconsistency check ["+projectIdNameList.size()+"]");
			for(Object [] project : projectIdNameList) {
				
				totolConsistency = 0;
				projectId = (Integer)project[0];
				System.out.println("#### projectId "+projectId);
				if( inconsistencyLogsTableCount(projectId) > 0){
					lastResolvedCount = getLastResolvedCountByProjectId(projectId);
					totolConsistency = ((inconsistencyLogsRepo.findTopByProjectId(projectId).getInitialCount()) - lastResolvedCount);
					
				}else 
				{
					jsonResponse = getJSONMappingDataByProjectId(projectId, userId, null);
					
				    if(checkNull(jsonResponse).length() > 0){
				    	
				    	JSONObject jsonObject2 = (JSONObject) jsonParse.parse(jsonResponse);
				    	JSONArray jsonArray2 = (JSONArray) jsonObject2.get("MAPPING_DATA");
				    	if(jsonArray2 != null) {
				    		totolConsistency = jsonArray2.size();
				    		if(totolConsistency !=0) {
				    			insertIntoInconsistencyLogsTable(projectId, totolConsistency);
				    		}
				    		
				    	}
				    	
				    	
					}	
				}				
				
				if(totolConsistency !=0) {
					
					JSONObject jsonObjectVal = new JSONObject();
					jsonObjectVal.put("PROJECT_ID",projectId);
					jsonObjectVal.put("PROJECT_NAME",(String)project[1]);
					jsonObjectVal.put("TOTAL_CONSISTENCY",totolConsistency);
					jsonArray.add(jsonObjectVal);
				}
			    
				
			}	
			
			jsonObject.put("COSISTENCY_COUNT", jsonArray);		
			response = jsonObject.toString();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return response;
	}
	
	private List<Object[]> getProjectIdList(String userId, String role) 
	{
		String sql = "";
		//String sql = "select pm.project_id,pm.project_name from [project_master] pm inner join [user_project_mapping] upm on pm.project_id = upm.project_id and upm.userid ='"+userId+"' and upm.status='Active'";
		if(role.equals("User")) {
			sql = "select pm.project_id,pm.project_name from [project_master] pm inner join [user_project_mapping] upm on pm.project_id = upm.project_id and upm.userid ='"+userId+"' and pm.status= upm.status and upm.status='Active' inner join [deliverabletype_master] dtm on pm.[deliverabletype_id] = dtm.[deliverabletype_id] and dtm.status='Active'";
		}else {
			sql = "select distinct (pm.project_id),pm.project_name from [project_master] pm inner join [master_deliverable_mapping] mdm on pm.project_id = mdm.project_id and pm.status = mdm.status and pm.status='Active'  inner join [deliverabletype_master] dtm on  pm.[deliverabletype_id] = dtm.[deliverabletype_id] and dtm.status='Active'";
		}
		
		
		List<Object[]> dataList = null;
		
		Session session = null;
		
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
		try 
		{
			Query query = session.createNativeQuery(sql);			
			dataList = query.getResultList();
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		System.out.println("##### getProjectIdList "+dataList.size());
		return dataList;
	}

	private int inconsistencyLogsTableCount(int projectId) 
	{		
		//int count = inconsistencyLogsService.getInconsistencyLogsTableCount(projectId);
		int count = inconsistencyLogsService.getInconsistencyLogsTableCountByProjectId(projectId);
		System.out.println("#### initialcount "+count);
		return count;
	}
	
	private int getLastResolvedCountByProjectId(int projectId) 
	{
		int count = inconsistencyLogsService.getLastResolvedCountByProjectId(projectId);
		System.out.println("#### getLastResolvedCountByProjectId "+count);
		return count;		
	}

	private void insertIntoInconsistencyLogsTable(int projectId, int totolConsistency) {
		
		InconsistencyLogs inconsistencyLogs = new InconsistencyLogs();
		inconsistencyLogs.setProjectId(projectId);
		inconsistencyLogs.setInitialCount(totolConsistency);
		inconsistencyLogs.setResolvedCount(0);
		inconsistencyLogs.setDateOfEntry(new Timestamp(new Date().getTime()));		
		inconsistencyLogsRepo.save(inconsistencyLogs);
	}
	
	@Override
	@Transactional
	public String getJSONMappingDataByProjectId(int projectId, String userId, String filterKeyField) throws Exception{
		
		String response = "";
		String inserSQL = "insert into temp_consistency_tracking_table_"+userId+"(key_field, mappingid, deliverable_name, master_data, deliverable_data) values(?,?,?,?,?)";
		List<Object[]> masterDeliverableValList = null;
		List<Integer> holdtrackingIdList = null; 
		List<Object[]> mappingList = null;		
		JSONArray jsonConsistentData = null;
		
		List<Integer> mappingIdList = new ArrayList<Integer>();
		
		int mappingId = 0, maxLoop = 0; 
		String masterKeyField = "", deliverableKeyField = "", masterTable = "", masterField = "",	deliverableTable = "", deliverableField = "";
		String masterDataVal = "", deliverableDataVal = "";
		
		dropTable(userId);
		createTable(userId);
		//m1	
		mappingList = getMasterDeliverableMapingData(projectId, filterKeyField);
		
		System.out.println("#### mappingList "+mappingList.size());
		
		mainLoop :
		for(Object[] mapping : mappingList) {	
			
			maxLoop = 0; mappingId = 0;
			masterKeyField = ""; deliverableKeyField = ""; masterTable = ""; masterField = "";	deliverableTable = ""; deliverableField = "";
			
			deliverableKeyField = (String)mapping[0]; mappingId = (Integer)mapping[1]; masterTable = (String) mapping[2]; 
			masterField = (String) mapping[3]; deliverableTable = (String) mapping[4]; deliverableField = (String) mapping[5];			
			
			mappingIdList.add(mappingId);
			
			//Used to maintain inconsistency_los_tables 
			if( consistencyDataHashMap != null && consistencyDataHashMap.get("MdMappingId").equals(String.valueOf(mappingId)) && checkNull(consistencyDataHashMap.get("deliverableName")).length() == 0) {
				consistencyDataHashMap.put("deliverableName", deliverableField);
				System.out.println("#### consistencyDataHashMap "+consistencyDataHashMap);
			}
			//m2
			masterKeyField = getMasterKeyField(masterTable, projectId);
			
			//m3
			masterDeliverableValList = getMasterAndDeliverableValByKeyField(masterField,masterTable,masterKeyField,deliverableTable,deliverableField,deliverableKeyField);
			
			if(masterDeliverableValList != null && masterDeliverableValList.size() > 0) {
				
				for(Object [] masterDeliverableVal : masterDeliverableValList) {
					
					masterDataVal = ""; 	deliverableDataVal = "";
					
					masterDataVal = (String) masterDeliverableVal[1];	deliverableDataVal = (String) masterDeliverableVal[2];					
					
					if(!masterDataVal.equalsIgnoreCase(deliverableDataVal)) {
						//Storing master and deliverable inconsistent value into temporary table. Later distinct record is taken from temporary table
						entityManager.createNativeQuery(inserSQL).setParameter(1,(String) masterDeliverableVal[0]).setParameter(2, mappingId).setParameter(3,deliverableField )
						  .setParameter(4,(String) masterDataVal).setParameter(5,(String) deliverableDataVal ).executeUpdate();
					}
					
					  
				}
			}
			
			
		}		
		
		//Used to maintain inconsistency_los_tables insert record for mark as alias and ignore by rule.
		if( inconsistencyLogs != null) {			
			saveDataForInconsistencyLogs(projectId, userId);			
		}
	
		//m4 It will be empty if no inconsistency mark yet.
		List<Object[]> trackingdataList = getTrackingData(userId, projectId);
		
		System.out.println("#### trackingdataList "+trackingdataList.size());
		
		if(trackingdataList.size() > 0) {
			
			//m5
			deleteMatchTempData(trackingdataList, userId);// Deleting when data is present in tracking table
			//m6
			//Right now It is not showing to other user that data is on hold. And neither it will be appear in inconsistency check.
			
			holdtrackingIdList = getHoldTrackingIdList(userId); //fetching when data is present in tracking table
			
			if(holdtrackingIdList != null) {
				
				//On Hold --> fetching when data is present in tracking table
				//m7
				jsonConsistentData = getHoldConsistentTrackingData(holdtrackingIdList, userId, projectId, mappingIdList); 
			}
			
		}
				
		//m8
		response = getRawTempTableData(jsonConsistentData,userId);
		
		return response;
	}
		
	
	//Gives the list of Active Mapping data list
	//Gives table, master field, deliverable table, deliverable field, deliverable table key field, master_deliverable_mapping id
	private List<Object[]> getMasterDeliverableMapingData(int projectId, String keyField) 
	{	
		String sql = "select rd.key_field as deliverable_keyField,md_mappingid, md.master_table, md.master_field, md.deliverable_table, md.deliverable_field"
				+ " from [master_deliverable_mapping] md inner join  repository_details rd on md.project_id="+projectId+" and md.deliverable_table = rd.tables_name and md.status ='Active'";
		
		if(checkNull(keyField).length() > 0) {
			sql = sql+" and rd.key_field='"+checkNull(keyField)+"'";
		}
		 		
		List<Object[]> dataList = null;
			
		Session session = null;
		
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
		try 
		{
			Query query = session.createNativeQuery(sql);			
			dataList = query.getResultList();
			
		} catch (Exception e) {			
			System.out.println("#### Exception ConsistencyTrackingService :: getMasterDeliverableMapingData : "+e);
		}
		
		System.out.println("##### getMasterDeliverableMapingData "+dataList.size());
		return dataList;
		
	}
	
	//Getting master key field for inconsistency data comparison
	//Inconsistency is calculated, by matching master table and deliverable table key field.
	private String getMasterKeyField(String masterTable, int projectId) 
	{
		String masterTableKeyField = "";
		Session session = null;
		String sql = "select rd.key_field from repository_details rd where rd.project_id="+projectId
				+ "  and rd.tables_name = '"+checkNull(masterTable)+"'";
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
		try 
		{
			Query query = session.createNativeQuery(sql);			
			masterTableKeyField = query.getSingleResult().toString();			
			
		} catch (Exception e) {			
			System.out.println("#### Exception ConsistencyTrackingService :: getMasterKeyField: "+e);
		}
		
		return masterTableKeyField;
	}
	
	//Fetching master field and deliverable field value on the basis of mapping.
	private List<Object[]> getMasterAndDeliverableValByKeyField(String masterField, String masterTable,
			String masterKeyField, String deliverableTable, String deliverableField, String deliverableKeyField) 
	{
		
		List<Object[]> dataList = null;		
		String masterKey = "", deliverableKey = "";
		//"x,y,z" --> "x+t2.y+t2.z"		
		if(masterKeyField.contains(",")) {
			masterKey = masterKeyField.replace(",", "+t1.");			
		}else {
			masterKey = masterKeyField;
		}
		
		if(deliverableKeyField.contains(",")) {
			deliverableKey = deliverableKeyField.replace(",", "+t2.");
		}else {
			deliverableKey = deliverableKeyField;
		}
		
		String sql = "select t2."+checkNull(deliverableKey)+" as deliverableKeyField, t1."+checkNull(masterField)+" as masterFieldVal,t2."+checkNull(deliverableField)+" as deliverableFieldVal from "+checkNull(masterTable)+" "
				+ "t1 inner join "+deliverableTable+" t2 on t1."+masterKey+" = t2."+deliverableKey;		
		
		Session session = null;
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
		try 
		{
			Query query = session.createNativeQuery(sql);			
			dataList = query.getResultList();
			
			
		} catch (Exception e) {			
			System.out.println("#### Exception ConsistencyTrackingService :: getMasterAndDeliverableValByKeyField : "+e);
		}
		
		return dataList;
		
		
	}
	
	//On submit of inconsistency (Mark as Alias and Ignore by rule) data is inserted into log table.
	private void saveDataForInconsistencyLogs(int projectId, String userId) {
		int initialCount = 0, resolvedCount = 0;
		try 
		{
			initialCount = inconsistencyLogsRepo.findTopByProjectId(projectId).getInitialCount();
			resolvedCount = inconsistencyLogsRepo.getLastResolvedCountByProjectId(projectId) + getResolvedCount(projectId, userId);
		} catch (Exception e) {
			System.out.println("#### Exception ConsistencyTrackingService :: saveDataForInconsistencyLogs :"+e);
		}
		
		inconsistencyLogs.setProjectId(projectId); 
		inconsistencyLogs.setInitialCount(initialCount);
		inconsistencyLogs.setResolvedCount(resolvedCount); 
		inconsistencyLogs.setDateOfEntry(new Timestamp(new Date().getTime()));
		
		inconsistencyLogsRepo.save(inconsistencyLogs);
		
		inconsistencyLogs = null;	consistencyDataHashMap = null;		
	}

	private int getResolvedCount(int projectId, String userId) 
	{
		
		Session session = null;
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
		int count = 0;
		
		String sql = "select count(*) from temp_consistency_tracking_table_"+userId+" where mappingid ='"+consistencyDataHashMap.get("MdMappingId")+"' and deliverable_name ='"+consistencyDataHashMap.get("deliverableName")+"' and master_data ='"+consistencyDataHashMap.get("MasterFieldValue")+"' and deliverable_data = '"+consistencyDataHashMap.get("DeliverableFieldValue")+"'";
		try 
		{
			Query query = session.createNativeQuery(sql);			
			if(query.getSingleResult() != null) {
				count = (int) query.getSingleResult();
			}		
			
		} catch (Exception e) {			
			System.out.println("#### Exception ConsistencyTrackingService :: getResolvedCount : "+e);
		}
		System.out.println("#### getResolvedCount "+count);
		return count;
	}

	
	
	//Select Submitted tracking data for particular project and user. So It will be removed from UI.
	//It will be empty if no inconsistency mark yet.
	private List<Object[]> getTrackingData(String userId, int projectId) 
	{		
		List<Object[]> dataList = null;		
		Session session = null;
		
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
		//String sql = "select ct.key_field,ct.md_mappingid,md.deliverable_field,ct.master_field_value,ct.deliverable_field_value,ct.consistency_flag from [consistency_tracking_table] ct, "
		//		+ "  [master_deliverable_mapping] md where ct.md_mappingid = md.md_mappingid  and ct.flagged_by='"+userId+"' and md.status='Active' and md.project_id="+projectId;
		
		//Changed on 29-10-2021. All User can see only clean data.
		String sql = "select ct.key_field,ct.md_mappingid,md.deliverable_field,ct.master_field_value,ct.deliverable_field_value,ct.consistency_flag from [consistency_tracking_table] ct, "
				+ "  [master_deliverable_mapping] md where ct.md_mappingid = md.md_mappingid  and md.status='Active' and md.project_id="+projectId;
	
		try 
		{
			Query query = session.createNativeQuery(sql);			
			dataList = query.getResultList();
			
		} catch (Exception e) {			
			System.out.println("#### Exception ConsistencyTrackingService :: getTrackingData : "+e);
		}
		
		System.out.println("##### getTrackingData "+dataList.size());
		//System.out.println("##### dataList "+dataList);
		return dataList;
		
	}
	
	//To hide submitted inconsistent data in the UI, It is deleted from temporary table.
	//In the UI data from temporary table is shown, which is processed in various steps.
	private void deleteMatchTempData(List<Object[]> trackingdataList, String userId) {
		
		String sql = "";
		String consistencyFlag = "";
		Session session = null;
		
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
		try {
			
			for(Object [] data: trackingdataList)
			{
				
				consistencyFlag = (String)data[5];
				//Added on 14-10-2021
				if(checkNull(consistencyFlag).equals("Ignore Manually") || checkNull(consistencyFlag).equals("On Hold")) {
					sql = "delete from temp_consistency_tracking_table_"+userId+" where key_field='"+(String)data[0]+"' and mappingid ='"+(Integer)data[1]+
							"' and deliverable_name='"+(String)data[2]+"' and master_data ='"+(String)data[3]+"' and deliverable_data = '"+(String)data[4]+"'" ;	
				}else {
					sql = "delete from temp_consistency_tracking_table_"+userId+" where mappingid ='"+(Integer)data[1]+
							"' and deliverable_name='"+(String)data[2]+"' and master_data ='"+(String)data[3]+"' and deliverable_data = '"+(String)data[4]+"'" ;
				}			
						
				session.createSQLQuery(sql).executeUpdate();
			}	
			
		} catch (Exception e) {			
			System.out.println("#### Exception ConsistencyTrackingService :: deleteMatchTempData"+e);
		}
		
	}
	
	private List<Integer> getHoldTrackingIdList(String userId) 
	{
			
		List<Integer> dataList = null;			
		Session session = null;
		
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
		String sql = "select max(trackingid) as trackingid from  consistency_tracking_table where flagged_by = '"+userId+"' group by "
				+ " key_Field, md_mappingid, master_field_value,deliverable_field_value";
		
		try 
		{
			Query query = session.createNativeQuery(sql);				
			dataList = query.getResultList();
			
		} catch (Exception e) {			
			System.out.println("#### Exception ConsistencyTrackingService :: getHoldTrackingIdList"+e);
		}
		System.out.println("#### getHoldTrackingIdList "+dataList.size());
		return dataList;

	}
		
	private JSONArray getHoldConsistentTrackingData(List<Integer>trackingIdList, String userId, int projectId, List<Integer> mappingIdList) 
	{
					
		String sql = "";		
		JSONArray consistentJsonArray = new JSONArray();	
		JSONObject jsonObjectVal = null;
		List<Object[]> data = null;	
		Session session = null;
		Query query = null;
		
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}		
		
		try 
		{	
			//Added on 29-10-2021 START
			if(mappingIdList.size() > 0) {
				for(Integer mappingId : mappingIdList) {
					
					for( Integer trackingId : trackingIdList) {
						
						//Commented and Changed on 29-10-2021 START
						/*
						  sql = "select ct.key_field, ct.md_mappingid,md.deliverable_field,ct.master_field_value,ct.deliverable_field_value,ct.consistency_flag,ct.remarks from [consistency_tracking_table] ct, [master_deliverable_mapping] md "				  
						  +", repository_details rd where ct.md_mappingid = "+mappingId+" and rd.tables_name = md.deliverable_table and ct.consistency_flag='On Hold' and ct.flagged_by='"
						  +userId+"' and md.status='Active' and md.project_id="+projectId+" and ct.trackingid  ="+trackingId;
						  */
						
						sql = "select ct.key_field,ct.md_mappingid,md.deliverable_field,ct.master_field_value,ct.deliverable_field_value,ct.consistency_flag, ct.remarks   from [consistency_tracking_table] ct inner join [master_deliverable_mapping] md   on ct.md_mappingid = md.md_mappingid and  md.md_mappingid="+mappingId+" and ct.consistency_flag='On Hold' and ct.flagged_by='"+userId+"' and ct.trackingid="+trackingId;;
						//Commented on 29-10-2021 END
						   query = session.createNativeQuery(sql);
						   data = query.getResultList();
							
							  data = query.getResultList(); 
							  if(data != null) { 
								  for(Object [] d : data) {
									  
								    jsonObjectVal = new JSONObject();			
										
									jsonObjectVal.put("KEY_FIELD",(String) d[0]);
									jsonObjectVal.put("MAPPING_ID",String.valueOf((Integer) d[1]));
									jsonObjectVal.put("DELIVERABLE_NAME",(String) d[2]);
									jsonObjectVal.put("MASTER_DATA", (String) d[3]);
									jsonObjectVal.put("DELIVERABLE_DATA",(String) d[4]);
									jsonObjectVal.put("CONSISTENCY_FLAG",(String) d[5]);
									jsonObjectVal.put("REMARK",(String) d[6]);
								    consistentJsonArray.add(jsonObjectVal);
								  } 
					 } 
						 
					}
					
				}
			}
			
			//Added on 29-10-2021 END
			
			/*
			  for( Integer trackingId : trackingIdList) {
			  
			  sql =
			  "select ct.key_field, ct.md_mappingid,md.deliverable_field,ct.master_field_value,ct.deliverable_field_value,ct.consistency_flag,ct.remarks from [consistency_tracking_table] ct, [master_deliverable_mapping] md "
			  +", repository_details rd where ct.md_mappingid = md.md_mappingid and rd.tables_name = md.deliverable_table and ct.consistency_flag='On Hold' and ct.flagged_by='"
			  +userId+"' and md.status='Active' and md.project_id="+projectId+" and ct.trackingid  ="+trackingId;
			  
			  query = session.createNativeQuery(sql); data = query.getResultList();
			  
			  data = query.getResultList(); if(data != null) { for(Object [] d : data) {
			  
			  jsonObjectVal = new JSONObject();
			  
			  jsonObjectVal.put("KEY_FIELD",(String) d[0]);
			  jsonObjectVal.put("MAPPING_ID",String.valueOf((Integer) d[1]));
			  jsonObjectVal.put("DELIVERABLE_NAME",(String) d[2]);
			  jsonObjectVal.put("MASTER_DATA", (String) d[3]);
			  jsonObjectVal.put("DELIVERABLE_DATA",(String) d[4]);
			  jsonObjectVal.put("CONSISTENCY_FLAG",(String) d[5]);
			  jsonObjectVal.put("REMARK",(String) d[6]);
			  consistentJsonArray.add(jsonObjectVal); } }
			  
			  }
			 */
			
			
		} catch (Exception e) {
			
			System.out.println("#### Exception :: ConsistencyTrackingService  : getHoldConsistentTrackingData :"+e);
		}			
		//System.out.println("####### On Hold Data Size  "+consistentJsonArray.size());
		
		return consistentJsonArray;
	
	}
		

	private String getRawTempTableData(JSONArray consistentJsonArray, String userId)
	{
		System.out.println("#### getRawTempTableData");
		String response = "";
		JSONObject jsonObject = new JSONObject();		
		JSONArray jsonArray = new JSONArray();
		
		JSONObject jsonObjectVal = null;
		List<Object[]> dataList = null;
		
		Session session = null;
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}		
		String sql = "select distinct key_field, mappingid, deliverable_name, master_data, deliverable_data from temp_consistency_tracking_table_"+userId;
		try 
		{
			Query query = session.createNativeQuery(sql);			
			dataList = query.getResultList();
			
			for(Object [] data: dataList)		
			{
				jsonObjectVal = new JSONObject();			
				
				jsonObjectVal.put("KEY_FIELD",(String) data[0]);
				jsonObjectVal.put("MAPPING_ID",(String) data[1]);
				jsonObjectVal.put("DELIVERABLE_NAME",(String) data[2]);
				jsonObjectVal.put("MASTER_DATA", (String) data[3]);
				jsonObjectVal.put("DELIVERABLE_DATA",(String) data[4]);
				jsonObjectVal.put("CONSISTENCY_FLAG","");
				jsonObjectVal.put("REMARK","");
				
				jsonArray.add(jsonObjectVal);			
				
			}
			
			if(consistentJsonArray != null && consistentJsonArray.size() >0) {			
				for(Object arrayObj : consistentJsonArray) {
					jsonArray.add(arrayObj);
				}
			}
			
			jsonObject.put("MAPPING_DATA", jsonArray);	
			
			response = jsonObject.toString();
			
		} catch (Exception e) {			
			System.out.println("#### Exception ConsistencyTrackingService :: getTempTableData: "+e);
		}finally {
			dropTable(userId);
		}
		
		return response;
	}
	
	//@javax.transaction.Transactional
	public boolean createTable(String userId)
	{	
		Session session = null;
		
		String sql = "CREATE TABLE [temp_consistency_tracking_table_"+userId+"]( [id] INT IDENTITY(1,1) PRIMARY KEY, "
				+ "[key_field] VARCHAR(1000),[mappingid] VARCHAR(50), [deliverable_name] VARCHAR(750), "
				+ "[master_data] VARCHAR(150) , [deliverable_data] VARCHAR(150))";
		
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
		try {
			int i = session.createSQLQuery(sql).executeUpdate();
		} catch (Exception e) {
			System.out.println("#### Exception ConsistencyTrackingService :: createTable : "+e);
			return false;
		}	
		
		return true;
	}
	
	@Modifying	
	//@javax.transaction.Transactional
	public boolean dropTable(String userId)
	{	
		Session session = null;
		String sql = "drop table if exists [temp_consistency_tracking_table_"+userId+"]";
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
		int i = 0;
		try {
			i = session.createSQLQuery(sql).executeUpdate();
		} catch (Exception e) {			
			System.out.println("#### Exception ConsistencyTrackingService :: dropTable : "+e);
			return false;
		}
		System.out.println("#### dopTable count "+i);
		return true;
	}
	
	public List<String> getTableColData(String tableName, String colName)
	{
		List<String> dataList = null;
		Session session = null;
				
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
		String sql = "select "+checkNull(colName)+" from "+checkNull(tableName)+" order by "+checkNull(tableName)+"_id";
		
		try 
		{
			dataList = session.createSQLQuery(sql).getResultList();
		} catch (Exception e) {			
			System.out.println("#### Exception ConsistencyTrackingService :: getTableColData : "+e);			
		}
		
		return dataList;
	}
	
	public String checkNull(String input)
    {
		System.out.println("#### Input String ["+input+"]");
        if(input == null || "null".equalsIgnoreCase(input) || "undefined".equalsIgnoreCase(input))
        input = "";
        return input.trim();    
    }
	
	
	
}
