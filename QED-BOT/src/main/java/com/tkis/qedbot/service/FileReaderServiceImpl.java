package com.tkis.qedbot.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.monitorjbl.xlsx.StreamingReader;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import com.tkis.qedbot.dao.CustomTableDao;
import com.tkis.qedbot.entity.RepositoryDetails;

@Component
public class FileReaderServiceImpl implements FileReaderService 
{	
	
	private StringBuffer tableDesignBuff = null;
		private ArrayList<String> arraylist = null;
			private ArrayList<String> duplicateColList = null;
				private ArrayList<String> invalidColList = null;
					private RepositoryDetails repoDetails = null;

	@Autowired
	private CustomTableDao customTableDao;
	
	
	@Override
	public List<String> getProjectList() {
		
		return customTableDao.getProjectList();
	}
	
	
	@Override
	public String upload(MultipartFile file, String projectName, String tableType, String userId, boolean isSave) {
		
		String response = "";
		
		String extension = "", fileName = "", tableName = "";
		
		boolean isPresnt = false;
		
		tableDesignBuff = new StringBuffer();
		arraylist = new ArrayList<>();
		duplicateColList = new ArrayList<>();
		invalidColList = new ArrayList<>();
		repoDetails = new RepositoryDetails();
		
		try 
		{
			fileName = file.getOriginalFilename();
			tableName = getTableName(fileName, projectName, tableType);
			
			isPresnt = customTableDao.isTablePresent(tableName);
			
			if(!isPresnt) 
			{
				if(isSave) {
					System.out.println("#### tableName "+tableName);
					System.out.println("#### tableType "+tableType);
					System.out.println("#### userId ["+userId+"]");
					repoDetails.setFileName(fileName);
					repoDetails.setTablesName(tableName);
		        	repoDetails.setTableTypes(tableType);
		        	repoDetails.setUserId("ASANT");//get It from session or send via method call
		        	repoDetails.setDate(new Timestamp(new Date().getTime()));
				}
				
				
				startTableDesign(tableName, isSave);
				
				extension = FilenameUtils.getExtension(file.getOriginalFilename());
				
				if( extension.equalsIgnoreCase("csv") ) {
					
					response = readDataFromCSV(file, tableName, projectName, tableType, userId, isSave);
					
				}else if( extension.equalsIgnoreCase("xls") ) {
					
					response = readDataFromXLS(file, tableName, projectName, tableType, userId, isSave);
				} 
				else if( extension.equalsIgnoreCase("xlsx") ) {
					
					response = readDataFromXLSX(file, tableName, projectName, tableType, userId, isSave);
					
				}else {
					
					response = "<div class='py-3 text-center'>Invalid file Type <span style='color:red'>"+extension+"</span></div>";
				}
				
			}else {
				
				response =  "<div class='py-3 text-center'>Table <span style='color:red'> "+tableName+"</span> is Already Present</div>";
			}
		}catch (IllegalArgumentException e) 
		{			
			e.printStackTrace();
			
		}catch (Exception e) 
		{			
			e.printStackTrace();
		}
		
		
		return response;
	}

	
	private String getTableName(String fileName, String projectName, String tableType) {
		
		String tableName = "";
		
		try 
		{
			tableName = fileName.substring(0, fileName.lastIndexOf(".")).toLowerCase();
			
			tableName = checkNull(tableName);
			 
			if( tableName.length() >0 ) {
				
				tableName = tableName.toLowerCase();
				tableName = tableName.replaceAll("[^a-zA-Z0-9]", "_");
				
				int lastCharcter = tableName.length();        	
			    
			    if(tableName.endsWith("_")) {
			 	   
			    	tableName = tableName.substring(0, lastCharcter-1);
			    }
			    if(tableName.startsWith("_")){
			 	   	 
			    	tableName = tableName.substring(1, lastCharcter);
			    }
			    
				tableName = projectName+"_"+tableType+"_"+tableName;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    	
		
		return tableName;
	}


	private String readDataFromCSV(MultipartFile file, String tableName, String projectName, String tableType, String userId, boolean isSave) 
	{
		String response = "";
		
		System.out.print("#### Reading CSV File ");
		
		try 
		{
			
			InputStreamReader reader = new InputStreamReader(file.getInputStream());
			
			CSVReader csvReader = new CSVReaderBuilder(reader).build();
			
			String [] nextRecord;
			
			sheetRow:
			while((nextRecord = csvReader.readNext()) != null) {
				
				int srNo = 0;
				for(String cellValue : nextRecord) {
					srNo ++;
					System.out.println("#### CSV File Row "+cellValue);		
					
					validateAndCreateColumns(srNo, cellValue, isSave);
					
					
				}
				
				break sheetRow;
			}
			
			response = getNewTable(tableName, tableType, userId, isSave); 			
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return response;
	}
	
	private String readDataFromXLS(MultipartFile file, String tableName, String projectName, String tableType, String userId, boolean isSave) 
	{
		String response = "";
		
		System.out.print("#### readDataFromXLS ");
		
		try 
		{	
			Workbook workbook = new HSSFWorkbook(file.getInputStream());
			
			String fileName = file.getOriginalFilename();
			
			response = getExcelReadResponse(workbook, tableName, fileName, projectName, tableType, userId,isSave);			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return response;
	}

	

	private String readDataFromXLSX(MultipartFile file, String tableName, String projectName, String tableType, String userId, boolean isSave)
	{
		String response = "";
		
		System.out.print("#### readDataFrom XLSX ");
		try (
				InputStream fileInputStream = file.getInputStream();
				Workbook workbook = StreamingReader.builder()
						.rowCacheSize(10)
						.bufferSize(4096)
						.open(fileInputStream)
				)
			
				{
					String fileName = file.getOriginalFilename();
					
					response = getExcelReadResponse(workbook, tableName, fileName, projectName, tableType, userId, isSave);
					
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
		
		return response;
	}
	
	private String getExcelReadResponse(Workbook workbook, String tableName, String fileName, String projectName, String tableType, String userId, boolean isSave) 
	{
		
		String response = "";
		
		try 
		{
			Sheet sheet = workbook.getSheetAt(0);
			
			Iterator<Row> rowIterator = sheet.rowIterator();
			
			DataFormatter dataFormatter = new DataFormatter();
			
			int srNo = 0;
			
			rowReader:
			while(rowIterator.hasNext()) 
			{
				Row row = rowIterator.next();
				
				Iterator<Cell> cellIterator = row.cellIterator();
				
				while(cellIterator.hasNext()) {
					
					srNo ++;
					
					Cell cell = cellIterator.next();
					
					String cellValue = dataFormatter.formatCellValue(cell);			
					
					System.out.println("#### cellValue "+cellValue);
					
					validateAndCreateColumns(srNo, cellValue, isSave);
					
					//START					
					//END
				}
				
				break rowReader;
			}
			
			response = getNewTable(tableName, tableType, userId, isSave); //callMethod
			
		} catch (Exception e) 
		{
			
			e.printStackTrace();
		}finally {
			
			try {
				workbook.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		
		return response;
	}
	
	private void validateAndCreateColumns(int srNo, String cellValue, boolean isSave) {	
         	
         try 
         {
        	  if(cellValue.length() > 128) 
        	  {	
     			
     				invalidColList.add(cellValue);
        	  }
        	  if(cellValue.length() > 0)  
        	  {		                   
			       
        		  	if(arraylist.contains(cellValue)) 
					{   
						duplicateColList.add(cellValue);	
					}
					else 
					{ 	 							  
						arraylist.add(cellValue); 
					}
        		  
				 	cellValue = cellValue.toLowerCase();
				 	
				 	cellValue = cellValue.replaceAll("[^a-zA-Z0-9]", "_");
	      
				 	int lastChar = cellValue.length();
	      
					if(cellValue.endsWith("_")) {
				   
						cellValue = cellValue.substring(0, lastChar-1);
					}
					if(cellValue.startsWith("_")){
				   	 
						cellValue = cellValue.substring(1, lastChar);
					}					
					
					
				addTableAttribute(cellValue, srNo, isSave);
				
			 }
        	  
		} 
        catch (Exception e) 
        {			
			e.printStackTrace();
		}
		
	}


	private void startTableDesign(String tableName, boolean isSave) {

		if(isSave) {
			
			tableDesignBuff = new StringBuffer("create table ");
			tableDesignBuff.append(tableName);
			tableDesignBuff.append("( ["+tableName+"_id] int identity(1,1) primary key,");	
			
		}else {
			
			
			tableDesignBuff.append("<div class='py-3 text-center'>Table ");
			tableDesignBuff.append("<span class='blue-txt'>"+tableName+"</span>");
			tableDesignBuff.append(" will be created with following fields </div>");    	
			tableDesignBuff.append("<div style='width:50%; margin:0 auto;'>");
			
		
			  tableDesignBuff.append("<div class='row'><div class='col-md-12 mb-3' style='text-align:right'>");
			  tableDesignBuff.append("<input id ='createTable' name = 'createTable' class='btn btn-primary mx-2' type ='button' value ='Create Table' onClick='window.createTable()' />"); 
			  tableDesignBuff.append("</div></div>");
			
			
			tableDesignBuff.append("<table class='table table-responsive table-bordered table-striped'><tr><th style='width:15%; text-align:center;'>Sr No</th><th>Field Name</th></tr>");
		}
		
	}
	
	private void addTableAttribute(String cellValue, int srNo, boolean isSave ) 
	{
		if(isSave) {
			
			tableDesignBuff.append("[");
			tableDesignBuff.append(cellValue);
			tableDesignBuff.append("]");
			tableDesignBuff.append(" ");
			tableDesignBuff.append("varchar(200),");
              
		}else {
			
			tableDesignBuff.append("<tr><td align='center'>");
			tableDesignBuff.append(srNo);
			tableDesignBuff.append("</td>");
			tableDesignBuff.append("<td>");
			tableDesignBuff.append(cellValue);
			tableDesignBuff.append("</td></tr>");
			
		}
	}
	
	private String getNewTable(String tableName, String tableType, String userId, boolean isSave) {
		
		String responseStr = "";
		
		if(isSave) 
		{			
        	
			String createSQL = tableDesignBuff.toString();
            createSQL = createSQL.substring(0, createSQL.length()-1);
            createSQL = createSQL+")";
			if(customTableDao.createTable(createSQL, repoDetails))
			{ 
				responseStr = "<div class='py-3 text-center'>Table <span style='color:green'>"+tableName+"</span> Created Successfully</div>";
			}
		}
		else 
		{
			tableDesignBuff.append("</table></div>");
			/*
			tableDesignBuff.append("</table><div class='row'><div class='col-md-12' style='text-align:right'>");
			tableDesignBuff.append("<input id ='createTable' name = 'createTable' class='btn btn-primary mx-2' type ='button' value ='Create Table' onClick='window.createTable()' />");
			tableDesignBuff.append("</div></div></div>");
			*/
			
			if(invalidColList.size() > 0 ) {
				
				responseStr = "<div class='row py-2'><div class='col-md-2'>Column Size is too large : </div><div class='col-md-10'> <span style='color:red'>"+invalidColList+"</span></div></div>";
				//responseStr = "<div  class='row py-3 text-center'>Column Size is too large : <span style='color:red'>"+invalidColList+"<span></div>";
			}
			if(duplicateColList.size() > 0) 
			{
				
				responseStr = responseStr+"<div  class='row pt-2'><div class='col-md-2'>Duplicate Column : </div><div class='col-md-10'>  <span style='color:red'>"+duplicateColList+"<span></div></div>";
				
			}else {
				
				responseStr = tableDesignBuff.toString();
			}
			
		} 
		
		return responseStr;
	}
	
	private String checkNull(String input)
    {
	    if(input == null || "null".equalsIgnoreCase(input) || "undefined".equalsIgnoreCase(input)) {
	    	
	    	input = "";
	    }
        
        return input.trim();    
    }
	

}
