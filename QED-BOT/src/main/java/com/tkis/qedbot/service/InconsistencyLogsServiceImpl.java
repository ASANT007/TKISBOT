package com.tkis.qedbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkis.qedbot.repo.InconsistencyLogsRepo;

@Service
public class InconsistencyLogsServiceImpl implements InconsistencyLogsService {

	private static final Logger log = LoggerFactory.getLogger(InconsistencyLogsServiceImpl.class);
	@Autowired
	InconsistencyLogsRepo inconsistencyLogsRepo;
	
	
	@Override
	public int getInconsistencyLogsTableCountByProjectId(int projectId) {
		int count = 0;
		try {
			count = inconsistencyLogsRepo.getInconsistencyLogsTableCountByProjectId(projectId);
		}catch(Exception e) {
			System.out.println("#### Exception InconsistencyLogsServiceImpl :: getInconsistencyLogsTableCountByProjectId : "+e);
		}
		return count;
	}
	
	@Override
	public int getInconsistencyLogsTableCount(int projectId) {
		int count = 0;
		try {
			count = inconsistencyLogsRepo.getInconsistencyLogsTableCount(projectId);
		}catch(Exception e) {
			System.out.println("#### Exception InconsistencyLogsServiceImpl :: getInconsistencyLogsTableCount : "+e);
		}
		return count;
	}

	@Override
	public int getLastResolvedCountByProjectId(int projectId) {
		
		int count = 0;
		try {
			count = inconsistencyLogsRepo.getLastResolvedCountByProjectId(projectId);
		}catch(Exception e) {
			System.out.println("#### Exception InconsistencyLogsServiceImpl :: getLastResolvedCountByProjectId : "+e);
		}
		return count;
	}

	

}
