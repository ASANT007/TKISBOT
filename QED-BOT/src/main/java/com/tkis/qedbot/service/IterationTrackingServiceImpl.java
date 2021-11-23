package com.tkis.qedbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkis.qedbot.repo.IterationTrackingRepo;

@Service
public class IterationTrackingServiceImpl implements IterationTrackingService {

	private static final Logger log = LoggerFactory.getLogger(IterationTrackingServiceImpl.class);

	@Autowired
	IterationTrackingRepo iterationTrackingRepo;
	@Override
	public int getMaxBatchIdByProjectId(int projectId) {


		return iterationTrackingRepo.getMaxBatchIdByProjectId(projectId);
	}
}
