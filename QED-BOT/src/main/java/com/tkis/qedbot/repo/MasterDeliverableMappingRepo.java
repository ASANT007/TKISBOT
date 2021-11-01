package com.tkis.qedbot.repo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tkis.qedbot.entity.MasterDeliverableMapping;

@Repository
public interface MasterDeliverableMappingRepo extends JpaRepository<MasterDeliverableMapping, Integer>{

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

}
