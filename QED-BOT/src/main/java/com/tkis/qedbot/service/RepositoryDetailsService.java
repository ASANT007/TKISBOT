package com.tkis.qedbot.service;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public interface RepositoryDetailsService 
{
	
	
	//public List<String> getAllTablesName(@Param("filterTableNames") String filterTableNames) throws Exception;
	
	
	public String getAllTablesByProjectId(@Param("projectId") int projectId) throws Exception;
	
	
    int updateFileName(@Param("fileName") String fileName, @Param("repositoryId") int repositoryId) throws Exception;
	
	
	public String getFileName(@Param("repositoryId") int repositoryId) throws Exception;


	public String getTableNameFromRepositoryId(int repositoryId) throws Exception;

}
