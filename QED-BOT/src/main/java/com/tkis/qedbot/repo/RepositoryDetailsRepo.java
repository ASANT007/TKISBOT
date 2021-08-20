package com.tkis.qedbot.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tkis.qedbot.entity.RepositoryDetails;
@Repository
public interface RepositoryDetailsRepo extends JpaRepository<RepositoryDetails,Integer>{

		// We should use entity name rather than table name.
		/*
		 * @Query("select tablesName from RepositoryDetails where tablesName like :filterTableNames% "
		 * ) public List<String> getAllTablesName(@Param("filterTableNames") String
		 * filterTableNames);
		 */
		
		@Query("select repositoryId, tablesName from RepositoryDetails where projectId = :projectId")
		public List<Object[]> getAllTablesByProjectId(@Param("projectId") int projectId) throws Exception;
		
		/*
		 * @Transactional
		 * 
		 * @Modifying(clearAutomatically = true)
		 * 
		 * @Query("update RepositoryDetails rd set rd.fileName = :fileName where rd.tablesName = :tableName"
		 * ) int updateFileName(@Param("fileName") String fileName, @Param("tableName")
		 * String tableName);
		 */
		
		/*
		 * @Query("select rd.fileName from RepositoryDetails rd where rd.tablesName = :tableName"
		 * ) public String getFileName(@Param("tableName") String tableName);
		 */
		
		//Changed on 19-08-2021
		@Query("select fileName from RepositoryDetails  where repositoryId = :repositoryId")
		public String getFileName(@Param("repositoryId") int repositoryId) throws Exception;
		
		@Transactional
		@Modifying(clearAutomatically = true)
	    @Query("update RepositoryDetails set fileName = :fileName where repositoryId = :repositoryId")
	    int updateFileName(@Param("fileName") String fileName, @Param("repositoryId") int repositoryId) throws Exception;
		
		@Query("select tablesName from RepositoryDetails where repositoryId = :repositoryId")
		public String getTableNameFromRepositoryId(int repositoryId) throws Exception;
}
