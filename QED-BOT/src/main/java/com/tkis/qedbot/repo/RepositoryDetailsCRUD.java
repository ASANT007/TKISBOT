package com.tkis.qedbot.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tkis.qedbot.entity.RepositoryDetails;

public interface RepositoryDetailsCRUD extends CrudRepository<RepositoryDetails,Integer>{
	
	// Query JPQL
	
		// ERROR : org.hibernate.hql.internal.ast.QuerySyntaxException: repository_details is not mapped.
		
		// We should use entity name rather than table name.
		/*
		 * @Query("SELECT tablesName FROM RepositoryDetails WHERE  tablesName LIKE :filterTableNames% "
		 * ) public List<String> getAllTablesName(@Param("filterTableNames") String
		 * filterTableNames);
		 * 
		 * @Query("select tablesName from RepositoryDetails where projectId = :projectId"
		 * ) public List<String> getAllTablesByProjectId(@Param("projectId") int
		 * projectId);
		 * 
		 * @Transactional
		 * 
		 * @Modifying(clearAutomatically = true)
		 * 
		 * @Query("UPDATE RepositoryDetails rd SET rd.fileName = :fileName WHERE rd.tablesName = :tableName"
		 * ) int updateFileName(@Param("fileName") String fileName, @Param("tableName")
		 * String tableName);
		 * 
		 * @Query("SELECT rd.fileName FROM RepositoryDetails rd WHERE rd.tablesName = :tableName"
		 * ) public String getFileName(@Param("tableName") String tableName);
		 */
}
