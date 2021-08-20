package com.tkis.qedbot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tkis.qedbot.entity.ProjectMaster;

@Service
public interface ProjectMasterService {

	public List<Object[]> getProjectIdAndName(int deliverableTypeId) throws Exception;
	
	public List<ProjectMaster> getProjectMaster() throws Exception;
	
	
	
}
