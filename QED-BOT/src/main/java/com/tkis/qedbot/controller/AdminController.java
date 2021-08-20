package com.tkis.qedbot.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tkis.qedbot.repo.RepositoryDetailsRepo;
import com.tkis.qedbot.service.CreateTableService;
import com.tkis.qedbot.service.DeliverabletypeMasterService;
import com.tkis.qedbot.service.ProjectMasterService;
import com.tkis.qedbot.service.RepositoryDetailsService;
import com.tkis.qedbot.service.RuleMasterService;
import com.tkis.qedbot.service.TableModificationService;

@Controller
public class AdminController {

	@Autowired
	CreateTableService createTableService;
	
	@Autowired
	//RepositoryDetailsCRUD repoDetailsCRUD;
	RepositoryDetailsRepo repoDetailsRepo;
	
	@Autowired
	RepositoryDetailsService repositoryDetailsService;
	
	
	@Autowired
	TableModificationService tableModificationService;
	
	@Autowired
	RuleMasterService ruleMasterService;
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	DeliverabletypeMasterService deliverabletypeMasterService;	
	
	//Show create table page
	@RequestMapping("/createTable")
	public String createTable(Model model) 
	{		
		try 
		{
			model.addAttribute("deliverableType",deliverabletypeMasterService.getDeliverableIdAndShortName());
			
		} catch (Exception e) 
		{
			
			model.addAttribute("message", e.toString());
			e.printStackTrace();
		}
		return "createTable";
	}
	
	//Return projectList for deliverable type.
	@ResponseBody
	@RequestMapping("/getProjectsForDeliverableType")
	public List<Object[]> getProjects(@RequestParam("deliverableTypeId") String deliverableTypeId){
		List<Object[]> projectList = null;
		try 
		{
			int Id = Integer.valueOf(URLDecoder.decode(deliverableTypeId, "UTF-8"));
			projectList = projectMasterService.getProjectIdAndName(Id);
			
		} catch (Exception e) 
		{
			//model.addAttribute("message", e.toString());
			e.printStackTrace();
		}
		return projectList;
		
	}
	
	//Read excel, csv file and return table structure
	@ResponseBody
	@PostMapping("/uploadFile") public String uploadFile( @RequestParam("file") MultipartFile file,
	  @RequestParam("deliverableTypeName") String deliverableType, @RequestParam("projectname") String projectName,
	  @RequestParam("tabletype") String tabletype,  HttpSession session )
	{
		
		String responseStr = "";
		
		String userId = "";
		
		userId = checkNull((String) session.getAttribute("userId")); // Not Set Yet
		
		System.out.println("#### userId "+userId);
		if(userId.length() > 0) {
			
			responseStr = createTableService.upload(file, projectName, deliverableType, tabletype, userId, false);
			
			if(checkNull(responseStr).length() == 0) {
				
				responseStr = "<div class='py-3 text-center' style='color:red'> Please Check Excel File </div>";
			}
		}
		
		
		System.out.println("#### Upload Response ["+responseStr+"]");	
		
		return responseStr;
	}
	
	// Create Custom Table from Excel, Csv files End
	@ResponseBody
	@PostMapping("/saveTable") 
	public String createtable(@RequestParam("file") MultipartFile file,
	  @RequestParam("deliverableTypeName") String deliverableType, @RequestParam("projectname") String projectName,
	  @RequestParam("tabletype") String tabletype, HttpSession session) 
	{
		//String responseStr = "<div class='py-3 text-center' style='color:red'> Please Check Excel File </div>";
		String responseStr = "";
		String userId = "";
		
		userId = checkNull((String) session.getAttribute("userId")); // Not Set Yet
		
		if(userId.length() >0) {
			
			responseStr = createTableService.upload(file, projectName, deliverableType,tabletype, userId, true);
		}
		
		
				
		return responseStr;
		
	}
	
	//Returns Modify Table page
	@RequestMapping("/modifyTable")
	public String modifyTable(Model model){
		
		/*
		 * List<String> projectList = createTableService.getProjectList();
		 * 
		 * model.addAttribute("projectList", projectList);
		 */
		try 
		{
			model.addAttribute("deliverableType",deliverabletypeMasterService.getDeliverableIdAndShortName());
			
		} catch (Exception e) {
			model.addAttribute("message", e.toString());
			e.printStackTrace();
		}
		
		return "modifyTable";
	}	
	

	//Commented on 20-08-2021
	/*
	 * @RequestMapping(value = "/getTableNames", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public List<String> getTableName(@RequestParam("projectName")
	 * String projectName ) {
	 * 
	 * System.out.println("#### projectName "+projectName);
	 * 
	 * List<String> tableList = repoDetailsRepo.getAllTablesName(projectName);
	 * 
	 * System.out.print("#### tableList"+tableList);
	 * 
	 * return tableList; }
	 */	
	
