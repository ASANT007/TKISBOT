package com.tkis.qedbot.service;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.monitorjbl.xlsx.StreamingReader;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.tkis.qedbot.dao.CustomTableDao;
import com.tkis.qedbot.entity.RepositoryDetails;

@Service
public class CreateTableServiceImpl implements CreateTableService 
{	
	private static final Logger log = LoggerFactory.getLogger(CreateTableServiceImpl.class);
	private StringBuffer tableDesignBuff = null;
		private ArrayList<String> arraylist = null;
			private ArrayList<String> duplicateColList = null;
				private ArrayList<String> invalidColList = null;
					

	@Autowired
	private CustomTableDao customTableDao;
	
	
	@Override
	public List<String> getProjectList() {
		
		return customTableDao.getProjectList();
	}
	
	
	@Override
	public String genrateTableStructure(MultipartFile file, String projectName, String deliverableTypeName, String tableType) throws Exception
	{
		
		String response = "", extension = "", fileName = "", tableName = "";
		
		boolean isPresnt = false;
		
		arraylist = new ArrayList<>();
		duplicateColList = new ArrayList<>();
		invalidColList = new ArrayList<>();		

		fileName = file.getOriginalFilename();
		tableName = getTableName(fileName, projectName, deliverableTypeName, tableType);
		
		isPresnt = customTableDao.isTablePresent(tableName);
		
		if(!isPresnt) 
		{						
			startTableDesign(tableName);
			
			extension = FilenameUtils.getExtension(file.getOriginalFilename());
			
			if( extension.equalsIgnoreCase("csv") ) {
				
				response = readDataFromCSV(file, tableName, projectName, tableType);
				
			}else if( extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx")) {
				
				response = readDataFromExcelSheet(file, tableName, projectName, tableType, extension);
			} 
			else {
				
				response = "<div class='py-3 text-center'>Invalid file Type <span style='color:red'>"+extension+"</span></div>";
			}
			
		}else {
			
			response =  "<div class='py-3 text-center'>Table <span style='color:red'> "+tableName+"</span> is Already Present</div>";
		}
				
		return response;
	}

	
	private String getTableName(String fileName, String projectName, String deliverableTypeName, String tableType) {
		
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
			    
				tableName = projectName+"_"+deliverableTypeName+"_"+tableType+"_"+tableName;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    	
		
		return tableName;
	}


	private String readDataFromCSV(MultipartFile file, String tableName, String projectName, String tableType) throws IOException, Exception 
	{
		String response = "";
		
		System.out.print("#### Reading CSV File ");
		
		InputStreamReader reader = new InputStreamReader(file.getInputStream());
		
		CSVReader csvReader = new CSVReaderBuilder(reader).build();
		
		String [] nextRecord;
		
		sheetRow:
		while((nextRecord = csvReader.readNext()) != null) {
			
			int srNo = 0;
			for(String cellValue : nextRecord) {
				srNo ++;
				System.out.println("#### CSV File Row "+cellValue);	
				validateAndCreateColumns(srNo, cellValue);
			}
			
			break sheetRow;
		}
			
		response = getNewTable(tableName, tableType); 			
		
		return response;
	}
	
	private String readDataFromExcelSheet(MultipartFile file, String tableName, String projectName, String tableType, String extension) throws Exception
	{
		String response = "";
		if(extension.equals("xls")) {
			Workbook workbook = new HSSFWorkbook(file.getInputStream());
			response = getExcelReadResponse(workbook, tableName, file.getOriginalFilename(), projectName, tableType);	
		}else 
		{
			
			try (
					InputStream fileInputStream = file.getInputStream();
					Workbook workbook = StreamingReader.builder()
							.rowCacheSize(10)
							.bufferSize(4096)
							.open(fileInputStream)
				)				
				{
					response = getExcelReadResponse(workbook, tableName, file.getOriginalFilename(), projectName, tableType);
					
				}catch (FileNotFoundException e) {						
					e.printStackTrace();
				} catch (IOException e) {						
					e.printStackTrace();
				}
		}
		
		
		return response;
	}
	
	private String getExcelReadResponse(Workbook workbook, String tableName, String fileName, String projectName, String tableType) throws Exception 
	{
		
		String response = "";
		
		try 
		{
			//Added on 09-09-2021 START
			Iterator<Sheet> sheetItr = workbook.sheetIterator();
			Sheet sheet = null;
			int i = 0;
			sheetLoop :
			while(sheetItr.hasNext()) {
				
				sheet = sheetItr.next();
				
				System.out.println("Sheet name : "+workbook.getSheetName(i)+" Is Sheet Hidden : "+workbook.isSheetVeryHidden(i));
				if(workbook.isSheetHidden(i) || workbook.isSheetVeryHidden(i)) {
					
				}else {					
					sheet = workbook.getSheetAt(i);
					break sheetLoop;
				}
				
				i++;
			}
			//Added on 09-09-2021 END
			//Sheet sheet = workbook.getSheetAt(0);
			
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
					
					validateAndCreateColumns(srNo, cellValue);
					
				}
				
				break rowReader;
			}
			
