package com.tkis.qedbot.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkis.qedbot.entity.MasterDeliverableMapping;
import com.tkis.qedbot.repo.MasterDeliverableMappingRepo;

@Service
public class MasterDeliverableMappingServiceImpl implements MasterDeliverableMappingService {

	private static final Logger log = LoggerFactory.getLogger(MasterDeliverableMappingServiceImpl.class);
	
	@Autowired
	private MasterDeliverableMappingRepo masterDeliverableMappingRepo;
	
	@Override
	public String saveMapping(int projectId, String masterTable, String masterField, String deliverableTable,
			String deliverableField, String userId) throws Exception {
		//Saving single entry
		System.out.println("#### projectId - "+projectId+" masterTable - "+masterTable+" masterField - "+masterField);
		System.out.println("#### deliverableTable - "+deliverableTable+" deliverableField - "+deliverableField+" userId - "+userId);
		System.out.println("#### DateTime "+new Timestamp(new Date().getTime()));
		MasterDeliverableMapping masterDeliverableMapping = new MasterDeliverableMapping();
		masterDeliverableMapping.setProjectId(projectId);
		masterDeliverableMapping.setMasterTable(masterTable);
		masterDeliverableMapping.setMasterField(masterField);
		masterDeliverableMapping.setDeliverableTable(deliverableTable);
		masterDeliverableMapping.setDeliverableField(deliverableField);
		  
		masterDeliverableMapping.setStatus("Active");
		masterDeliverableMapping.setCreatedBy(userId);
		masterDeliverableMapping.setCreationDate(new Timestamp(new Date().getTime()));
		  
		masterDeliverableMappingRepo.save(masterDeliverableMapping);
		
		return "Mapping Saved Successfully";
	}

