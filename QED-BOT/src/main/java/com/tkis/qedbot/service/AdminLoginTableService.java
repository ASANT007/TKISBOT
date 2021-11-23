package com.tkis.qedbot.service;

import java.util.Date;
import java.util.List;

import com.tkis.qedbot.entity.AdminLoginTable;

public interface AdminLoginTableService {

	
	boolean editAdminLoginDetails(int adminSrNo, String userId, String password, Date lastUpdationDate);

	boolean authenticateUser(String username, String password);

	AdminLoginTable getUserDetails(String userId);

}
