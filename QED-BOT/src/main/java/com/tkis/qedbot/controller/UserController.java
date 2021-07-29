package com.tkis.qedbot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tkis.qedbot.dao.UserDaoImpl;

@Controller
public class UserController {

	@Autowired
	UserDaoImpl userDao;
	
	@RequestMapping("/user-home")
	public ModelAndView dashboard() {
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName("dashboard");
		List<Object> entities = userDao.getAllUserProjects();
		modelAndView.addObject("dashboardData", entities);
		return modelAndView;
	}
	
	@RequestMapping("/user-proTracking")
	public ModelAndView userProTracking() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("projectTracking");
		return modelAndView;
	}
	
	@RequestMapping("/user-viewProjects")
	public ModelAndView viewProjects() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("viewProjectDetails");
		return modelAndView;
	}
}