			response = getNewTable(tableName, tableType); //callMethod
			
		} catch (IOException e) 
		{			
			System.out.println("#### Exception :: getExcelReadResponse : "+e);
		}finally {
			
			try {
				workbook.close();
			} catch (IOException e) {
				
				System.out.println("#### Exception 2 :: getExcelReadResponse : "+e);
			}
		}
		
		
		return response;
	}
	
	private void validateAndCreateColumns(int srNo, String cellValue) {	
         	
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
					
				addTableAttribute(cellValue, srNo);
				
			 }
        	  
		} 
        catch (Exception e) 
        {			
			e.printStackTrace();
		}
		
	}

	// Single time
	private void startTableDesign(String tableName) 
	{
		tableDesignBuff = new StringBuffer();
		
		tableDesignBuff.append("<div class='py-3 text-center'>Table ");
		tableDesignBuff.append("<span class='blue-txt' id='tablename' name='tablename'>"+tableName+"</span>");
		tableDesignBuff.append(" will be created with following fields </div>");    	
		tableDesignBuff.append("<div style='width:50%; margin:0 auto;'>");
	
		tableDesignBuff.append("<div class='row'><div class='col-md-12 mb-3' style='text-align:right'>");
		tableDesignBuff.append("<input id ='createTable' name = 'createTable' class='btn btn-primary mx-2' type ='button' value ='Create Table' onClick='window.saveTableStructure()' />"); 
		tableDesignBuff.append("</div></div>");
		
		tableDesignBuff.append("<table class='table table-responsive table-bordered table-striped'><tr><th style='width:15%; text-align:center;'>Sr No</th><th>Field Name</th><th>Key Field</th></tr>");		
		
	}
	
	//Code Execute in loop
	private void addTableAttribute(String cellValue, int srNo ) 
	{
			
		tableDesignBuff.append("<tr><td align='center'>");
		tableDesignBuff.append(srNo);
		tableDesignBuff.append("</td>");
		tableDesignBuff.append("<td>");
		tableDesignBuff.append(cellValue);
		tableDesignBuff.append("</td>");
		tableDesignBuff.append("<td><input type='checkbox'  name = 'keyfieldCheckBox' id='keyfield_"+srNo+"'  value='"+cellValue+"'>");
		tableDesignBuff.append("</td></tr>");
	}
	
	private String getNewTable(String tableName, String tableType) throws Exception{
		
		String responseStr = "";	
		
		tableDesignBuff.append("</table></div>"); // Single time
		System.out.println("#### invalidColList"+invalidColList.size());
		if(invalidColList.size() > 0 ) {
			responseStr = "<div class='row py-2'><div class='col-md-2'>Column Size is too large : </div><div class='col-md-10'> <span style='color:red'>"+invalidColList+"</span></div></div>";				
		}
		if(duplicateColList.size() > 0) 
		{
			responseStr = responseStr+"<div  class='row pt-2'><div class='col-md-2'>Duplicate Column : </div><div class='col-md-10'>  <span style='color:red'>"+duplicateColList+"<span></div></div>";				
		}
		
		//Added on 26-10-2021 START
		if(invalidColList.size() > 0 || duplicateColList.size() > 0) {
			return responseStr;
		}//Added on 26-10-2021 END
		else{
			
			responseStr = tableDesignBuff.toString();
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


	@Override
	public String saveTableStructure(String userId, int projectId, String tableType, String fileName,
			String tableName, String columnArray, String keyField) throws Exception 
	{
		
		String response = "", createTableSQL = "";
		
		RepositoryDetails repoDetails = new RepositoryDetails();
		
		repoDetails.setProjectId(projectId); 
		repoDetails.setFileName(fileName);
		repoDetails.setTablesName(tableName);
		repoDetails.setKeyField(keyField);
    	repoDetails.setTableTypes(tableType);
    	repoDetails.setCreatedBy(userId); 
    	repoDetails.setCreationDate(new Timestamp(new Date().getTime()));


    	StringBuffer stringBuffer = new StringBuffer("create table ");
    	stringBuffer.append(tableName);			
    	stringBuffer.append("( ["+tableName+"_id] int identity(1,1) primary key,");
		
		String columnDataArray [] = columnArray.split(",");
		
		for(String columnName : columnDataArray) {
			
			stringBuffer.append("[");// Required to MSSQL
			stringBuffer.append(columnName);
			stringBuffer.append("]");// Required to MSSQL
			stringBuffer.append(" ");
			stringBuffer.append("varchar(750),");
		}
		
		createTableSQL = stringBuffer.toString();
		createTableSQL = createTableSQL.substring(0, createTableSQL.length()-1);
		createTableSQL = createTableSQL+")";
		
		if(customTableDao.createTable(createTableSQL, repoDetails))
		{ 
			response = "<div class='py-3 text-center'>Table <span style='color:green'>"+tableName+"</span> Created Successfully</div>";
		}
		
		return response;
	}
	
	
}