	@ResponseBody		
	@RequestMapping(value = "/getTableStructure", method = RequestMethod.POST)
	public List<String> getTable(@RequestParam ("tableName") String tableName, Model model) {
		
		System.out.println("#### tableName "+tableName); 
		List<String> columnList = null;
		try 
		{
			tableName = URLDecoder.decode(tableName, "UTF-8");
			columnList = tableModificationService.findAllColumns(tableName);
			
		} catch (UnsupportedEncodingException e) {
			model.addAttribute("message", e.toString());
			e.printStackTrace();
		}catch(Exception e) {
			model.addAttribute("message", e.toString());
			e.printStackTrace();
		}
		
		System.out.print("#### columnList "+columnList);
		
		return columnList;
	}
	
	@ResponseBody
	@RequestMapping("/alterTable")
	public List<String> getAlterTable(@RequestParam ("tableName") String tableName,
			@RequestParam ("columnName") String colName, Model model){
		
		System.out.println("#### tableName "+tableName);
		System.out.println("#### colName "+colName);
		List<String> columnList = null;
		
		try 
		{
			tableName = URLDecoder.decode(tableName, "UTF-8");
			colName = URLDecoder.decode(colName, "UTF-8");
			tableModificationService.alterTable(tableName, colName);
			
		} catch (UnsupportedEncodingException e) {
			model.addAttribute("message", e.toString());
			e.printStackTrace();
		}catch(Exception e) {
			model.addAttribute("message", e.toString());
			e.printStackTrace();
		}
		 
		System.out.print("#### columnList "+columnList);
		
		return columnList;
		
	}
	
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping("/getFileName") public String getFileName( @RequestParam
	 * ("tableName") String tableName){ String fileName = "";
	 * System.out.println("#### tableName "+tableName); try { tableName =
	 * URLDecoder.decode(tableName, "UTF-8"); fileName =
	 * repoDetailsRepo.getFileName(tableName); } catch (UnsupportedEncodingException
	 * e) {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * System.out.println("#### fileName "+fileName); return fileName;
	 * 
	 * }
	 */
	

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping("/updateFileName") public String updateFileName(@RequestParam
	 * ("fileName") String fileName, @RequestParam ("tableName") String tableName){
	 * 
	 * System.out.println("#### fileName "+fileName);
	 * System.out.println("#### tableName "+tableName); try { fileName =
	 * URLDecoder.decode(fileName, "UTF-8"); tableName =
	 * URLDecoder.decode(tableName, "UTF-8"); } catch (UnsupportedEncodingException
	 * e) {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * 
	 * if(repoDetailsRepo.updateFileName(fileName, tableName) > 0) {
	 * 
	 * return fileName; }
	 * 
	 * return "";
	 * 
	 * }
	 */
	
	@ResponseBody
	@RequestMapping("/getFileName")
	public String getFileName( @RequestParam ("repositoryId") String repoId, Model model){
		String fileName = "";
		System.out.println("#### repositoryId "+repoId);
		try 
		{			
			int repositoryId = Integer.valueOf(URLDecoder.decode(repoId, "UTF-8"));
			fileName = repositoryDetailsService.getFileName(repositoryId);
			
		} catch (UnsupportedEncodingException e) {
			
			model.addAttribute("message", e.toString());
			e.printStackTrace();
		}catch (Exception e) {
			
			model.addAttribute("message", e.toString());
			e.printStackTrace();
		}
		
		System.out.println("#### fileName "+fileName);
		return fileName;
		
	}
	
	@ResponseBody
	@RequestMapping("/updateFileName")
	public String updateFileName(@RequestParam ("fileName") String fileName, @RequestParam ("repositoryId") String repoId, Model model){
		
		System.out.println("#### fileName "+fileName);
		System.out.println("#### repoId "+repoId);
		try 
		{
			fileName = URLDecoder.decode(fileName, "UTF-8");
			int repositoryId = Integer.valueOf(URLDecoder.decode(repoId, "UTF-8"));
			if(repoDetailsRepo.updateFileName(fileName, repositoryId) > 0) {
				
				return fileName;
			}
			
		} catch (UnsupportedEncodingException e) {
			
			model.addAttribute("message", e.toString());
			e.printStackTrace();
		}catch (Exception e) {
			
			model.addAttribute("message", e.toString());
			e.printStackTrace();
		}
		
		return "";
		
	}
	/* Rule Management Added Create Rule on 10-08-2021 Start*/
	/*
	 * @RequestMapping("/createRule") public String createRule(Model model){
	 * 
	 * List<String> projectList = fileReaderService.getProjectList();
	 * model.addAttribute("projectList", projectList);
	 * System.out.println("projectList "+projectList);
	 * 
	 * 
	 * return "createRule"; }
	 */
	
	//Gives projects id and name 	
	//https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/chapters/query/native/Native.html
	//@RequestMapping("/createRule")
	//public String createRule(@RequestParam("deliverableTypeId") String deliverableTypeId ,Model model){
		
		//List<Object[]> projectMaster = projectMasterRepo.getProjectIdAndName();
		/*
		 * List<Object[]> projectList = null; try {
		 * 
		 * int Id = Integer.valueOf(URLDecoder.decode(deliverableTypeId, "UTF-8"));
		 * projectList = projectMasterService.getProjectIdAndName(Id);
		 * 
		 * } catch (Exception e) {
		 * 
		 * model.addAttribute("message", e.toString()); }
		 */
		
		//return "ruleMgmnt_createRule";
	//}
	@RequestMapping("/createRule")
	public String createRule(Model model) 
	{		
		try 
		{
			model.addAttribute("deliverableType",deliverabletypeMasterService.getDeliverableIdAndShortName());
			
		} catch (Exception e) 
		{
			
			model.addAttribute("message", e.toString());
			e.printStackTrace();
		}
		return "ruleMgmnt_createRule";
	}
	
	//Gives tables as per projectId
	/*
	 * @RequestMapping(value = "/getAllTablesByProjectId", method =
	 * RequestMethod.POST)
	 * 
	 * @ResponseBody public List<String>
	 * getAllTablesByProjectId(@RequestParam("projectId") String Id ) {
	 * System.out.println("#### projectId "+Id); List<String> tableList = null;
	 * 
	 * try { int projectId = Integer.valueOf(Id); tableList =
	 * repoDetailsRepo.getAllTablesByProjectId(projectId); } catch
	 * (NumberFormatException e) {
	 * 
	 * e.printStackTrace();
	 * 
	 * }catch(Exception e) {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * System.out.print("#### tableList"+tableList);
	 * 
	 * return tableList; }
	 */
	@RequestMapping(value = "/getAllTablesByProjectId", method = RequestMethod.POST)
	@ResponseBody
	public String getAllTablesByProjectId(@RequestParam("projectId") String Id ) 
	{		
		System.out.println("#### projectId "+Id);		
		String response = "";
		
		try {
			int projectId = Integer.valueOf(Id);
			response = repositoryDetailsService.getAllTablesByProjectId(projectId);
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		System.out.print("#### response"+response);
		
		return response;
	}	
	@ResponseBody
	@RequestMapping("/saveRule")
	public String saveRule(@RequestParam ("projectId") String projectId, @RequestParam ("repositoryId") String repoId, 
			@RequestParam ("shortDesc") String shortDesc, @RequestParam ("ruleType") String ruleType, HttpSession session)
	{
		System.out.println("#### projectId "+projectId);
		System.out.println("#### shortDesc "+shortDesc);
		
		String response = "";
		String userId = "";
		
		userId = checkNull((String) session.getAttribute("userId"));
		try 
		{
			int proId = 0, repositoryId = 0;
			String query = "";
			if(userId.length() > 0) {
				
				proId = Integer.valueOf(URLDecoder.decode(projectId, "UTF-8"));	
				repositoryId = Integer.valueOf(URLDecoder.decode(repoId, "UTF-8"));	
				query = URLDecoder.decode(shortDesc, "UTF-8");
				ruleType = URLDecoder.decode(ruleType, "UTF-8");
				response = ruleMasterService.save(proId, repositoryId, query, ruleType, userId);
			}
			
			
		} catch (UnsupportedEncodingException e) {
			
			return e.toString();
			
		}catch(Exception e) {
			
			return e.toString();
		}
		return response;
	}
	
	// Rule Management Create Rule Added on 10-08-2021 End
	
	//Rule Management Added Execute Rule on 10-08-2021 Start
	@RequestMapping("/executeRule")
	public String executeRule(Model model){
		
		try {
			model.addAttribute("deliverableType",deliverabletypeMasterService.getDeliverableIdAndShortName());
			//model.addAttribute("projectList",projectMasterService.getProjectIdAndName());
			
		} catch (Exception e) {			

			model.addAttribute("message", e.toString());
			e.printStackTrace();
		} 
		
		
		return "ruleMgmnt_executeRule";
	}
	//Rule Management Added Execute Rule on 10-08-2021 End
	
	//Rule Management Added View  Rule on 10-08-2021 Start
	@RequestMapping("/viewRulePanel")
	public String viewRule(Model model){		
		
		try {
			model.addAttribute("deliverableType",deliverabletypeMasterService.getDeliverableIdAndShortName());
			
			//model.addAttribute("projectList",projectMasterService.getProjectIdAndName());
			
		} catch (NullPointerException e) {
			System.out.println("#### NullPointerException");
			model.addAttribute("message", e.toString());
			
		}catch (Exception e) {
			
			model.addAttribute("message", e.toString());
			e.printStackTrace();
		}  
		
		return "ruleMgmnt_viewModifyRule";
	}
	
	/*
	@ResponseBody
	@RequestMapping("/getRules")
	public List<Object[]> getRules(@RequestParam("projectId") String projectName, Model model){
		List<Object[]> ruleList = null;
		try 
		{
			int projectId = Integer.valueOf(URLDecoder.decode(projectName, "UTF-8"));
			ruleList = ruleMasterService.getRuleById(projectId);
			
		} catch (Exception e) 
		{
			model.addAttribute("message", e.toString());
			e.printStackTrace();
		}
		return ruleList;
		
	}
	*/
	@ResponseBody
	@RequestMapping("/getRules")
	public String getRules(@RequestParam("projectId") String projectName, Model model){
		String response = "";
		try 
		{
			int projectId = Integer.valueOf(URLDecoder.decode(projectName, "UTF-8"));
			response = ruleMasterService.getJSONRuleById(projectId);
			
		} catch (Exception e) 
		{
			model.addAttribute("message", e.toString());
			e.printStackTrace();
		}
		System.out.println("###### Json Response "+response);
		return response;
		
	}
	
	@ResponseBody
	@RequestMapping("/getTableNameFromRepositoryId")
	public String getTableNameFromRepositoryId(@RequestParam("repositoryId") String repoId, Model model){
		String response = "";
		int repositoryId = 0;
		try 
		{
			repositoryId = Integer.valueOf(URLDecoder.decode(repoId, "UTF-8"));
			response = repositoryDetailsService.getTableNameFromRepositoryId(repositoryId);
			
		} catch (Exception e) 
		{
			model.addAttribute("message", e.toString());
			e.printStackTrace();
		}
		System.out.println("#### Table Name : "+response);
		return response;
		
	}
	
	@ResponseBody
	@RequestMapping("/updateRuleDesc")
	public String updateRuleDesc(@RequestParam ("ruleId") String ruleNo, @RequestParam ("shortDesc") String shortDesc,HttpSession session)
	{
		System.out.println("#### ruleId "+ruleNo);
		System.out.println("#### shortDesc "+shortDesc);
		
		String response = "";
		String userId = "";
		
		userId = checkNull((String) session.getAttribute("userId"));
		try 
		{
			int ruleId = 0;
			String query = "";
			if(userId.length() > 0) {
				
				ruleId = Integer.valueOf(URLDecoder.decode(ruleNo, "UTF-8"));			
				query = URLDecoder.decode(shortDesc, "UTF-8");
				response = ruleMasterService.updateRuleDesc(ruleId, query, userId);
			}
			
			
		} catch (UnsupportedEncodingException e) {
			
			return e.toString();
			
		}catch(Exception e) {
			
			return e.toString();
		}
		return response;
	}
	@ResponseBody
	@RequestMapping("/updateRuleStatus")
	public String updateRuleStatus(@RequestParam ("ruleId") String ruleNo, @RequestParam ("status") String status,HttpSession session)
	{
		System.out.println("#### ruleId "+ruleNo);
		System.out.println("#### status "+status);
		
		String response = "";
		String userId = "";
		
		userId = checkNull((String) session.getAttribute("userId"));
		try 
		{
			int ruleId = 0;
			String query = "";
			if(userId.length() > 0) {
				
				ruleId = Integer.valueOf(URLDecoder.decode(ruleNo, "UTF-8"));			
				query = URLDecoder.decode(status, "UTF-8");
				response = ruleMasterService.updateRuleStatus(ruleId, status, userId);
			}
			
			
		} catch (UnsupportedEncodingException e) {
			
			return e.toString();
			
		}catch(Exception e) {
			
			return e.toString();
		}
		return response;
	}
	//Rule Management Added View Rule on 10-08-2021 End
	
	private String checkNull(String input)
    {
	    if(input == null || "null".equalsIgnoreCase(input) || "undefined".equalsIgnoreCase(input)) {
	    	
	    	input = "";
	    }
        
        return input.trim();    
    }
	
}
