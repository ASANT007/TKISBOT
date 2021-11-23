package com.tkis.qedbot.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkis.qedbot.entity.AdminLoginTable;
import com.tkis.qedbot.repo.AdminLoginTableRepo;

@Service
public class AdminLoginTableServiceImpl implements AdminLoginTableService {
	
	private static final Logger log = LoggerFactory.getLogger(AdminLoginTableServiceImpl.class);

	@PersistenceContext
	private EntityManager entityManager;


	@Autowired
	private AdminLoginTableRepo adminLoginTableRepo;
	
	@Override
	@Transactional(rollbackOn  = Exception.class)
	public boolean editAdminLoginDetails(int adminSrNo,  String password, 
			String lastUpdatedBy, Date lastUpdationDate) {
		Session session = null;
		int  modifications=0;
		boolean succ=false;
		
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
	
		try {
			Query query = session.createQuery("update AdminLoginTable set password= :password,lastUpdatedBy= :lastUpdatedBy,lastUpdationDate= :lastUpdationDate where srNo=:adminSrNo");
			
			query.setParameter("password",password);
			query.setParameter("adminSrNo",adminSrNo);
			
			query.setParameter("lastUpdatedBy",lastUpdatedBy);
			query.setParameter("lastUpdationDate",lastUpdationDate);
			
			
			modifications = query.executeUpdate();

			 succ = (modifications > 0);
		} catch (Exception e) {
			succ = false;
			log.error("editAdminLoginDetails()", e);
			e.printStackTrace();
		}
	
		return succ;

	}

	@Override
	public boolean authenticateUser(String username, String password) {

		return adminLoginTableRepo.authenticateUser(username,password);
	}

	@Override
	public AdminLoginTable getUserDetails(String userId) {
		return adminLoginTableRepo.findInfoByUserId(userId);
	}

}
