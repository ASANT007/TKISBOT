package com.tkis.qedbot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import javax.servlet.http.HttpSession;
//import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tkis.qedbot.dao.UserDao;

@Repository
public class UserDaoImpl implements UserDao{
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public boolean authenticateUser(String userid, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Object> getAllUserProjects() {
		// TODO Auto-generated method stub
		
		List<Object > entity = null;
		String sql = "select * from repository_details";
		
		
		/*
		 * Session session = null;
		 * 
		 * if (entityManager == null || (session = entityManager.unwrap(Session.class))
		 * == null) { throw new NullPointerException(); } entity =
		 * session.createNamedQuery(sql).getResultList();
		 */
		 
		
		//String userid = (String) session.getAttribute("userid");
		//entity = entityManager.createNamedQuery(sql).getResultList(); //Hibernate Query
		entity = entityManager.createNativeQuery(sql).getResultList(); // Native sql
		for(Object data : entity) {
			System.out.println(String.valueOf(data));
		}
		System.out.println(entity.toString());
		return entity;
	}

	@Override
	public List<Object> getProjectDetails() {
		// TODO Auto-generated method stub
		List<Object> entity = null;
		String sql = "";
		
		/*
		 * Session session = null;
		 * 
		 * if (entityManager == null || (session = entityManager.unwrap(Session.class))
		 * == null) { throw new NullPointerException(); }
		 */
		 
		
		//String userid = (String) session.getAttribute("userid");
		entity = entityManager.createNamedQuery(sql).getResultList();
		return entity;
	}

	@Override
	public List<Object> getAllProjectTracking() {
		List<Object> entity = null;
		String sql = "";
		
		/*
		 * Session session = null;
		 * 
		 * if (entityManager == null || (session = entityManager.unwrap(Session.class))
		 * == null) { throw new NullPointerException(); }
		 */
		 
		
		//String userid = (String) session.getAttribute("userid");
		entity = entityManager.createNamedQuery(sql).getResultList();
		return entity;
	}

}
