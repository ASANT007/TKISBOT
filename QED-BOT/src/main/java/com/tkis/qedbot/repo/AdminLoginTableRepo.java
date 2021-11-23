package com.tkis.qedbot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tkis.qedbot.entity.AdminLoginTable;

public interface AdminLoginTableRepo extends JpaRepository<AdminLoginTable, Integer>{

	@Query("select case when count(t)> 0 then true else false end from AdminLoginTable t where upper(t.userId) = upper(:username) and upper(t.password) = upper(:password)")
	boolean authenticateUser(@Param("username") String username, @Param("password") String password);

	AdminLoginTable findInfoByUserId(String userId);

}
