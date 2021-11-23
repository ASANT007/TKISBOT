package com.tkis.qedbot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tkis.qedbot.entity.IterationTracking;

@Repository
public interface IterationTrackingRepo extends JpaRepository<IterationTracking, Integer> {	
			
	String maxBatchIdSQL = "select max(it.batchId) from IterationTracking it where it.projectId=:projectId";
	
	@Query(maxBatchIdSQL)
	int getMaxBatchIdByProjectId(int projectId);

}
