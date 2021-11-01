package com.tkis.qedbot.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tkis.qedbot.entity.ConsistencyTracking;
import com.tkis.qedbot.repo.ProjectMasterRepo;
import com.tkis.qedbot.service.ConsistencyTrackingService;
import com.tkis.qedbot.service.MasterDeliverableMappingService;
import com.tkis.qedbot.service.ProjectMasterService;
import com.tkis.qedbot.service.RepositoryDetailsService;

@Controller
public class UserController {

	@Autowired
	RepositoryDetailsService repositoryDetailsService;
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	ProjectMasterRepo projectMasterRepo;
	
	@Autowired
	MasterDeliverableMappingService masterDeliverableMappingService;
	
	@Autowired
	ConsistencyTrackingService consistencyTrackingService;
	
	//Added on 15-09-2021 START	
	@RequestMapping("/userDashboard")
	public ModelAndView userDashboard(ModelAndView modelAndView,HttpSession session) {
		//ModelAndView modelAndView = new ModelAndView();
		System.out.println("#### userDashboard");
		String userId = checkNull((String)session.getAttribute("userId"));
		String role = checkNull((String)session.getAttribute("role"));//Added on 29-10-2021
		if(userId.length() > 0) {
			modelAndView.setViewName("userDashboard");
			try {
				modelAndView.addObject("projectConsistency",consistencyTrackingService.getConsistencyCountForAllProjects(userId,role));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}else {
			modelAndView.setViewName("logout");
		}
		
		return modelAndView;
	}
	
	
	@ResponseBody
	@RequestMapping("/setProjectIdName")	
	public String setProjectIdName(@RequestParam("projectId") int projectId, @RequestParam("projectName") String projectName, HttpSession session) {	
		String response = "";
		String userId = checkNull((String)session.getAttribute("userId"));
		
		if(userId.length() > 0) {
			
			session.setAttribute("projectId", projectId);
			try {
				session.setAttribute("projectName", URLDecoder.decode(projectName, "UTF-8"));
			} catch (UnsupportedEncodingException e) {				
				e.printStackTrace();
			}
			response = "success";
		}
		System.out.println("##### response "+response);
		return response;
	}
	
	//@RequestMapping("viewProjectDetails/{projectId}/{projectName}")
	//public ModelAndView viewProjectDetails(ModelAndView modelAndView, @PathVariable("projectId") int projectId, @PathVariable("projectName") String projectName,HttpSession session) {
	@RequestMapping("/viewProjectDetails")
	public ModelAndView viewProjectDetails(ModelAndView modelAndView,HttpSession session) {
		//System.out.println("#### viewProjectDetails projectId "+projectId+" projectName "+projectName);
		
		String userId = checkNull((String)session.getAttribute("userId"));
		
		
		if(userId.length() > 0) {
			try {
				
				int projectId = (Integer) session.getAttribute("projectId");
				String projectName = checkNull((String) session.getAttribute("projectName"));
				System.out.println("#### viewProjectDetails projectId "+projectId+" projectName "+projectName);
				
				modelAndView.addObject("projectId",projectId);	
				modelAndView.addObject("keyField",repositoryDetailsService.getKeyFieldByProjectId(projectId));
				modelAndView.addObject("projectName",projectName);				
							
				//modelAndView.addObject("projectName",projectMasterRepo.findById(1).get().getProjectName());				
				//modelAndView.addObject("projectdata",consistencyTrackingService.getJSONMappingDataByProjectId(1, userId));
				
			} catch (Exception e) {
				modelAndView.addObject("message",e.getMessage());
				e.printStackTrace();
			}
			modelAndView.setViewName("viewProjectDetails");
		}else {
			modelAndView.setViewName("logout");
		}
		return modelAndView;
	}
	//Added on 15-09-2021 END
	
	//Added on 11-10-2021 START
	
	@PostMapping("/viewConsistencyByKeyField")	
	public ModelAndView viewConsistencyByKeyField(ModelAndView modelAndView,HttpSession session, 
			@RequestParam("filterKeyField") String filterKeyField) {
		
		int projectId = 0;
		String projectName ="";
		
		String userId = checkNull((String)session.getAttribute("userId"));
			
			if(userId.length() > 0) {
				projectId = (Integer) session.getAttribute("projectId");
				projectName = checkNull((String) session.getAttribute("projectName"));
				System.out.println("#### filterKeyField ["+filterKeyField+"] projectId ["+projectId+"] projectName["+projectName);
				try {
					modelAndView.addObject("projectId",projectId);
					modelAndView.addObject("keyField",repositoryDetailsService.getKeyFieldByProjectId(projectId));
					modelAndView.addObject("fiterKeyField",filterKeyField);
					//modelAndView.addObject("projectName",projectMasterService.findById(projectId));			
					//modelAndView.addObject("projectName",projectMasterRepo.findById(projectId).get().getProjectName());				
					modelAndView.addObject("projectName",projectName);				
					modelAndView.addObject("projectdata",consistencyTrackingService.getJSONMappingDataByProjectId(projectId, userId, filterKeyField));
					//modelAndView.addObject("projectdata",consistencyTrackingService.getJSONMappingDataByKeyField(filterKeyField,projectId, userId));
					
				} catch (Exception e) {
					//modelAndView.addObject("message",e.getMessage());
					System.out.println("#### Exception UserController :: viewConsistencyByKeyField: "+e);
				}
				modelAndView.setViewName("viewProjectDetails");
			}else {
				modelAndView.setViewName("logout");
			}
			return modelAndView;
		}

	/*
	 * @GetMapping(
	 * "viewConsistencyByKeyField/filterKeyField/{filterKeyField}/projectId/{projectId}")
	 * public ModelAndView viewConsistencyByKeyField(ModelAndView
	 * modelAndView,HttpSession session,
	 * 
	 * @PathVariable("filterKeyField") String
	 * filterKeyField, @PathVariable("projectIdVal") String projectIdVal) {
	 * System.out.println("#### filterKeyField"+filterKeyField);
	 * System.out.println("#### projectIdVal"+projectIdVal); String userId =
	 * checkNull((String)session.getAttribute("userId"));
	 * 
	 * if(userId.length() > 0) { try { int projectId =
	 * Integer.valueOf(URLDecoder.decode(projectIdVal, "UTF-8"));
	 * modelAndView.addObject("keyField",repositoryDetailsService.
	 * getKeyFieldByProjectId(projectId));
	 * modelAndView.addObject("selectedkeyField",URLDecoder.decode(filterKeyField,
	 * "UTF-8"));
	 * modelAndView.addObject("projectName",projectMasterRepo.findById(1).get().
	 * getProjectName());
	 * //modelAndView.addObject("projectdata",consistencyTrackingService.
	 * getJSONMappingDataByProjectId(projectId, userId));
	 * modelAndView.addObject("projectdata",consistencyTrackingService.
	 * getJSONMappingDataByProjectId(projectId, userId));
	 * 
	 * } catch (Exception e) { modelAndView.addObject("message",e.getMessage());
	 * e.printStackTrace(); } modelAndView.setViewName("viewProjectDetails"); }else
	 * { modelAndView.setViewName("logout"); } return modelAndView; }
	 */
	//Added on 11-10-2021 END
	
	
	//Added on 01-10-2021 START
	//public String saveConsistency(@RequestBody ConsistencyTracking consistencyJsonData) {
	
	//Saving single json value
	
	@ResponseBody
	@PostMapping("/saveConsistency")	
	public String saveConsistency(@RequestBody ConsistencyTracking consistencyTracking, HttpSession session) {	
		
		//System.out.println("#### saveConsistency....."+consistencyTracking);
		//System.out.println("#### saveConsistency....."+consistencyTracking.toString());
		String userId = checkNull((String)session.getAttribute("userId"));
		if(userId.length() > 0) {
			//consistencyTracking.setFlaggedBy(userId);
			//consistencyTracking.setFlaggedDate(new Timestamp(new Date().getTime()));
			int projectId = (Integer) session.getAttribute("projectId");
			
			System.out.println("#### saveConsistency....."+consistencyTracking);
			try {
				consistencyTrackingService.saveConsistency(consistencyTracking, projectId, userId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "success";
	}
	
	
	//Used for json array. Multiple values 
	/*
	@ResponseBody
	@PostMapping("/saveConsistency")	
	public String saveConsistencyList(@RequestBody List<ConsistencyTracking> consistencyTracking, HttpSession session) {	
		
		//System.out.println("#### saveConsistency....."+consistencyTracking);
		//System.out.println("#### saveConsistency....."+consistencyTracking.toString());
		consistencyTrackingService.saveConsistency(consistencyTracking);
		return "success";
	}
	*/
	//Added on 01-10-2021 END
	
	public String checkNull(String input)
    {
		System.out.println("#### Input String ["+input+"]");
        if(input == null || "null".equalsIgnoreCase(input) || "undefined".equalsIgnoreCase(input))
        input = "";
        return input.trim();    
    }
}
