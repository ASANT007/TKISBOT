package com.tkis.qedbot.service;

import java.util.List;

public interface TableModificationService {
	
	//public List<String> getTableNames(String projectName, String tableTpye, String tableName);
	
	public List<String> findAllColumns(String tableName);
	
	public List<String> alterTable(String tableName, String colName);
	

}
