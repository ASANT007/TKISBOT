package com.tkis.qedbot.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkis.qedbot.entity.DeliverabletypeMaster;
import com.tkis.qedbot.entity.ProjectMaster;
import com.tkis.qedbot.repo.ProjectMasterRepo;

@Service
public class ProjectMasterServiceImpl implements ProjectMasterService 
{
	private static final Logger log = LoggerFactory.getLogger(ProjectMasterServiceImpl.class);
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

	@Override
	public String findById(int projectId) {
		String projectName = "";
		try {
			projectName = projectMasterRepo.findById(projectId).get().getProjectName();
		}catch(Exception e) {
			System.out.println("#### Exception ProjectMasterService :: findById : "+e);
		}
		System.out.println("#### findById: projectName "+projectName);
		return projectName;
	}

	
	//Integration of DeliverableTypeMaster and ProjectMaster Added on 27-10-2021 START
	
	@PersistenceContext
	private EntityManager entityManager;	

	@Override
	public List<Object[]> getAllProjectMasterDetails() {
		
		List<Object[]> projectList = null;
		try {
			String sql = "select pm.*,dm.deliverabletype_shortname from project_master pm inner join deliverabletype_master dm on pm.deliverabletype_id=dm.deliverabletype_id";

				Session session = null;
				if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
					throw new NullPointerException();
				}
				 projectList= session.createNativeQuery(sql).getResultList();
				
				
			} catch (Exception e) {

			log.error("exception in getAllProjectMasterDetails():", e);
			return null;
		}
		return projectList;
	}

	@Override
	public List<DeliverabletypeMaster> getAllDeliverableTypeMasterActive() {
		List<DeliverabletypeMaster> deliverableList = null;
		try {
			String sql = "select * from deliverabletype_master m where m.STATUS='Active' order by m.deliverabletype_shortname";

				Session session = null;
				if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
					throw new NullPointerException();
				}
				deliverableList= session.createNativeQuery(sql,DeliverabletypeMaster.class).getResultList();
				
			} catch (Exception e) {

			log.error("exception in getAllDeliverableTypeMasterActive():", e);
			return null;
		}
		return deliverableList;
	}
	
	@Override
	public String checkDuplicateProjectMaster(int projectMasterId,int deliverableTypeId, String projectTag, String projectName,
			String projectDescription) throws Exception{
		
			boolean isTagExist=projectMasterRepo.checkDuplicateTag(projectTag, projectMasterId);
			if(isTagExist) {
				return "true|| project tag";
			}
			boolean isProjectNameExist=projectMasterRepo.checkDuplicateProjectName(projectName, projectMasterId);
			if(isProjectNameExist) {
				return "true|| project name";
			}
		
			return "false";

	}
	
	@Override
	@Transactional(rollbackOn  = Exception.class)
	public boolean editProject(int projectMasterId,   String projectName,
			String projectDescription, String status, String lastUpdatedBy, String lastUpdationDate, String filesPath) {
		Session session = null;
		int  modifications=0;
		boolean succ=false;
		
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
	
		try {
			Query query = session.createNativeQuery("update project_master set project_name= :projectName,short_description= :projectDescription,last_updated_by= :lastUpdatedBy,last_updation_date= :lastUpdationDate,status= :status, files_Path= :filesPath where project_id=:projectMasterId");
			
			query.setParameter("projectName",projectName);
			query.setParameter("projectDescription",projectDescription);
			query.setParameter("lastUpdatedBy",lastUpdatedBy);
			query.setParameter("lastUpdationDate",lastUpdationDate);
			query.setParameter("status",status);
			query.setParameter("filesPath",filesPath);
			query.setParameter("projectMasterId",projectMasterId);
			
			modifications = query.executeUpdate();

			 succ = (modifications > 0);
		} catch (Exception e) {
			succ = false;
			log.error("editProject()", e);
			e.printStackTrace();
		}
	
		return succ;
	}

	
	@Override
	public boolean addProjectMasterDetails(ProjectMaster projectMaster) {
		
		boolean isAdded = false;
		try {
			ProjectMaster projectDetails = projectMasterRepo.save(projectMaster);
			if (projectDetails != null)
				isAdded = true;

		} catch (Exception e) {
			e.printStackTrace();
			isAdded = false;
		}
		return isAdded;
		
	}
	
	@Override
	public Object[] projectMasterInfoById(int projectMasterId) {
		Object[] projectList = null;
		try {
			String sql = "select pm.*,dm.deliverabletype_shortname from project_master pm inner join deliverabletype_master dm on pm.deliverabletype_id=dm.deliverabletype_id where pm.project_id="+projectMasterId;

				Session session = null;
				if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
					throw new NullPointerException();
				}
				 projectList= (Object[]) session.createNativeQuery(sql).getSingleResult();
				
			} catch (Exception e) {

			log.error("exception in projectMasterInfoById():", e);
			return null;
		}
		return projectList;
	}
	//Integration of DeliverableTypeMaster and ProjectMaster Added on 27-10-2021 END
	

}
