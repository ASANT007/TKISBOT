package com.tkis.qedbot.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.repository.query.Param;


public interface RepositoryDetailsService 
{
	
	
	//public List<String> getAllTablesName(@Param("filterTableNames") String filterTableNames) throws Exception;
	
	
	public String getAllTablesByProjectId(int projectId) throws Exception;
	
	
    int updateFileName(String fileName, String lastUpdatedBy, Timestamp lastUpdationDate, int repositoryId) throws Exception;
	
	
	public String getFileName(int repositoryId) throws Exception;


	public String getTableNameFromRepositoryId(int repositoryId) throws Exception;
	
	
	//User START
	List<Object[]> getKeyFieldByProjectId(int projectId) throws Exception;
	//User END
	

}
