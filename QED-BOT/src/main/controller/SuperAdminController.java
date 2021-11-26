package com.tkis.qedbot.controller;

import java.util.List;
import java.net.URLDecoder;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tkis.qedbot.FileEncryption;
import com.tkis.qedbot.ServerSideValidation;
import com.tkis.qedbot.entity.GroupMaster;
import com.tkis.qedbot.entity.ADServiceMaster;
import com.tkis.qedbot.entity.AdminLoginTable;
import com.tkis.qedbot.service.ADServiceMasterService;
import com.tkis.qedbot.service.AdminLoginTableService;
import com.tkis.qedbot.service.GroupMasterService;
//http://localhost:8083/admin/
@Controller
@RequestMapping("admin")
public class SuperAdminController {
	
	
	//Super admin LoginController Integration 01-11-2021 START 
	@Autowired
	private FileEncryption encdec;

	@RequestMapping({"/","/login","/index"})
	public String index() {
		return "superAdminLogin";
	}
	
	
	@RequestMapping("/superAdminLogout")
	public String logout() {
		return "superAdminlogout";
	}
	
	@ResponseBody
	@RequestMapping("checkAdminSession")	
	public String checkAdminSession(HttpSession session) {
		String response = "";
		
		String userId = checkNull((String) session.getAttribute("username"));
		if(userId.length() > 0) {			
			response = "valid";
		}else {		
			response = "Invalid";
		}
		
		return response;
		
	}
	
	@Autowired
	private AdminLoginTableService adminLoginTableService;
	
