package com.tkis.qedbot.repo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tkis.qedbot.entity.MasterDeliverableMapping;

@Repository
public interface MasterDeliverableMappingRepo extends JpaRepository<MasterDeliverableMapping, Integer>{

	String getMappedDeliverableFieldByKeyFieldSQL = "select mdm.deliverable_field from master_deliverable_mapping mdm, repository_details rd where mdm.project_id = rd.project_id\r\n"
			+ "  and mdm.deliverable_table = rd.tables_name and rd.key_field=:key_field and rd.project_id=:project_id and mdm.status='Active'";
	
	@Query("select mdMappingId, masterTable, masterField, deliverableTable, deliverableField from MasterDeliverableMapping where projectId = :projectId and status = 'Active'")
	List<Object[]> getMasterDeliverableMapping(int projectId) throws Exception;

	@Transactional
	@Modifying
	@Query("update MasterDeliverableMapping set status = 'Inactive', lastUpdatedBy =:userId, lastUpdationDate =:timestamp  where mdMappingId = :mdId")	
	int updateMasterDeliverableMapping(int mdId, String userId, Timestamp timestamp) throws Exception;

	@Query("select case when count(md)> 0 then true else false end from MasterDeliverableMapping md where "
			+ "md.projectId = :projectId and md.masterTable = :masterTable and md.masterField = :masterField "
			+ "and md.deliverableTable = :deliverableTable and md.deliverableField = :deliverableField and md.status = 'Active'")
	boolean isMappingPresent(int projectId, String masterTable, String masterField, String deliverableTable,
			String deliverableField);

	@Query(value = getMappedDeliverableFieldByKeyFieldSQL, nativeQuery = true)
	List<String>getMappedDeliverableFieldByKeyField(@Param("project_id") int project_id, @Param("key_field")String filterKeyField);

}
