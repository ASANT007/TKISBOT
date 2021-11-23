package com.tkis.qedbot.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tkis.qedbot.entity.ConsistencyTracking;

public interface ConsistencyTrackingRepo extends JpaRepository<ConsistencyTracking, Integer> {
	
	//String projectWiseReportDataSQL = "select il.project_id, pm.project_name,il.initialcount, sum(il.resolvedcount) as totalResolved from inconsistency_logs_table il,\r\n"
	//		+ "  project_master pm where il.project_id = pm.project_id and pm.deliverabletype_id=:deliverabletype_id group by il.project_id, il.initialcount,pm.project_name";
	
	String fieldWiseReportDataSQL = "select mdm.deliverable_field, sum(ct.flag_count) from consistency_tracking_table ct, \r\n"
			+ "  master_deliverable_mapping mdm where ct.md_mappingid=mdm.md_mappingid and mdm.project_id=:project_id group by \r\n"
			+ "  ct.md_mappingid, mdm.deliverable_field";
		
	@Query(value = fieldWiseReportDataSQL, nativeQuery = true)
	List<Object[]> getFieldWiseReportData(@Param("project_id") int project_id);

	

}
