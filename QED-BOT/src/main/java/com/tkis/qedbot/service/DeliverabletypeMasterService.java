package com.tkis.qedbot.service;

import java.util.List;

import com.tkis.qedbot.entity.DeliverabletypeMaster;


public interface DeliverabletypeMasterService 
{

	public List<Object[]> getDeliverableIdAndShortName() throws Exception;
	
	//Integration of DeliverableTypeMaster added on 28-10-2021 START
	List<DeliverabletypeMaster> getAllDeliverableTypeMasters();

	String checkDuplicateDeliverableType(String shortName, String fullName, int deliverableTypeId);

	boolean addDeliverableTypeDetails(DeliverabletypeMaster deliverabletype);

	DeliverabletypeMaster editDeliverableTypeMasterById(int deliverableTypeId);

	boolean editDeliverableType(int deliverableTypeId, String shortName, String fullName, String status,
			String lastUpdatedBy);
	//Integration of DeliverableTypeMaster 28-10-2021 END
}
