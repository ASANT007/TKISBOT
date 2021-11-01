package com.tkis.qedbot.service;

import java.sql.Timestamp;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkis.qedbot.repo.RepositoryDetailsRepo;

@Service
public class RepositoryDetailsServiceImpl implements RepositoryDetailsService 
{
	private static final Logger log = LoggerFactory.getLogger(RepositoryDetailsServiceImpl.class);
	@Autowired
	RepositoryDetailsRepo repositoryDetailsRepo;
	
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
	public int updateFileName(String fileName, String lastUpdatedBy, Timestamp lastUpdationDate, int repositoryId) throws Exception {
		
		return repositoryDetailsRepo.updateFileName(fileName, lastUpdatedBy, lastUpdationDate, repositoryId);
	}

	@Override
	public String getFileName(int repositoryId) throws Exception {
		
		return repositoryDetailsRepo.getFileName(repositoryId);
	}

	@Override
	public String getTableNameFromRepositoryId(int repositoryId) throws Exception {
		
		return repositoryDetailsRepo.getTableNameFromRepositoryId(repositoryId);
	}

	@Override
	public List<Object[]> getKeyFieldByProjectId(int projectId) throws Exception {
		
		return repositoryDetailsRepo.findByProjectId(projectId);
		//return null;
	}
	
	

}
