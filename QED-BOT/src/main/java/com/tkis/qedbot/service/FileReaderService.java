package com.tkis.qedbot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileReaderService {

	public List<String> getProjectList();
	public String upload(MultipartFile file, String projectName, String tabletype, String userId, boolean isSave);
}
