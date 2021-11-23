package com.tkis.qedbot.service;

import java.util.Date;
import java.util.List;

import com.tkis.qedbot.entity.ADServiceMaster;

public interface ADServiceMasterService {

	List<ADServiceMaster> getAllADServices();

	 ADServiceMaster adServiceInfoById(int adServiceId);

	boolean editADServiceDetails(int adServiceId, String userId, String password, String securityPrincipal,
			String ldapUrl, String status, Date lastUpdationDate);


}
