package com.tkis.qedbot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CreateTableService 
{

	public List<String> getProjectList();
	public String upload(MultipartFile file, String projectName, String deliverableTypeName, String tabletype, String userId, boolean isSave);
}
