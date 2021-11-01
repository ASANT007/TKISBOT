package com.tkis.qedbot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tkis.qedbot.entity.InconsistencyLogs;

public interface InconsistencyLogsRepo extends JpaRepository<InconsistencyLogs,Integer>{

		InconsistencyLogs findTopByProjectId(@Param("projectId") int projectId) throws Exception;

	/*
	 * @Query(
	 * value="select max(initialcount) from inconsistency_logs_table where project_Id=:projectId"
	 * , nativeQuery = true) int getInconsistencyLogsTableCount(int projectId);
	 */
		
	@Query(value="select count(*) from InconsistencyLogs il where il.projectId=:projectId")
	int getInconsistencyLogsTableCountByProjectId(int projectId) throws Exception;
		
	@Query(value="select max(il.initialCount) from InconsistencyLogs il where il.projectId=:projectId")
	int getInconsistencyLogsTableCount(int projectId) throws Exception;
	
	@Query("select max(il.resolvedCount) from InconsistencyLogs il where il.projectId=:projectId")
	int getLastResolvedCountByProjectId(int projectId) throws Exception;

	
	//Optional<InconsistencyLogs> findTopByOrderBySrNoDesc(@PathVariable("resolvedCount") int resolvedCount, @PathVariable("projectId") int projectId);
	//InconsistencyLogs findTopByOrderBySrNoDesc();
	//InconsistencyLogs findByProjectIdOrderBySrNoDesc(@PathVariable("projectId") int projectId);
	//Optional<InconsistencyLogs> findTopByOrderByProjectIdDesc();

	//Optional<InconsistencyLogs> findTopByOrderByProjectIdAndSrNoDesc();

	//InconsistencyLogs findTopResolvedCountByProjectIdOrderBySrNoDesc(@PathVariable("projectId") int projectId);
	
	//InconsistencyLogs findResolvedCountByProjectIdOrderBySrNoDesc(int projectId);
	
	//@Query(value="select max(resolvedcount) from inconsistency_logs_table where project_Id =:projectId order by srno desc",nativeQuery = true)
	//int findResolvedCountByProjectIdOrderBySrNoDesc(@Param("projectId") int projectId);
}
