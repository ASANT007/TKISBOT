package com.tkis.qedbot.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tkis.qedbot.entity.RuleMaster;
import com.tkis.qedbot.service.RuleMasterService;

public interface RuleMasterRepo extends JpaRepository<RuleMaster, Integer>{
	
	


	//@Query("select ruleId, repositoryId, ruleDesc, ruleType, status from RuleMaster where projectId =:projectId order by ruleId")
	//public List<Object[]> getRuleById(@Param("projectId") int projectId) throws Exception;
	
	@Query("select ruleDesc from RuleMaster where ruleId =:ruleId order by ruleId")	
	public String getRuleDescById(@Param("ruleId") int ruleId) throws Exception;

	//@Query("select ruleId, repositoryId, ruleDesc, ruleType, status from RuleMaster where projectId =:projectId and status = 'Active' order by ruleId")
	//public List<Object[]> getRuleByIdForExecute(int projectId) throws Exception;;
	
	@Query("select repositoryId  from RuleMaster where ruleId =:ruleId")
	public int getRepoIdById(@Param("ruleId") int ruleId) throws Exception;
	/*
	 * @Transactional
	 * 
	 * @Modifying
	 * 
	 * @Query(value= ruleDesc,nativeQuery = true) public int executeRule() throws
	 * Exception;
	 */

	
	
	

}
