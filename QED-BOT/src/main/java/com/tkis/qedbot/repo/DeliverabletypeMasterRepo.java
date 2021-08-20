package com.tkis.qedbot.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tkis.qedbot.entity.DeliverabletypeMaster;

public interface DeliverabletypeMasterRepo extends JpaRepository<DeliverabletypeMaster, Integer>{

	@Query("select deliverableTypeId, deliverableTypeShortname from DeliverabletypeMaster")
	public List<Object[]> getDeliverableIdAndShortName() throws Exception;
}
