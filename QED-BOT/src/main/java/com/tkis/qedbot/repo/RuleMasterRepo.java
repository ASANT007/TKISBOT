package com.tkis.qedbot.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tkis.qedbot.entity.RuleMaster;

public interface RuleMasterRepo extends JpaRepository<RuleMaster, Integer>{
	
	@Query("select ruleId, repositoryId, ruleDesc, ruleType, status from RuleMaster where projectId =:projectId")	
	public List<Object[]> getRuleById(@Param("projectId") int projectId) throws Exception;

	
	
	

}
