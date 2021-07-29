package com.tkis.qedbot.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tkis.qedbot.ad.ADAuthentication;

@Controller
public class LoginController {
	
	@Autowired
	private ADAuthentication adAuthentication;

	@RequestMapping({"/","login"})	
	public String login() {
		
		return "login";
		
	}
	
	@RequestMapping("/logout")	
	public String logout() {
		
		return "logout";
		
	}
	
	@ResponseBody
	@PostMapping("/validateUser")
	public String validateUser(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("domain") String domain, HttpSession session) {
		
		System.out.println("### username "+username);
		System.out.println("### password "+password);
		System.out.println("### domain "+domain);
		
		String response = "", providerUrl = "";
		boolean isConnected = false;
		
		if(checkNull(domain).equalsIgnoreCase("STMUDC02")) {
			
			providerUrl = "ldap://stmudc02.in.uhde.org";
			
		}else {
			
			providerUrl = "ldap://stmudc15.ext.in.uhde.org";
		}
		
		isConnected = adAuthentication.makeConnection(providerUrl, username, password);
		
		if(isConnected) {
			
			response = adAuthentication.validateUser(username, password, session);
					   adAuthentication.closeConnection();
					   
					   response = "functionaladmin";
		}
		
		return response ;
	}
	
	
	@RequestMapping("/userDashboard")
	public String userDashboard() {
		
		return "userDashboard";
	}
	@RequestMapping("/functionalAdminHome")
	public String functionalAdminHome() {
		
		return "functionalAdminHome";
	}
	
	@RequestMapping("/superAdminHome")
	public String superAdminHome() {
		
		return "superAdminHome";
	}
	
	@RequestMapping("/managementDashboard")
	public String managementDashboard() {
		
		return "managementDashboard";
	}
	
	/*
	 * @RequestMapping("/dashboard") public String
	 * dashboard(@RequestParam("username") String
	 * username, @RequestParam("password") String password,
	 * 
	 * @RequestParam("domain") String domain,RedirectAttributes redirectAttributes )
	 * {
	 * 
	 * 
	 * //ModelAndView modelAndView = new ModelAndView();
	 * //modelAndView.setViewName("login");
	 * 
	 * 
	 * System.out.println("### username "+username);
	 * System.out.println("### password "+password);
	 * System.out.println("### domain "+domain);
	 * 
	 * 
	 * if(checkNull(username).length() == 0){
	 * 
	 * redirectAttributes.addFlashAttribute("error", "Please Enter user name !!!");
	 * return "redirect:/";
	 * 
	 * }else if(checkNull(password).length() == 0) {
	 * 
	 * //modelAndView.addObject("error", "Please Enter password !!!");
	 * 
	 * } else if(checkNull(domain).length() == 0) {
	 * 
	 * //modelAndView.addObject("error", "Please Select domain name !!!");
	 * 
	 * } else {
	 * 
	 * //modelAndView.setViewName("dashboard"); }
	 * 
	 * 
	 * return "dashboard";
	 * 
	 * }
	 */
	
	public String checkNull(String input)
    {
		System.out.println("#### Input String ["+input+"]");
        if(input == null || "null".equalsIgnoreCase(input) || "undefined".equalsIgnoreCase(input))
        input = "";
        return input.trim();    
    }
	
}
