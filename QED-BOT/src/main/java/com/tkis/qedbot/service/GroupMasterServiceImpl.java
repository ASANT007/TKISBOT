package com.tkis.qedbot.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkis.qedbot.entity.GroupMaster;
import com.tkis.qedbot.repo.GroupMasterRepo;

@Service
public class GroupMasterServiceImpl implements GroupMasterService {
	

	private static final Logger log = LoggerFactory.getLogger(GroupMasterServiceImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	
	@Autowired 
	private GroupMasterRepo groupMasterRepo;

	@Override
	public String checkDuplicateGroupMaster(int groupId, String groupName, String groupRole) {
		boolean isGroupName=groupMasterRepo.checkDuplicateGroupName(groupName, groupId);
		if(isGroupName) {
			return "true|| group name";
		}
		boolean isProjectNameExist=groupMasterRepo.checkDuplicateGroupRole(groupRole, groupId);
		if(isProjectNameExist) {
			return "true|| role";
		}
		return "false";

	}

	@Override
	public boolean addGroupMasterDetails(GroupMaster groupMaster) {
		boolean isAdded = false;
		try {
			GroupMaster groupDetails = groupMasterRepo.save(groupMaster);
			if (groupDetails != null)
				isAdded = true;

		} catch (Exception e) {
			e.printStackTrace();
			isAdded = false;
		}
		return isAdded;
	}

	@Override
	public List<GroupMaster> getAllProjectGroupDetails() {
		return groupMasterRepo.findAll();
	}

	@Override
	public GroupMaster groupMasterInfoById(int groupMasterId) {
		Optional<GroupMaster> groupMasterInfo = groupMasterRepo
				.findById(groupMasterId);
		if (groupMasterInfo.isPresent()) {
			return groupMasterInfo.get();
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackOn  = Exception.class)
	public boolean editGroup(int groupId, String groupName, String groupRole, String status,
			String lastUpdatedBy, Date lastUpdationDate) {
		Session session = null;
		int  modifications=0;
		boolean succ=false;
		
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
	
		try {
			Query query = session.createQuery("update GroupMaster set groupName= :groupName,role= :groupRole,last_updated_by= :lastUpdatedBy,last_updation_date= :lastUpdationDate,status= :status where groupId=:groupId");
			
			query.setParameter("groupName",groupName);
			query.setParameter("groupRole",groupRole);
			query.setParameter("lastUpdatedBy",lastUpdatedBy);
			query.setParameter("lastUpdationDate",lastUpdationDate);
			query.setParameter("status",status);
			query.setParameter("groupId",groupId);
			
			modifications = query.executeUpdate();

			 succ = (modifications > 0);
		} catch (Exception e) {
			succ = false;
			log.error("editGroup()", e);
			e.printStackTrace();
		}
	
		return succ;
	
	}

	@Override
	public Optional<GroupMaster> findByRoleAndStatus(String role, String status) {
		
		return groupMasterRepo.findByRoleAndStatus(role,status);
	}

	
}
