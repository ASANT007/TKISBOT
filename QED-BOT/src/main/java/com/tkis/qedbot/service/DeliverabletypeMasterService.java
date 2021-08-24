package com.tkis.qedbot.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface DeliverabletypeMasterService 
{

	public List<Object[]> getDeliverableIdAndShortName() throws Exception;
}
