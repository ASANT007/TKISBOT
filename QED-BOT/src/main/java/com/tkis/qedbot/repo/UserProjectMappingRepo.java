package com.tkis.qedbot.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tkis.qedbot.entity.UserProjectMapping;
@Repository
public interface UserProjectMappingRepo  extends JpaRepository<UserProjectMapping, Integer>{

	@Query("select upm.userId from UserProjectMapping upm where upm.userId =:userId and upm.projectId =:projectId and upm.status ='Active'")
	public String getActiveUserProjectMapping(@Param("userId")String userId, @Param("projectId") int projectId);

	public Optional<UserProjectMapping>findByUserIdAndProjectId(String userId, Integer projectId);

	

	

	/*
	 * @Query("select pm.projectId,pm.projectName from projectMaster pm inner join UserProjectMapping upm on pm.projectId = upm.projectId and upm.userId =:userId"
	 * ) List<Object[]> findProjectIdByUserId(@Param("userId") String userId);
	 */

}
 