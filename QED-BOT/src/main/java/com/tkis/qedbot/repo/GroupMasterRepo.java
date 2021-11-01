package com.tkis.qedbot.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tkis.qedbot.entity.GroupMaster;

public interface GroupMasterRepo extends JpaRepository<GroupMaster, Integer> {

	@Query("select case when count(g)> 0 then true else false end from GroupMaster g where upper(g.groupName) = upper(:groupName)  and g.groupId<>(:groupId)")
	boolean checkDuplicateGroupName(@Param("groupName") String groupName,@Param("groupId") int groupId);

	@Query("select case when count(g)> 0 then true else false end from GroupMaster g where  upper(g.role) = upper(:groupRole) and g.groupId<>(:groupId)")
	boolean checkDuplicateGroupRole(@Param("groupRole") String groupRole,@Param("groupId") int groupId);
	
	@Query("select groupName, role from GroupMaster where status ='Active' ")
	public List<Object[]> getGroupDetails() throws Exception;

	Optional<GroupMaster> findByRoleAndStatus(String role, String status);
	
}
