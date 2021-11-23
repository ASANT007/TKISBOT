package com.tkis.qedbot.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
	private IterationTrackingService iterationTrackingService;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void saveConsistency(ConsistencyTracking consistencyTracking, int projectId, String userId) throws Exception
	{		
		int initialCount = 0, resolvedCount=0, maxSrNo = 0;
		
		int batchId = 0; // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Remaining >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		
		String lastLogDate = "";
		String consistencyFlag = "";
		consistencyFlag = checkNull(consistencyTracking.getConsistencyFlag());
		if(consistencyTrackingRepo.save(consistencyTracking) != null)
		{				
		
			System.out.println("##### Consistency Save for Flag["+consistencyFlag+"]");
			if("On Hold".equals(consistencyFlag)) 
			{
			  
			}else if("Ignore Manually".equals(consistencyFlag) || "Mark as Alias".equals(consistencyFlag))
			{		
				
				try 
				{
					batchId = inconsistencyLogsService.getMaxBatchIdByProjectId(projectId);// Max batchId
					
					//initialCount = inconsistencyLogsRepo.findTopByProjectId(projectId).getInitialCount();
					initialCount = inconsistencyLogsRepo.findTopByProjectIdAndBatchId(projectId,batchId).getInitialCount();
					lastLogDate = checkNull(inconsistencyLogsRepo.getDateOfEntryProjectId(projectId));
					
					System.out.println("Date Diff ["+lastLogDate);
					
					
					
					//Update data
					if(lastLogDate.equals("0")) 
					{
						
						
						if("Ignore Manually".equals(consistencyFlag)) {
							//resolvedCount = inconsistencyLogsRepo.getLastResolvedCountByProjectId(projectId) + 1;
							
							resolvedCount = inconsistencyLogsRepo.getSumOfResolvedCountByProjectIdAndBatchId(projectId,batchId) + 1;
						}else {
							//resolvedCount = inconsistencyLogsRepo.getLastResolvedCountByProjectId(projectId) + consistencyTracking.getFlagCount();
							resolvedCount = inconsistencyLogsRepo.getSumOfResolvedCountByProjectIdAndBatchId(projectId, batchId) + consistencyTracking.getFlagCount();
							
						}
						
						maxSrNo = inconsistencyLogsRepo.getMaxSrNoByProjectId(projectId);
						Optional<InconsistencyLogs> optional = inconsistencyLogsRepo.findById(maxSrNo);
						if(optional.isPresent()) {
							
							InconsistencyLogs inconsistencyLogs = optional.get();
							inconsistencyLogs.setResolvedCount(resolvedCount);
							inconsistencyLogsRepo.save(inconsistencyLogs);
						}
						
					}
					else //Insert Data
					{
						
						if("Ignore Manually".equals(consistencyFlag)) {
							resolvedCount = 1;
						}else {
							resolvedCount = consistencyTracking.getFlagCount();
						}
						
						InconsistencyLogs inconsistencyLogs = new InconsistencyLogs();
						inconsistencyLogs.setProjectId(projectId); 
						inconsistencyLogs.setInitialCount(initialCount);
						inconsistencyLogs.setResolvedCount(resolvedCount); 
						inconsistencyLogs.setDateOfEntry(new Timestamp(new Date().getTime()));
						inconsistencyLogs.setBatchId(batchId);
						inconsistencyLogsRepo.save(inconsistencyLogs);
						
					}
					
				} 
				catch (Exception e) {					
					System.out.println("#### Exception ConsistencyTrackingService :: saveConsistency -- : "+e);
				}
			
			  }else
			  {// Ignore By rule
				  
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
		int totolConsistency = 0, projectId = 0, initialCount = 0, lastResolvedCount = 0, logTableBatchId = 0, iterationBatchId = 0;
		JSONObject jsonObject = new JSONObject(); 
	    JSONArray jsonArray = new JSONArray(); 	    
		JSONParser jsonParse = new JSONParser();
		
        try 
        {
			List<Object[]> projectIdNameList = getProjectIdList(userId, role); // Change it put in repository
			System.out.println("Total Project's for inconsistency check ["+projectIdNameList.size()+"]");
			for(Object [] project : projectIdNameList) {
				
				totolConsistency = 0;
				projectId = (Integer)project[0];
				System.out.println("#### projectId "+projectId);
				
				//Check table has data or not for the given projectId no need of batchId.
				if( inconsistencyLogsTableCount(projectId) > 0){
					
					//Check for New Iteration Data START
					logTableBatchId = inconsistencyLogsService.getMaxBatchIdByProjectId(projectId);
					iterationBatchId = iterationTrackingService.getMaxBatchIdByProjectId(projectId);
					
					if(logTableBatchId != iterationBatchId) {
						
						//Common Part START						
						jsonResponse = getJSONMappingDataByProjectId(projectId, userId, null, null, "Dashboard");
						
					    if(checkNull(jsonResponse).length() > 0){
					    	
					    	JSONObject jsonObject2 = (JSONObject) jsonParse.parse(jsonResponse);
					    	JSONArray jsonArray2 = (JSONArray) jsonObject2.get("MAPPING_DATA");
					    	if(jsonArray2 != null) {
					    		totolConsistency = jsonArray2.size();
					    		if(totolConsistency != 0) {					    			
					    			//Insert data for latest batchId in to the inconsistency_logs_table
					    			inconsistencyLogsService.saveInconsistencyLogsTableData(projectId, totolConsistency, iterationBatchId);
					    		}
					    		
					    	}
					    	
					    	
						}
					    System.out.println("#### totolConsistency "+totolConsistency);
					  //Common Part END
					    
					}else {
						//OLD Code
						//initialCount = inconsistencyLogsRepo.findTopByProjectId(projectId).getInitialCount();
						initialCount = inconsistencyLogsService.findTopByProjectIdAndBatchId(projectId, logTableBatchId).getInitialCount();
						System.out.println("#### initialCount "+initialCount);
						//lastResolvedCount = getLastResolvedCountByProjectId(projectId);
						lastResolvedCount = inconsistencyLogsService.getSumOfResolvedCountByProjectIdAndBatchId(projectId, logTableBatchId);
						System.out.println("#### lastResolvedCount "+lastResolvedCount);
						
						
						totolConsistency = (initialCount - lastResolvedCount);
						System.out.println("#### totolConsistency "+totolConsistency);
					}
					//Check for New Iteration Data END
					
					
					
				}else 
				{
					//Common Part START
					iterationBatchId = iterationTrackingService.getMaxBatchIdByProjectId(projectId);
					jsonResponse = getJSONMappingDataByProjectId(projectId, userId, null, null, "Dashboard");
					
				    if(checkNull(jsonResponse).length() > 0){
				    	
				    	JSONObject jsonObject2 = (JSONObject) jsonParse.parse(jsonResponse);
				    	JSONArray jsonArray2 = (JSONArray) jsonObject2.get("MAPPING_DATA");
				    	if(jsonArray2 != null) {
				    		totolConsistency = jsonArray2.size();
				    		if(totolConsistency != 0) {
				    			inconsistencyLogsService.saveInconsistencyLogsTableData(projectId, totolConsistency, iterationBatchId);
				    		}
				    		
				    	}
				    	
				    	
					}	
				  //Common Part END
				}				
				
				if(totolConsistency != 0) {
					
					JSONObject jsonObjectVal = new JSONObject();
					jsonObjectVal.put("PROJECT_ID",projectId);
					jsonObjectVal.put("PROJECT_NAME",(String)project[1]);
					jsonObjectVal.put("DELIVERABLE_TYPE",(String)project[2]);
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
		
		if(role.equals("User")) {
			sql = "select pm.project_id,pm.project_name, dtm.deliverabletype_shortname from [project_master] pm inner join [user_project_mapping] upm on pm.project_id = upm.project_id and upm.userid ='"+userId+"' and pm.status= upm.status and upm.status='Active' inner join [deliverabletype_master] dtm on pm.[deliverabletype_id] = dtm.[deliverabletype_id] and dtm.status='Active'";
		}else {
			sql = "select distinct (pm.project_id),pm.project_name, dtm.deliverabletype_shortname from [project_master] pm inner join [master_deliverable_mapping] mdm on pm.project_id = mdm.project_id and pm.status = mdm.status and pm.status='Active'  inner join [deliverabletype_master] dtm on  pm.[deliverabletype_id] = dtm.[deliverabletype_id] and dtm.status='Active'";
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

	//Only check table have data or not.
	private int inconsistencyLogsTableCount(int projectId) 
	{		
		int count = inconsistencyLogsService.getInconsistencyLogsTableCountByProjectId(projectId);
		System.out.println("#### initialcount "+count);
		return count;
	}
	
	//Commented on 19-11-2021
	/*
	private int getLastResolvedCountByProjectId(int projectId) 
	{
		int count = inconsistencyLogsService.getLastResolvedCountByProjectId(projectId);
		System.out.println("#### getLastResolvedCountByProjectId "+count);
		return count;		
	}
	*/
	
	//Commented on 19-11-2021
	/*
	private void insertIntoInconsistencyLogsTable(int projectId, int totolConsistency, int iterationBatchId) {
		
		InconsistencyLogs inconsistencyLogs = new InconsistencyLogs();
		inconsistencyLogs.setProjectId(projectId);
		inconsistencyLogs.setInitialCount(totolConsistency);
		inconsistencyLogs.setResolvedCount(0); // Check for this...
		inconsistencyLogs.setDateOfEntry(new Timestamp(new Date().getTime()));
		inconsistencyLogs.setBatchId(iterationBatchId);//Added on 19-11-2021
		inconsistencyLogsRepo.save(inconsistencyLogs);
	}
	*/
	
	@Override
	@Transactional
	public String getJSONMappingDataByProjectId(int projectId, String userId, String filterKeyField, String deliverableColumn, String callFrom) throws Exception{
		
		String response = "";
		String inserSQL = "insert into temp_consistency_tracking_table_"+userId+"(key_field, mappingid, deliverable_name, master_data, deliverable_data) values(?,?,?,?,?)";
		List<Object[]> masterDeliverableValList = null;
		List<Integer> holdtrackingIdList = null; 
		List<Object[]> mappingList = null;		
		JSONArray jsonConsistentData = null;
		
		List<Integer> mappingIdList = new ArrayList<Integer>();
		
		deliverableColumn = checkNull(deliverableColumn);
		
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
						
			//m2
			masterKeyField = getMasterKeyField(masterTable, projectId);
			
			//m3
			masterDeliverableValList = getMasterAndDeliverableValByKeyField(masterField,masterTable,masterKeyField,deliverableTable,deliverableField,deliverableKeyField);
			
			System.out.println("Row Data Size ==> "+masterDeliverableValList.size());
			
			if(masterDeliverableValList != null && masterDeliverableValList.size() > 0) {
				
				for(Object [] masterDeliverableVal : masterDeliverableValList) {
					
					masterDataVal = ""; 	deliverableDataVal = "";
					
					masterDataVal = (String) masterDeliverableVal[1];	deliverableDataVal = (String) masterDeliverableVal[2];					
					
					if(!masterDataVal.equalsIgnoreCase(deliverableDataVal)) {
						
						//Storing master and deliverable inconsistent value into temporary table. Later distinct record is taken from temporary table		
						if(deliverableColumn.length() > 0 ) {
							//added on 16-11-2021 Deliverable name wise filter data.
							if(deliverableColumn.equals(deliverableField)) {
								entityManager.createNativeQuery(inserSQL).setParameter(1,(String) masterDeliverableVal[0]).setParameter(2, mappingId).setParameter(3,deliverableField )
								  .setParameter(4,(String) masterDataVal).setParameter(5,(String) deliverableDataVal ).executeUpdate();
							}
							
						}else {
							entityManager.createNativeQuery(inserSQL).setParameter(1,(String) masterDeliverableVal[0]).setParameter(2, mappingId).setParameter(3,deliverableField )
							  .setParameter(4,(String) masterDataVal).setParameter(5,(String) deliverableDataVal ).executeUpdate();
						}
						
					}
					
					  
				}
			}
			
			
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
				// Send column filter here
				jsonConsistentData = getHoldConsistentTrackingData(holdtrackingIdList, userId, projectId, mappingIdList, deliverableColumn); 
			}
			
		}
				
		//m8
		response = getRawTempTableData(jsonConsistentData,userId, callFrom);
		
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
	
	//Select Submitted tracking data for particular project and user. So It will be removed from UI.
	//It will be empty if no inconsistency mark yet.
	private List<Object[]> getTrackingData(String userId, int projectId) 
	{		
		List<Object[]> dataList = null;		
		Session session = null;
		
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
				
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
		
	private JSONArray getHoldConsistentTrackingData(List<Integer>trackingIdList, String userId, int projectId, List<Integer> mappingIdList, String deliverableColumn) 
	{
					
		String sql = "";		
		JSONArray consistentJsonArray = new JSONArray();	
		JSONObject jsonObjectVal = null;
		List<Object[]> data = null;	
		Session session = null;
		Query query = null;
		deliverableColumn = checkNull(deliverableColumn);
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}		
		
		try 
		{	
			//Added on 29-10-2021 START
			if(mappingIdList.size() > 0) {
				
				for(Integer mappingId : mappingIdList) {
					
					for( Integer trackingId : trackingIdList) {
						if(deliverableColumn.length() > 0) {
							
							sql = "select ct.key_field,ct.md_mappingid,md.deliverable_field,ct.master_field_value,ct.deliverable_field_value,ct.consistency_flag, ct.remarks   from [consistency_tracking_table] ct inner join [master_deliverable_mapping] md   on ct.md_mappingid = md.md_mappingid and  md.md_mappingid="+mappingId+" and ct.consistency_flag='On Hold' and ct.flagged_by='"+userId+"' and md.deliverable_field='"+deliverableColumn+"' and ct.trackingid="+trackingId;

						}else {
							
							sql = "select ct.key_field,ct.md_mappingid,md.deliverable_field,ct.master_field_value,ct.deliverable_field_value,ct.consistency_flag, ct.remarks   from [consistency_tracking_table] ct inner join [master_deliverable_mapping] md   on ct.md_mappingid = md.md_mappingid and  md.md_mappingid="+mappingId+" and ct.consistency_flag='On Hold' and ct.flagged_by='"+userId+"' and ct.trackingid="+trackingId;							
						}
								
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
			
		} catch (Exception e) {
			
			System.out.println("#### Exception :: ConsistencyTrackingService  : getHoldConsistentTrackingData :"+e);
		}			
				
		return consistentJsonArray;
	
	}
		

	private String getRawTempTableData(JSONArray consistentJsonArray, String userId, String callFrom)
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
			System.out.println("#### Data Size "+jsonArray.size());
			if(callFrom != null) {
				//Used to parse JSON in Java Code. To show incosistency count in Dashboard
				jsonObject.put("MAPPING_DATA", jsonArray);			
				response = jsonObject.toString();
			}else {
				//Used to parse Json in JavaScript (ajax call), when checking inconsistency data
				response = jsonArray.toString();
			}
			
			
			
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
	
	private String checkNull(String input)
    {
		System.out.println("#### Input String ["+input+"]");
        if(input == null || "null".equalsIgnoreCase(input) || "undefined".equalsIgnoreCase(input))
        input = "";
        return input.trim();    
    }
			
	@Override
	public String getFieldWiseReportData(int projectId, String userId) {
		
		String response = "", fieldName = "", consistencyCheckCount = "";		
		JSONObject mainJsonObject = new JSONObject();
		List<Object[]> fieldWiseDataList = null;
		
		fieldWiseDataList = consistencyTrackingRepo.getFieldWiseReportData(projectId);
		
		if(fieldWiseDataList != null && fieldWiseDataList.size() >0) {
			
			for(Object [] data : fieldWiseDataList) {
				fieldName = fieldName+","+(String)data[0];
				consistencyCheckCount = consistencyCheckCount+","+(int)data[1];
			}		
			
			mainJsonObject.put("FIELDS", fieldName.substring(1));
			mainJsonObject.put("CONSIS_CHK", consistencyCheckCount.substring(1));
			
		}
		
		response = mainJsonObject.toString();
		System.out.println("#### getFieldWiseReportData "+response);
		return response;
	}

	@Override
	public String getProjectAndDateWiseReportData(int deliverableTypeId, String userId) {
		
		return inconsistencyLogsService.getProjectAndDateWiseReportData(deliverableTypeId,userId);
	}
	
}
