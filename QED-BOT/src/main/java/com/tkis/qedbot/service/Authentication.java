package com.tkis.qedbot.service;

import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;

import com.tkis.qedbot.entity.ADServiceMaster;

public interface Authentication {

	public List<ADServiceMaster> getADServiceDetails() throws Exception;
	
	public String validateUser(String userId, String password, String domain, HttpSession session) throws Exception;

	public String getADUserGroupUsers(String term);
	
	//public HashMap<String,String> getADUsers() throws Exception;
}
