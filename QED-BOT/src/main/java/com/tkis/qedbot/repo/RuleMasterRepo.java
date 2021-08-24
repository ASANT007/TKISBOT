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
	
	


	@Query("select ruleId, repositoryId, ruleDesc, ruleType, status from RuleMaster where projectId =:projectId")	
	public List<Object[]> getRuleById(@Param("projectId") int projectId) throws Exception;
	
	@Query("select ruleDesc from RuleMaster where ruleId =:ruleId")	
	public String getRuleDescById(@Param("ruleId") int ruleId) throws Exception;
	
	/*
	 * @Transactional
	 * 
	 * @Modifying
	 * 
	 * @Query(value= ruleDesc,nativeQuery = true) public int executeRule() throws
	 * Exception;
	 */

	
	
	

}
