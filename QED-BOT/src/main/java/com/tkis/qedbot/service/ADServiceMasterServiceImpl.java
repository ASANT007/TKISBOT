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

import com.tkis.qedbot.entity.ADServiceMaster;
import com.tkis.qedbot.repo.ADServiceMasterRepo;

@Service
public class ADServiceMasterServiceImpl implements ADServiceMasterService {

	@Autowired
	private ADServiceMasterRepo adServiceMasterRepo;
	
	private static final Logger log = LoggerFactory.getLogger(ADServiceMasterServiceImpl.class);

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<ADServiceMaster> getAllADServices() {
		return adServiceMasterRepo.findAll();
	}

	@Override
	public ADServiceMaster adServiceInfoById(int adServiceId) {
		Optional<ADServiceMaster> adServiceInfo = adServiceMasterRepo
				.findById(adServiceId);
		if (adServiceInfo.isPresent()) {
			return adServiceInfo.get();
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackOn  = Exception.class)
	public boolean editADServiceDetails(int adServiceId, String userId, String password,
			String ldapUrl, String status, String lastUpdatedBy, Date lastUpdationDate) {
		Session session = null;
		int  modifications=0;
		boolean succ=false;
		
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
	
		try {
			Query query = session.createQuery("update ADServiceMaster set userId= :userId,password= :password,ldapUrl= :ldapUrl,lastUpdatedBy= :lastUpdatedBy,lastUpdationDate= :lastUpdationDate,status= :status where serviceId=:adServiceId");
			
			query.setParameter("userId",userId);
			query.setParameter("password",password);
			query.setParameter("ldapUrl",ldapUrl);
			query.setParameter("lastUpdatedBy",lastUpdatedBy);
			query.setParameter("lastUpdationDate",lastUpdationDate);
			query.setParameter("status",status);
			query.setParameter("adServiceId",adServiceId);
			
			modifications = query.executeUpdate();

			 succ = (modifications > 0);
		} catch (Exception e) {
			succ = false;
			log.error("editADServiceDetails()", e);
			e.printStackTrace();
		}
	
		return succ;

	}

}
