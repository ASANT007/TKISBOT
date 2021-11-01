package com.tkis.qedbot.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tkis.qedbot.entity.DeliverabletypeMaster;

@Repository
public interface DeliverabletypeMasterRepo extends JpaRepository<DeliverabletypeMaster, Integer>
{

	@Query("select deliverableTypeId, deliverableTypeShortname from DeliverabletypeMaster where status = 'Active'")
	public List<Object[]> getDeliverableIdAndShortName() throws Exception;
	
	//Integration of DeliverableTypeMaster added on 28-10-2021 START
	@Query("select case when count(d)> 0 then true else false end from DeliverabletypeMaster d where upper(d.deliverableTypeShortname) = upper(:shortName)  and d.deliverableTypeId<>(:deliverableTypeId)")
	boolean checkDuplicateShortName(@Param("shortName") String shortName,@Param("deliverableTypeId") int deliverableTypeId);

	@Query("select case when count(d)> 0 then true else false end from DeliverabletypeMaster d where upper(d.deliverableTypeName) = upper(:fullName)  and d.deliverableTypeId<>(:deliverableTypeId)")
	boolean checkDuplicateFullName(@Param("fullName") String fullName,@Param("deliverableTypeId") int deliverableTypeId);
	//Integration of DeliverableTypeMaster added on 28-10-2021 END
}
