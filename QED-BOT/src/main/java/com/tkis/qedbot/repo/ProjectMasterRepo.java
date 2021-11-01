package com.tkis.qedbot.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tkis.qedbot.entity.ProjectMaster;

@Repository
public interface ProjectMasterRepo extends JpaRepository<ProjectMaster, Integer>
{

	@Query("select projectId, projectName from ProjectMaster where deliverableTypeId = :deliverableTypeId and status = 'Active' ")
	public List<Object[]> getProjectIdAndName(@Param("deliverableTypeId") int deliverableTypeId);

	//Integration of DeliverableTypeMaster and ProjectMaster Added on 27-10-2021 START
	@Query("select case when count(p)> 0 then true else false end from ProjectMaster p where upper(p.projectTag) = upper(:projectTag)  and p.projectId<>(:projectId)")
	boolean checkDuplicateTag(@Param("projectTag") String projectTag,@Param("projectId") int projectId);

	@Query("select case when count(p)> 0 then true else false end from ProjectMaster p where upper(p.projectName) = upper(:projectName)  and p.projectId<>(:projectId)")
	boolean checkDuplicateProjectName(@Param("projectName") String projectName,@Param("projectId") int projectId);
	//Integration of DeliverableTypeMaster and ProjectMaster Added on 27-10-2021 END
}
