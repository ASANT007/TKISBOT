package com.tkis.qedbot.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.tkis.qedbot.entity.GroupMaster;

public interface GroupMasterService {

	public String checkDuplicateGroupMaster(int groupId, String groupName, String groupRole);

	public boolean addGroupMasterDetails(GroupMaster groupMaster);

	public List<GroupMaster> getAllProjectGroupDetails();

	public GroupMaster groupMasterInfoById(int groupMasterId);

	public boolean editGroup(int groupId, String groupName, String groupRole, String status,
			String lastUpdatedBy, Date lastUpdationDate);

	public Optional<GroupMaster> findByRoleAndStatus(String role, String status);
}
