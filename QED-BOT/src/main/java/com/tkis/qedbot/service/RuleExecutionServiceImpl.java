package com.tkis.qedbot.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

@Service
public class RuleExecutionServiceImpl implements RuleExecutionService 
{
	
	
	private static final Logger log = LoggerFactory.getLogger(RuleExecutionServiceImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Modifying
	@Override
	public int executeRule(String ruleDesc) throws Exception 
	{
		Session session = null;
		int i = 0;
		
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
		
		i = session.createSQLQuery(ruleDesc).executeUpdate();
		
		return i;
	}

}
