package com.tkis.qedbot.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tkis.qedbot.service.FileReaderService;

@Controller
public class FileUploadController {
	
	@Autowired
	FileReaderService service;

	@RequestMapping("/uploadExcel")
	public String uploadExcel(Model model) {
		
		List<String> projectList = service.getProjectList();		
		model.addAttribute("projectList", projectList);
		return "createTable";
	}
	
	
	@ResponseBody
	@PostMapping("/uploadFile") public String uploadFile(@RequestParam("file")
	  MultipartFile file,@RequestParam("projectName") String projectName,
	  @RequestParam("tabletype") String tabletype, RedirectAttributes redirectAttributes, HttpSession session)
	{
		
		String responseStr = "";
		
		String userId = "";
		
		userId = checkNull((String) session.getAttribute("userId")); // Not Set Yet
		
		// Remove Code START
		
		if(userId.length() >0) {
			
			userId = "DummyAdminId";
		}
		
		userId = "DummyAdminId";
		
		// Remove Code END
		System.out.println("#### userId "+userId);
		
		responseStr = service.upload(file, projectName, tabletype, userId, false);
		
		if(checkNull(responseStr).length() == 0) {
			
			responseStr = "<div class='py-3 text-center' style='color:red'> Please Check Excel File </div>";
		}
		
		System.out.println("#### Upload Response ["+responseStr+"]");	
		
		return responseStr;
	}
	
	@ResponseBody
	@PostMapping("/createtable") 
	public String createtable(@RequestParam("file")
	  MultipartFile file,@RequestParam("projectName") String projectName,
	  @RequestParam("tabletype") String tabletype, RedirectAttributes redirectAttributes, HttpSession session) 
	{
		String responseStr = "<div class='py-3 text-center' style='color:red'> Please Check Excel File </div>";
		
		String userId = "";
		
		userId = checkNull((String) session.getAttribute("userId")); // Not Set Yet
		
		if(userId.length() >0) {
			userId = "DummyAdminId";
		}
		
		responseStr = service.upload(file, projectName, tabletype, userId, true);
				
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