	@Override
	public String getMasterDeliverableMapping(int projectId) throws Exception {
		List<Object[]> mappingList = null;
		mappingList = masterDeliverableMappingRepo.getMasterDeliverableMapping(projectId);
		JSONArray arr = new JSONArray();
		JSONObject obj = null;
		
		for(Object[] mapping : mappingList) {
			int Id = 0;
			String masterTable = "", masterField = "",	deliverableTable = "", deliverableField = "";
			
			Id = (Integer) mapping[0];
			masterTable = (String) mapping[1];
			masterField = (String) mapping[2];
			deliverableTable = (String) mapping[3];
			deliverableField = (String) mapping[4];
			
			obj = new JSONObject();
            obj.put("mdMappingId",Id);
            obj.put("masterTable",masterTable);
            obj.put("masterField",masterField);
            obj.put("deliverableTable",deliverableTable);
            obj.put("deliverableField",deliverableField);
            
            arr.add(obj);
		}
		return arr.toString();
		
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public String deactiveMasterDeliverableMapping(String mappingIdList, String userId) throws Exception {
		String[] mappingIdArray = mappingIdList.split(",");
		int row = 0;
		for(String mdId : mappingIdArray) {
			System.out.println("### mdId : "+mdId);
			row = masterDeliverableMappingRepo.updateMasterDeliverableMapping(Integer.valueOf(mdId), userId, new Timestamp(new Date().getTime()));
		}
		return "success";
	}

	@Override
	public boolean isMappingPresent(int projectId, String masterTable, String masterField, String deliverableTable,
			String deliverableField) {
		
		return masterDeliverableMappingRepo.isMappingPresent(projectId,masterTable,masterField,deliverableTable,deliverableField);
	}

	/*
	 * @Override public String getJSONMappingDataByProjectId(int projectId, String
	 * userId) throws Exception {
	 * 
	 * String response = ""; List<Object[]> mappingList = null; int mappingId = 0;
	 * String masterTable = "", masterField = "", deliverableTable = "",
	 * deliverableField = ""; int maxLoop = 0;
	 * 
	 * //Added on 04-10-2021 START dropTable(userId); createTable(userId); //Added
	 * on 04-10-2021 END mappingList =
	 * masterDeliverableMappingRepo.getMasterDeliverableMapping(projectId);
	 * 
	 * //Commented on 04-10-2021 START //JSONObject jsonObject = new JSONObject();
	 * //JSONArray jsonArray = new JSONArray(); //JSONObject jsonObjectVal = null;
	 * //Commented on 04-10-2021 END
	 * 
	 * mainLoop : for(Object[] mapping : mappingList) {
	 * 
	 * maxLoop = 0; mappingId = 0; masterTable = ""; masterField = "";
	 * deliverableTable = ""; deliverableField = "";
	 * 
	 * mappingId = (Integer)mapping[0]; masterTable = (String) mapping[1];
	 * masterField = (String) mapping[2]; deliverableTable = (String)
	 * mapping[3];deliverableField = (String) mapping[4];
	 * 
	 * List<String> masterDataValList = getTableColData(masterTable,masterField);
	 * List<String> deliverbleDataValList =
	 * getTableColData(deliverableTable,deliverableField);
	 * 
	 * maxLoop = deliverbleDataValList.size();
	 * 
	 * if(masterDataValList.size() > deliverbleDataValList.size()) { maxLoop =
	 * masterDataValList.size() ; }
	 * 
	 * for(int i = 0; i< maxLoop; i++) {
	 * 
	 * String masterData = "", deliberableData = "";
	 * 
	 * if(masterDataValList.size() > i) { masterData = masterDataValList.get(i); }
	 * if(deliverbleDataValList.size() > i){ deliberableData =
	 * deliverbleDataValList.get(i); }
	 * 
	 * //Added on 04-10-2021 START
	 * entityManager.createNativeQuery("insert into consistency_tracking_table_"
	 * +userId+" (key_field, mappingid, deliverable_name, master_data, deliverable_data) values(?,?,?,?,?)"
	 * ) .setParameter(1,"Dummy_KeyField" ) .setParameter(2, mappingId)
	 * .setParameter(3,deliverableField ) .setParameter(4,masterData )
	 * .setParameter(5,deliberableData ) .executeUpdate(); //Added on 04-10-2021 END
	 * 
	 * //Commented on 04-10-2021 START //jsonObjectVal = new JSONObject();
	 * 
	 * //jsonObjectVal.put("MAPPING_ID",mappingId);
	 * //jsonObjectVal.put("KEY_FIELD","ABC");
	 * //jsonObjectVal.put("DELIVERABLE_NAME",deliverableField);
	 * //jsonObjectVal.put("MASTER_DATA", masterData);
	 * //jsonObjectVal.put("DELIVERABLE_DATA",deliberableData);
	 * 
	 * //jsonArray.add(jsonObjectVal);
	 * 
	 * //Commented on 04-10-2021 END
	 * 
	 * }
	 * 
	 * 
	 * } //Commented on 04-10-2021 START //jsonObject.put("MAPPING_DATA",
	 * jsonArray); //response = jsonObject.toString(); //Commented on 04-10-2021 END
	 * response = getTempTableData(userId);//Added on 04-10-2021 START
	 * 
	 * return response; }
	 * 
	 * //Added on 04-10-2021 START private String getTempTableData(String userId) {
	 * 
	 * String response = ""; JSONObject jsonObject = new JSONObject(); JSONArray
	 * jsonArray = new JSONArray(); JSONObject jsonObjectVal = null; String sql =
	 * "select key_field, mappingid, deliverable_name, master_data, deliverable_data from consistency_tracking_table_"
	 * +userId+" order by id"; List<String[]>dataList = null; Session session =
	 * null; if (entityManager == null || (session =
	 * entityManager.unwrap(Session.class)) == null) { throw new
	 * NullPointerException(); }
	 * 
	 * Query query = session.createNativeQuery(sql);
	 * 
	 * dataList = query.getResultList(); for(String[] data: dataList) {
	 * jsonObjectVal = new JSONObject();
	 * 
	 * 
	 * jsonObjectVal.put("KEY_FIELD",data[0]);
	 * jsonObjectVal.put("MAPPING_ID",data[1]);
	 * jsonObjectVal.put("DELIVERABLE_NAME",data[2]);
	 * jsonObjectVal.put("MASTER_DATA", data[3]);
	 * jsonObjectVal.put("DELIVERABLE_DATA",data[4]);
	 * 
	 * jsonArray.add(jsonObjectVal); } jsonObject.put("MAPPING_DATA", jsonArray);
	 * response = jsonObject.toString(); return response; }
	 * 
	 * 
	 * @PersistenceContext private EntityManager entityManager;
	 * 
	 * 
	 * @Transactional(rollbackOn = Exception.class) public boolean
	 * createTable(String userId) throws Exception { Session session = null;
	 * 
	 * String sql = "CREATE TABLE [consistency_tracking_table_"
	 * +userId+"]( [id] INT IDENTITY(1,1) PRIMARY KEY, " +
	 * "[key_field] VARCHAR(1000),[mappingid] VARCHAR(50), [deliverable_name] VARCHAR(750), "
	 * + "[master_data] VARCHAR(150) , [deliverable_data] VARCHAR(150))";
	 * 
	 * if (entityManager == null || (session = entityManager.unwrap(Session.class))
	 * == null) { throw new NullPointerException(); }
	 * 
	 * int i = session.createSQLQuery(sql).executeUpdate();
	 * 
	 * if(i > 0 ) { System.out.println("#### Create Table Count "+i); return true;
	 * 
	 * }else { throw new Exception(); } }
	 * 
	 * @Modifying
	 * 
	 * @Transactional public boolean dropTable(String userId) throws Exception {
	 * Session session = null; String sql =
	 * "drop table [consistency_tracking_table_"+userId+"]"; if (entityManager ==
	 * null || (session = entityManager.unwrap(Session.class)) == null) { throw new
	 * NullPointerException(); }
	 * 
	 * int i = session.createSQLQuery(sql).executeUpdate();
	 * System.out.println("#### dopTable count "+i); return true; }
	 * 
	 * public List<String> getTableColData(String tableName, String colName) throws
	 * Exception { List<String> dataList = null; Session session = null; int i = 0;
	 * 
	 * if (entityManager == null || (session = entityManager.unwrap(Session.class))
	 * == null) { throw new NullPointerException(); }
	 * 
	 * String sql =
	 * "select "+checkNull(colName)+" from "+checkNull(tableName)+" order by "
	 * +checkNull(tableName)+"_id"; dataList =
	 * session.createSQLQuery(sql).getResultList();
	 * 
	 * return dataList; }
	 * 
	 * public String checkNull(String input) {
	 * System.out.println("#### Input String ["+input+"]"); if(input == null ||
	 * "null".equalsIgnoreCase(input) || "undefined".equalsIgnoreCase(input)) input
	 * = ""; return input.trim(); }
	 */

}
