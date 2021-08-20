package com.tkis.qedbot.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tkis.qedbot.repo.RepositoryDetailsRepo;

@Component
public class RepositoryDetailsServiceImpl implements RepositoryDetailsService {
	
	@Autowired
	RepositoryDetailsRepo repositoryDetailsRepo;

	/*
	 * @Override public List<String> getAllTablesName(String filterTableNames)
	 * throws Exception { // TODO Auto-generated method stub return null; }
	 */

	@Override
	public String getAllTablesByProjectId(int projectId) throws Exception {
		
		List<Object[]> repoList = repositoryDetailsRepo.getAllTablesByProjectId(projectId);
		
		JSONArray arr = new JSONArray();
		JSONObject obj = null;
		
		for(Object[] repo : repoList) {
			
			int repositoryId = (Integer) repo[0];
			String tableName = (String) repo[1];
			
			obj = new JSONObject();
            obj.put("repositoryId",repositoryId);
            obj.put("tableName",tableName);
            arr.add(obj);
		}
		return arr.toString();
		
		
	}

	@Override
	public int updateFileName(String fileName, int repositoryId) throws Exception {
		
		return repositoryDetailsRepo.updateFileName(fileName, repositoryId);
	}

	@Override
	public String getFileName(int repositoryId) throws Exception {
		
		return repositoryDetailsRepo.getFileName(repositoryId);
	}

	@Override
	public String getTableNameFromRepositoryId(int repositoryId) throws Exception {
		
		return repositoryDetailsRepo.getTableNameFromRepositoryId(repositoryId);
	}
	
	

}
