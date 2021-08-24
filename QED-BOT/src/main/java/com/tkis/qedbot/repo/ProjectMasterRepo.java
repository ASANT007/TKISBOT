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

	@Query("select projectId, projectName from ProjectMaster where deliverableTypeId = :deliverableTypeId")
	public List<Object[]> getProjectIdAndName(@Param("deliverableTypeId") int deliverableTypeId);
}
