package com.tkis.qedbot.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tkis.qedbot.entity.DeliverabletypeMaster;
import com.tkis.qedbot.repo.DeliverabletypeMasterRepo;

@Service
public class DeliverabletypeMasterServiceImpl implements DeliverabletypeMasterService 
{
	private static final Logger log = LoggerFactory.getLogger(DeliverabletypeMasterServiceImpl.class);
	
	@Autowired
	DeliverabletypeMasterRepo deliverabletypeMasterRepo;
	
	@Override
	public List<Object[]> getDeliverableIdAndShortName() throws Exception {
		
		return deliverabletypeMasterRepo.getDeliverableIdAndShortName();
	}
	
	//Integration of DeliverableTypeMaster added on 28-10-2021 START
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<DeliverabletypeMaster> getAllDeliverableTypeMasters() {
		return deliverabletypeMasterRepo.findAll();
	}

	@Override
	public String checkDuplicateDeliverableType(String shortName, String fullName, int deliverableTypeId) {
		
		if(shortName!=null) {
		boolean isShortNameExist=deliverabletypeMasterRepo.checkDuplicateShortName(shortName, deliverableTypeId);
		if(isShortNameExist) {
			return "true|| short name";
		}
		}
		boolean isProjectNameExist=deliverabletypeMasterRepo.checkDuplicateFullName(fullName, deliverableTypeId);
		if(isProjectNameExist) {
			return "true|| long name";
		}
		return "false";
	}

	@Override
	public boolean addDeliverableTypeDetails(DeliverabletypeMaster deliverabletype) {
		boolean isAdded = false;
		try {
			DeliverabletypeMaster deliverableType = deliverabletypeMasterRepo.save(deliverabletype);
			if (deliverableType != null)
				isAdded = true;

		} catch (Exception e) {
			e.printStackTrace();
			isAdded = false;
		}
		return isAdded;
	}
	
	@Override
	public DeliverabletypeMaster editDeliverableTypeMasterById(int deliverableTypeId) {
		Optional<DeliverabletypeMaster> deliverableTypeMasterInfo = deliverabletypeMasterRepo
				.findById(deliverableTypeId);
		if (deliverableTypeMasterInfo.isPresent()) {
			return deliverableTypeMasterInfo.get();
		} else {
			return null;
		}
	}
	
	@Override
	@Transactional(rollbackOn  = Exception.class)
	public boolean editDeliverableType(int deliverabletype_id,  String fullName, String status,String lastUpdatedBy, String lastUpdationDate) {
		Session session = null;
		int  modifications=0;
		boolean succ=false;
		
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
	
		try {
			Query query = session.createNativeQuery("update deliverabletype_master set deliverabletype_name= :fullName,last_updated_by= :lastUpdatedBy,last_updation_date= :lastUpdationDate,status= :status where deliverabletype_id=:deliverabletype_id");
			
			query.setParameter("fullName",fullName);
			query.setParameter("lastUpdatedBy",lastUpdatedBy);
			query.setParameter("lastUpdationDate",lastUpdationDate);
			query.setParameter("status",status);
			query.setParameter("deliverabletype_id",deliverabletype_id);
			
			modifications = query.executeUpdate();

			 succ = (modifications > 0);
		} catch (Exception e) {
			succ = false;
			log.error("editDeliverableType()", e);
			e.printStackTrace();
		}
	
		return succ;
	}

}
