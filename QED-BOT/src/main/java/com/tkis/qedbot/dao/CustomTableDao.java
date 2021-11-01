package com.tkis.qedbot.dao;

import java.util.List;

import com.tkis.qedbot.entity.RepositoryDetails;

public interface CustomTableDao {
	
	public List<String> getProjectList();
	
	public boolean createTable(String createTableSQL, RepositoryDetails details) throws Exception;
	
	public boolean isTablePresent(String tableName);
	
	public List<String> findAllColumns(String tableName);
	
	List<String> alterTable(String tableName, String column);
	
	public int getRowCount(String tableName);

	public String getkeyfield(String tablename);

	public String updateKeyField(String tableName, String keyField);
	
}
