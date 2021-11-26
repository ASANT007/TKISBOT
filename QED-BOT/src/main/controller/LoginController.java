package com.tkis.qedbot.controller;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tkis.qedbot.service.Authentication;

@Controller
public class LoginController {
	
	@Autowired
	private Authentication authentication;

	@RequestMapping({"/","login"})	
	public String login() {
		
		return "login";
		
	}
	
	@RequestMapping("/logout")	
	public String logout() {
		
		return "logout";
		
	}
	
	@ResponseBody
	@RequestMapping("/checkSession")	
	public String checkSession(HttpSession session) {
		String response = "";
		
		String userId = checkNull((String) session.getAttribute("userId"));
		if(userId.length() > 0) {			
			response = "valid";
		}else {		
			response = "Invalid";
		}
		
		return response;
		
	}
	
	@ResponseBody
	@PostMapping("/validateUser")
	public String validateUser(@RequestParam("username") String userId, @RequestParam("password") String password,
			@RequestParam("domain") String domain, HttpSession session) {
		
		String response = "";
		
		try 
		{
			response = authentication.validateUser(userId, password, domain, session);
			
		} catch (Exception e) {
			response = e.toString();
			//e.printStackTrace();
		}
		
		return response ;
	}
	
	/*
	@RequestMapping("/userDashboard")
	public String userDashboard() {
		
		return "userDashboard";
	}
	*/	
	
	@RequestMapping("/functionalAdminHome")
	public String functionalAdminHome() {
		
		return "functionalAdminHome";
	}
	
	@RequestMapping("/managementDashboard")
	public String managementDashboard() {
		
		return "managementDashboard";
	}
	
	public String checkNull(String input)
    {
		System.out.println("#### Input String ["+input+"]");
        if(input == null || "null".equalsIgnoreCase(input) || "undefined".equalsIgnoreCase(input))
        input = "";
        return input.trim();    
    }
	
}
