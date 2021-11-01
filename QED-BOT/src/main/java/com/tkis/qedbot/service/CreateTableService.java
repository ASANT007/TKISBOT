package com.tkis.qedbot.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;


public interface CreateTableService 
{

	public List<String> getProjectList();
	
	public String genrateTableStructure(MultipartFile file, String projectName, String deliverableTypeName, String tabletype) throws Exception;
	
	public String saveTableStructure(String userId, int projectId, String tabletype, String originalFilename,
			String tablename, String columnArray, String keyField) throws Exception;
}
