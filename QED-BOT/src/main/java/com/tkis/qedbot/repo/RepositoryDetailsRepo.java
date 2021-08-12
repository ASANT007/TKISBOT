package com.tkis.qedbot.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tkis.qedbot.entity.RepositoryDetails;
@Repository
public interface RepositoryDetailsRepo extends JpaRepository<RepositoryDetails,Integer>{

	// Same can be written and work in RepositoryDetailsCRUD
	/*
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query("UPDATE RepositoryDetails rd SET rd.fileName = :fileName WHERE rd.tablesName = :tableName")
    int updateFileName(@Param("fileName") String fileName, @Param("tableName") String tableName);
    */
}