package com.tkis.qedbot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tkis.qedbot.repo.DeliverabletypeMasterRepo;

@Component
public class DeliverabletypeMasterServiceImpl implements DeliverabletypeMasterService 
{

	@Autowired
	DeliverabletypeMasterRepo deliverabletypeMasterRepo;
	
	@Override
	public List<Object[]> getDeliverableIdAndShortName() throws Exception {
		
		return deliverabletypeMasterRepo.getDeliverableIdAndShortName();
	}

}
