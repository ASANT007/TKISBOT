package com.tkis.qedbot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tkis.qedbot.dao.CustomTableDao;

@Component
public class TableModificationServiceImpl implements TableModificationService 
{
	
	
	@Autowired
	private CustomTableDao customTableDao;

	/*
	@Override
	public List<String> getTableNames(String projectName, String tableTpye, String tableName) {
		
		return null;
	}
	*/
	
	@Override
	public List<String> findAllColumns(String tableName) {
	
		return customTableDao.findAllColumns(tableName);
	}
	
	@Override
	public List<String> alterTable(String tableName, String colName) {
		
		colName = getCleanColValue(colName);
		
		System.out.println("#### cleasn colName "+colName);
		
		return customTableDao.alterTable(tableName, colName);
	}

	private String getCleanColValue(String colName) {
		try 
        {
			  colName = checkNull(colName);
		 
	       	  if(colName.length() > 0)  
	       	  {	  	
	       		  
				 	colName = colName.toLowerCase();
				 	
				 	colName = colName.replaceAll("[^a-zA-Z0-9]", "_");
		  
				 	int lastChar = colName.length();
		  
					if(colName.endsWith("_")) {
				   
						colName = colName.substring(0, lastChar-1);
					}
					if(colName.startsWith("_")){
				   	 
						colName = colName.substring(1, lastChar);
					}	
					
				}
       	  
		} 
       catch (Exception e) 
       {			
			e.printStackTrace();
       }
		return colName;
	}
	
	private String checkNull(String input)
    {
	    if(input == null || "null".equalsIgnoreCase(input) || "undefined".equalsIgnoreCase(input)) {
	    	
	    	input = "";
	    }
        
        return input.trim();    
    }
	
}
