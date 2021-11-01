package com.tkis.qedbot.service;

import java.util.List;

import com.tkis.qedbot.entity.DeliverabletypeMaster;
import com.tkis.qedbot.entity.ProjectMaster;


public interface ProjectMasterService 
{

	public List<Object[]> getProjectIdAndName(int deliverableTypeId) throws Exception;
	
	public List<ProjectMaster> getProjectMaster() throws Exception;

	public String findById(int projectId);
	
	//Integration of DeliverableTypeMaster and ProjectMaster Added on 27-10-2021 START
	List<Object[]> getAllProjectMasterDetails();

	List<DeliverabletypeMaster> getAllDeliverableTypeMasterActive();

	String checkDuplicateProjectMaster(int projectMasterId, int deliverableTypeId, String projectTag,
			String projectName, String projectDescription) throws Exception;

	boolean addProjectMasterDetails(ProjectMaster projectMaster);

	Object[] projectMasterInfoById(int projectMasterId);

	boolean editProject(int projectMasterId, String projectTag, String projectName,
			String projectDescription, String status, String lastUpdatedBy, String string);
	
	//Integration of DeliverableTypeMaster and ProjectMaster Added on 27-10-2021 END
}
