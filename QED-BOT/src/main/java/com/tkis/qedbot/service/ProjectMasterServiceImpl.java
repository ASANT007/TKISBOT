package com.tkis.qedbot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tkis.qedbot.entity.ProjectMaster;
import com.tkis.qedbot.repo.ProjectMasterRepo;

@Component
public class ProjectMasterServiceImpl implements ProjectMasterService 
{
	
	@Autowired
	ProjectMasterRepo projectMasterRepo;

	@Override
	public List<ProjectMaster> getProjectMaster() throws Exception{
		
		List<ProjectMaster> projectMaster = null;
		
		projectMaster = projectMasterRepo.findAll();
		
		
		
		return projectMaster;
	}

	@Override
	public List<Object[]> getProjectIdAndName(int deliverableTypeId) throws Exception{
		
		return projectMasterRepo.getProjectIdAndName(deliverableTypeId);
	}

	
	

	
	
	

}
