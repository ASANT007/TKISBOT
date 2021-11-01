package com.tkis.qedbot.service;

import java.util.List;

public interface TableModificationService {
	
	public List<String> findAllColumns(String tableName);
	
	public List<String> alterTable(String tableName, String colName);

	public String getkeyfield(String tablename) throws Exception;

	public String updateKeyField(String tableName, String keyField) throws Exception;
	

}