	@RequestMapping(value = "/checkAdminLogin", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkAdminLogin(@RequestParam String username, @RequestParam String password, HttpSession session) {
		password=encdec.Encrypt(password);
		boolean isAuthenticated = false;
		
		isAuthenticated = adminLoginTableService.authenticateUser(username, password);

		if (isAuthenticated) {
			session.setAttribute("username", username);
			session.setAttribute("role", "Administrator");
			return isAuthenticated;

		} else {
			return false;
		}

	}
	//Super admin LoginController Integration 01-11-2021 END
	
	//Super admin AdminController Integration 01-11-2021 START 
	@Autowired
	private GroupMasterService groupMasterService;

	@Autowired
	private ADServiceMasterService adServiceMasterService;

	@Autowired
	private ServerSideValidation ssv;


	@RequestMapping("createRule")
	public String viewProjectDetails() {
		return "createRule";
	}
	
	@RequestMapping("viewGroupMaster")
	public String viewGroupMaster(Model model) {
		List<GroupMaster> groupMasterList = groupMasterService.getAllProjectGroupDetails();
		model.addAttribute("groupMasterList", groupMasterList);
		return "viewGroupMaster";
	}

	@RequestMapping("addGroupMaster")
	public String addGroupMaster(Model model) {
		return "addGroupMaster";
	}

	@RequestMapping(value = "/checkDuplicateGroupMaster", method = RequestMethod.POST)
	@ResponseBody
	public String checkDuplicateGroupMaster(@RequestParam int groupId, @RequestParam String groupName,
			@RequestParam String groupRole, HttpSession session) {
		try {
			String isExist = null;
			if (!ssv.isStringEmpty(Integer.toString(groupId)) && !ssv.isStringEmpty(groupName)
					&& !ssv.isStringEmpty(groupRole)) {
				isExist = groupMasterService.checkDuplicateGroupMaster(groupId, groupName, groupRole);
				return isExist;
			} else {
				return "invaliddata";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Exception :" + e;
		}

	}

	@RequestMapping(value = "/addGroupMasterDetails", method = RequestMethod.POST)
	@ResponseBody
	public String addGroupMasterDetails(@RequestParam String groupName, @RequestParam String groupRole,
			HttpSession session) {
		boolean succ = false;

		if (!ssv.isStringEmpty(groupName) && !ssv.isStringEmpty(groupRole)) {

			String createdBy = null;
			createdBy = (String) session.getAttribute("username");
			
			GroupMaster groupMaster = new GroupMaster();

			groupMaster.setStatus("Active");
			groupMaster.setGroupName(groupName);
			groupMaster.setRole(groupRole);
			groupMaster.setCreatedBy(createdBy);
			groupMaster.setCreationDate(new java.sql.Timestamp(new java.util.Date().getTime()));

			succ = groupMasterService.addGroupMasterDetails(groupMaster);

			if (succ)
				return "true";
			else
				return "false";

		} else {
			return "invaliddata";
		}

	}

	@RequestMapping("/editGroupMaster")
	public String editGroupMaster(@RequestParam("groupMasterId") String encGroupMasterId, Model model) {
		int groupMasterId = Integer.parseInt(encdec.Decrypt(encGroupMasterId));
		GroupMaster groupMasterInfo = groupMasterService.groupMasterInfoById(groupMasterId);
		model.addAttribute("groupMasterInfo", groupMasterInfo);

		return "editGroupMaster";
	}

	@RequestMapping(value = "/editGroup", method = RequestMethod.POST)
	@ResponseBody
	public String editGroup(@RequestParam int groupId, @RequestParam String groupName, @RequestParam String groupRole,
			@RequestParam String status, HttpSession session) {
		boolean succ = false;

		String lastUpdatedBy = null;
		lastUpdatedBy = (String) session.getAttribute("username");

		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date lastUpdationDate = dt;

		if (!ssv.isStringEmpty(groupName) && !ssv.isStringEmpty(groupRole)) {
			succ = groupMasterService.editGroup(groupId, groupName, groupRole, status, lastUpdatedBy, lastUpdationDate);

			if (succ)
				return "true";
			else
				return "false";

		} else {
			return "invaliddata";
		}

	}

	@RequestMapping("ADServiceMaster")
	public String ADServiceMaster(Model model) {
		List<ADServiceMaster> adServiceList = adServiceMasterService.getAllADServices();
		model.addAttribute("adServiceList", adServiceList);
		return "ADServiceMaster";
	}

	@RequestMapping("/editADServiceMaster")
	public String editADServiceMaster(@RequestParam("adServiceId") String encADServiceId, Model model) {
		int adServiceId = Integer.parseInt(encdec.Decrypt(encADServiceId));

		ADServiceMaster adServiceMasterInfo = adServiceMasterService.adServiceInfoById(adServiceId);
		model.addAttribute("adServiceMasterInfo", adServiceMasterInfo);

		return "editADServiceMaster";
	}

	@RequestMapping(value = "/editADServiceDetails", method = RequestMethod.POST)
	@ResponseBody
	public String editADServiceDetails(@RequestParam int adServiceId, @RequestParam String userId,
			@RequestParam String password, @RequestParam String ldapUrl, @RequestParam String status,
			HttpSession session) {
		boolean succ = false;

		password = encdec.Encrypt(password);
		String lastUpdatedBy = null;
		lastUpdatedBy = (String) session.getAttribute("username");

		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date lastUpdationDate = dt;// new java.sql.Timestamp(new java.util.Date().getTime());//
												// sdf.format(dt);

		if (!ssv.isStringEmpty(userId) && !ssv.isStringEmpty(password) && !ssv.isStringEmpty(ldapUrl)) {
			succ = adServiceMasterService.editADServiceDetails(adServiceId, userId, password, ldapUrl, status,
					lastUpdatedBy, lastUpdationDate);

			if (succ)
				return "true";
			else
				return "false";

		} else {
			return "invaliddata";
		}

	}

	@RequestMapping("changePassword")
	public String AdminLogin(Model model, HttpSession session) {

		String userId = (String) session.getAttribute("username");
		AdminLoginTable loginDetails = adminLoginTableService.getUserDetails(userId);
		model.addAttribute("loginDetails", loginDetails);
		return "changeAdminPassword";
	}

	@RequestMapping(value = "/editAdminLoginDetails", method = RequestMethod.POST)
	@ResponseBody
	public String editAdminLoginDetails(@RequestParam int adminSrNo, @RequestParam String password,
			HttpSession session) {
		boolean succ = false;

		password = encdec.Encrypt(password);
		String lastUpdatedBy = null;
		lastUpdatedBy = (String) session.getAttribute("username");

		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date lastUpdationDate = dt;

		if (!ssv.isStringEmpty(password)) {
			succ = adminLoginTableService.editAdminLoginDetails(adminSrNo, password, lastUpdatedBy, lastUpdationDate);

			if (succ)
				return "true";
			else
				return "false";

		} else {
			return "invaliddata";
		}

	}
	
	//Super admin AdminController Integration 01-11-2021 END 
	public String checkNull(String input)
    {
		System.out.println("#### Input String ["+input+"]");
        if(input == null || "null".equalsIgnoreCase(input) || "undefined".equalsIgnoreCase(input))
        input = "";
        return input.trim();    
    }

}
