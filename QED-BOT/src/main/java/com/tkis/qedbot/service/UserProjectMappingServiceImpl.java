package com.tkis.qedbot.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkis.qedbot.entity.UserProjectMapping;
import com.tkis.qedbot.repo.ProjectMasterRepo;
import com.tkis.qedbot.repo.UserProjectMappingRepo;

@Service
public class UserProjectMappingServiceImpl implements UserProjectMappingService {
	
	private static final Logger log = LoggerFactory.getLogger(InconsistencyLogsServiceImpl.class);

	@Autowired 
	UserProjectMappingRepo userProjectMappingRepo;
	
	@Autowired
	ProjectMasterRepo projectMasterRepo;
	
	@Override
	public List<Object[]> getProjectIdList(int deliverableTypeId) throws Exception {
		
		return projectMasterRepo.getProjectIdAndName(deliverableTypeId);
	}
	
	@Override
	public String getMappingProjectsWithUser(String mappedUser, int deliverableTypeId) {
		String response = "";		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonValueObject = null;
		
		List<Object[]> projectMaster = null;
		try {
			projectMaster = getProjectIdList(deliverableTypeId);
			
		} catch (Exception e) {			
			System.out.println("#### Exception :: getMappingProjectsWithUser : projectMaster "+e);
		}
		
		try 
		{
			for(Object [] pm : projectMaster) {	
				
				jsonValueObject = new JSONObject();
				jsonValueObject.put("PROJECT_NAME", (String)pm[1]);
				jsonValueObject.put("PROJECT_ID", (Integer)pm[0]);
				jsonValueObject.put("USER_ID",userProjectMappingRepo.getActiveUserProjectMapping(mappedUser,(Integer)pm[0]));				
				jsonArray.add(jsonValueObject);
			}
			
			response = jsonArray.toString();
			
		} catch (Exception e) {			
			System.out.println("#### Exception :: getMappingProjectsWithUser : findByUserIdAndProjectId "+e);
		}	
		return response;
	}

	@Override
	public String saveUserProjectMapping(String mappedUser, String addProject, String userId) {
		
		
		String[] addProjectArray = addProject.split(",");
		
		int projectId = 0;
		UserProjectMapping userProjectMapping = null;
		
		for(String project : addProjectArray) {
			
			if(checkNull(project).length() > 0) {
				projectId = Integer.valueOf(project);
				System.out.println("Mapped User "+mappedUser);
				System.out.println("projectId "+projectId);
				Optional<UserProjectMapping> optional = userProjectMappingRepo.findByUserIdAndProjectId(mappedUser, projectId);
				System.out.println("86 UserProjectMapping isPresent"+optional.isPresent());
				if(optional.isPresent()) {
					userProjectMapping = optional.get();
					userProjectMapping.setStatus("Active");
					userProjectMapping.setLastUpdatedBy(userId);
					userProjectMapping.setLastUpdationDate(new Timestamp( new Date().getTime()));
					userProjectMappingRepo.save(userProjectMapping);
				}else {
					userProjectMapping = new UserProjectMapping();
					userProjectMapping.setUserId(mappedUser);
					userProjectMapping.setProjectId(projectId);
					userProjectMapping.setStatus("Active");
					userProjectMapping.setCreatedBy(userId);
					userProjectMapping.setCreationDate(new Timestamp( new Date().getTime()));
					userProjectMappingRepo.save(userProjectMapping);
				}
			}			
			
		}		
		
		return "Mapping Save Successfully";
	}
	
	@Override
	public String removeAllProjectMappingWithUser(String mappedUser, String removeProject, String userId) 
	{		
		String response = "No Mapping found to remove";
		int projectId = 0;
		String[] removeProjectArray = removeProject.split(",");
		UserProjectMapping userProjectMapping = null;		
		
		for(String project : removeProjectArray) {
			
			if(checkNull(project).length() > 0) {
				projectId = Integer.valueOf(project);
				Optional<UserProjectMapping> optional = userProjectMappingRepo.findByUserIdAndProjectId(mappedUser, projectId);
				System.out.println(" 145 UserProjectMapping isPresent"+optional.isPresent());				
				if(optional.isPresent()) {
					userProjectMapping = optional.get();
					if(userProjectMapping.getStatus().equals("Active")) {
						
						userProjectMapping.setStatus("Inactive");
						userProjectMapping.setLastUpdatedBy(userId);
						userProjectMapping.setLastUpdationDate(new Timestamp( new Date().getTime()));
						userProjectMappingRepo.save(userProjectMapping);					
						response = "Mapping Removed Successfully";
						
					}
					
					
				}
			}		
			
		}
		
		System.out.println("#### response "+response);
		return response;		
	}
	
	public String checkNull(String input)
    {
		System.out.println("#### Input String ["+input+"]");
        if(input == null || "null".equalsIgnoreCase(input) || "undefined".equalsIgnoreCase(input))
        input = "";
        return input.trim();    
    }

}
