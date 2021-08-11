package com.tkis.qedbot.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tkis.qedbot.repo.RepositoryDetailsCRUD;
import com.tkis.qedbot.service.FileReaderService;
import com.tkis.qedbot.service.TableModificationService;

@Controller
public class AdminController {

	@Autowired
	FileReaderService fileReaderService;
	
	@RequestMapping("/modifyTable")
	public String modifyTable(Model model){
		
		List<String> projectList = fileReaderService.getProjectList();		
		model.addAttribute("projectList", projectList);
		
		
		return "modifyTable";
	}
	
	

	@Autowired
	RepositoryDetailsCRUD repoDetailsCRUD;

	@RequestMapping(value = "/getTableNames", method = RequestMethod.POST)
	@ResponseBody
	public 	List<String> getTableName(@RequestParam("projectName") String projectName ) 
	{
		
		System.out.println("#### projectName "+projectName);
		
		List<String> tableList = repoDetailsCRUD.getAllTablesName(projectName);
		
		System.out.print("#### tableList"+tableList);
		
		return tableList;
	}	
	

	@Autowired
	TableModificationService tableModificationService;
	
	@ResponseBody		
	@RequestMapping(value = "/getTableStructure", method = RequestMethod.POST)
	public List<String> getTable(@RequestParam ("tableName") String tableName) {
		
		System.out.println("#### tableName "+tableName); 
		try 
		{
			tableName = URLDecoder.decode(tableName, "UTF-8");
			
		} catch (UnsupportedEncodingException e) {	
			
			e.printStackTrace();
		}
		List<String> columnList = tableModificationService.findAllColumns(tableName);
		
		System.out.print("#### columnList "+columnList);
		
		return columnList;
	}
	
	@ResponseBody
	@RequestMapping("/alterTable")
	public List<String> getAlterTable(@RequestParam ("tableName") String tableName, @RequestParam ("columnName") String colName){
		
		System.out.println("#### tableName "+tableName);
		System.out.println("#### colName "+colName);
		
		
		try {
			tableName = URLDecoder.decode(tableName, "UTF-8");
			colName = URLDecoder.decode(colName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		List<String> columnList = tableModificationService.alterTable(tableName, colName);
		
		System.out.print("#### columnList "+columnList);
		
		return columnList;
		
	}
	
	@ResponseBody
	@RequestMapping("/getFileName")
	public String getFileName( @RequestParam ("tableName") String tableName){
		String fileName = "";
		System.out.println("#### tableName "+tableName);
		try 
		{			
			tableName = URLDecoder.decode(tableName, "UTF-8");
			fileName = repoDetailsCRUD.getFileName(tableName);
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		
		System.out.println("#### fileName "+fileName);
		return fileName;
		
	}
	
	@ResponseBody
	@RequestMapping("/updateFileName")
	public String updateFileName(@RequestParam ("fileName") String fileName, @RequestParam ("tableName") String tableName){
		
		System.out.println("#### fileName "+fileName);
		System.out.println("#### tableName "+tableName);
		try 
		{
			fileName = URLDecoder.decode(fileName, "UTF-8");
			tableName = URLDecoder.decode(tableName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		 
		
		if(repoDetailsCRUD.updateFileName(fileName, tableName) > 0) {
			
			return fileName;
		}
		
		return "";
		
	}
	
	/* Rule Managment Added on 10-08-2021 Start*/
	@RequestMapping("/createRule")
	public String createRule(Model model){
		
		List<String> projectList = fileReaderService.getProjectList();		
		model.addAttribute("projectList", projectList);
		
		
		return "createRule";
	}
	
	
	/* Rule Managment Added on 10-08-2021 End*/
	
}
