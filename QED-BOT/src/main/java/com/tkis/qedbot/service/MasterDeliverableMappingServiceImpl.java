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

	@Override
	public List<String> getMappedDeliverableFieldByKeyField(int projectId, String filterKeyField, String userId) {
		List<String> columnNameList = null;
		columnNameList = masterDeliverableMappingRepo.getMappedDeliverableFieldByKeyField(projectId,filterKeyField);
		return columnNameList;
	}

}
