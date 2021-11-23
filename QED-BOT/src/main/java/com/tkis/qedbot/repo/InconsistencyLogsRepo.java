package com.tkis.qedbot.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tkis.qedbot.entity.InconsistencyLogs;

public interface InconsistencyLogsRepo extends JpaRepository<InconsistencyLogs,Integer>{

		
		
		String projectWiseAndDateWiseReportSQL = " with data as(\r\n"
				+ "   select project_id as project_id, convert(date,date_of_entry, 105) as date_of_entry, initialcount as initialcount,\r\n"
				+ "   sum (resolvedcount) as sum_of_resolvedcount from inconsistency_logs_table\r\n"
				+ "   group by project_id, convert(date,date_of_entry, 105)  , initialcount)\r\n"
				+ "   select project_id,   convert(date,date_of_entry, 105) as date_of_entry , initialcount, sum_of_resolvedcount, sum(sum_of_resolvedcount) \r\n"
				+ "   over(order by project_id,  convert(date,date_of_entry, 105) ) as total, \r\n"
				+ "   initialcount-(sum(sum_of_resolvedcount)   over(order by project_id, convert(date,date_of_entry, 105))) as remainingInconsistency\r\n"
				+ "   from data where project_id=:project_id";

	/*
	 * @Query(
	 * value="select max(initialcount) from inconsistency_logs_table where project_Id=:projectId"
	 * , nativeQuery = true) int getInconsistencyLogsTableCount(int projectId);
	 */
	
	//InconsistencyLogs findTopByProjectId(@Param("projectId") int projectId) throws Exception;
	
	InconsistencyLogs findTopByProjectIdAndBatchId(@Param("projectId") int projectId, @Param("batchId") int batchId) throws Exception;
	
	@Query("select count(*) from InconsistencyLogs il where il.projectId=:projectId")
	int getInconsistencyLogsTableCountByProjectId(int projectId) throws Exception;
		
	@Query("select max(il.initialCount) from InconsistencyLogs il where il.projectId=:projectId")
	int getInconsistencyLogsTableCount(int projectId) throws Exception;
	
	//Commneted on 19-11-2021
	//@Query("select max(il.resolvedCount) from InconsistencyLogs il where il.projectId=:projectId")
	//int getLastResolvedCountByProjectId(int projectId) throws Exception;
	
	//Added on 19-11-2021
	@Query("select sum(il.resolvedCount) from InconsistencyLogs il where il.projectId=:projectId and batchId=:batchId")
	int getSumOfResolvedCountByProjectIdAndBatchId(@Param("projectId") int projectId, @Param("batchId") int batchId);
	
	
	//@Query(value = "select FORMAT(max(il.date_Of_Entry),'dd-MM-yyyy') as date from [inconsistency_logs_table] il where il.[project_id]=project_Id",nativeQuery = true)
	//String getDateOfEntryProjectId(@Param("project_Id") int projectId) throws Exception;
	
	@Query(value = "select datediff(DAY,convert(date,max(il.date_of_entry),105),convert(date,GETDATE(),105)) from [inconsistency_logs_table] il where il.[project_id]=:project_id",nativeQuery = true)
	String getDateOfEntryProjectId(@Param("project_id") int projectId) throws Exception;
	
	/*
	@Query(value = "select il.initialcount, sum(il.resolvedcount) as resolvedcount ,convert(date,il.date_of_entry,105) as dateofEntry from [inconsistency_logs_table] il where il.project_id=:project_id group by il.initialcount, convert(date,il.date_of_entry,105)",nativeQuery = true)
	List<Object[]> getProjectAndDateWiseReportData(@Param("project_id") int projectId) throws Exception;
	*/
	@Query(value = projectWiseAndDateWiseReportSQL,nativeQuery = true)
	List<Object[]> getProjectAndDateWiseReportData(@Param("project_id") int projectId) throws Exception;
	
	@Query(value = "select distinct(convert(date,date_of_entry, 105)) from [inconsistency_logs_table] il, project_master pm where il.project_id=pm.project_id and pm.deliverabletype_id=:deliverabletype_id order by convert(date,date_of_entry, 105)",nativeQuery = true)
	List<Object[]> getAllProjectInconsistencyCheckDateByDeliverableType(@Param("deliverabletype_id") int deliverabletype_id) throws Exception;

	@Query("select max(il.srNo) from InconsistencyLogs il where il.projectId=:projectId")
	int getMaxSrNoByProjectId(@Param("projectId") int projectId);

	@Query("select max(il.batchId) from InconsistencyLogs il where il.projectId=:projectId")
	int getMaxBatchIdByProjectId(int projectId);
	
	
	//Optional<InconsistencyLogs> findTopByOrderBySrNoDesc(@PathVariable("resolvedCount") int resolvedCount, @PathVariable("projectId") int projectId);
	//InconsistencyLogs findTopByOrderBySrNoDesc();
	//InconsistencyLogs findByProjectIdOrderBySrNoDesc(@PathVariable("projectId") int projectId);
	//Optional<InconsistencyLogs> findTopByOrderByProjectIdDesc();

	//Optional<InconsistencyLogs> findTopByOrderByProjectIdAndSrNoDesc();

	//InconsistencyLogs findTopResolvedCountByProjectIdOrderBySrNoDesc(@PathVariable("projectId") int projectId);
	
	//InconsistencyLogs findResolvedCountByProjectIdOrderBySrNoDesc(int projectId);
	
	//@Query(value="select max(resolvedcount) from inconsistency_logs_table where project_Id =:projectId order by srno desc",nativeQuery = true)
	//int findResolvedCountByProjectIdOrderBySrNoDesc(@Param("projectId") int projectId);
	
	//Project Wise Report SQL  22-11-2021 Start
	String pIdNameTotalInconsistencySQL = "select il.project_id, pm.project_name,max(il.initialcount) as totalInc from inconsistency_logs_table il,"
			+ "  project_master pm where il.project_id = pm.project_id and pm.deliverabletype_id=:deliverabletype_id group by il.project_id,pm.project_name";
	
	@Query(value = pIdNameTotalInconsistencySQL, nativeQuery = true)
	List<Object[]> getPIdNameTotcalInconsistencyData(@Param("deliverabletype_id") int deliverabletype_id);
	
	//Project Wise Report SQL  22-11-2021 End
	
	
}
