package com.tkis.qedbot.service;

import java.util.List;

public interface UserProjectMappingService {

	List<Object[]> getProjectIdList(int deliverableTypeId) throws Exception;

	String getMappingProjectsWithUser(String mappedUser, int deliverableTypeId);

	String saveUserProjectMapping(String mappedUser, String addUserProject, String removeUserProject);

	String removeAllProjectMappingWithUser(String mappedUser, String removeProject, String userId);

}
