package com.tkis.qedbot.dao;

import java.util.List;

import com.tkis.qedbot.entity.RepositoryDetails;

public interface CustomTableDao {
	
	public List<String> getProjectList();
	
	public boolean createTable(String createTableSQL, RepositoryDetails details);
	
	public boolean isTablePresent(String tableName);
	
	public List<String> findAllColumns(String tableName);
	
	List<String> alterTable(String tableName, String column);	
	
}
