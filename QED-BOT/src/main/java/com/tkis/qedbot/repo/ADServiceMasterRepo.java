package com.tkis.qedbot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tkis.qedbot.entity.ADServiceMaster;

@Repository
public interface ADServiceMasterRepo extends JpaRepository<ADServiceMaster,Integer> {

	
}
