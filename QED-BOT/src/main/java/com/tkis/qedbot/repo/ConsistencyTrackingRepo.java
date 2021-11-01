package com.tkis.qedbot.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tkis.qedbot.entity.ConsistencyTracking;

public interface ConsistencyTrackingRepo extends JpaRepository<ConsistencyTracking, Integer> {

}
