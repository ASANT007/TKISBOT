package com.tkis.qedbot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MgmntController {

	
	@RequestMapping("/test")
	public String createTable(Model model) 
	{		
		return "test";
	}
}

