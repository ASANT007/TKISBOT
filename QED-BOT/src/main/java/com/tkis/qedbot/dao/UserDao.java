package com.tkis.qedbot.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

public interface UserDao {

	public boolean authenticateUser(String userid, String password);
	
	public List<Object> getAllUserProjects();
	
	public List<Object> getAllProjectTracking();
	
	public List<Object>getProjectDetails();
}
