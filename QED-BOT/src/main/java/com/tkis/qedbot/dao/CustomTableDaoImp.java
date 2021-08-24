package com.tkis.qedbot.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.tkis.qedbot.entity.RepositoryDetails;
import com.tkis.qedbot.repo.RepositoryDetailsRepo;

@Repository
public class CustomTableDaoImp implements CustomTableDao {
	
	public static void main(String [] args) {
		
		CustomTableDaoImp customTableDaoImp = new  CustomTableDaoImp();
		
		//customTableDaoImp.isTableCreated();
	}

	private static final Logger log = LoggerFactory.getLogger(CustomTableDaoImp.class);
	
	@Autowired
	private RepositoryDetailsRepo detailsRepo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	//Create table start
	
	@Override
	public List<String> getProjectList() {
		
		List<String> projectList = null;
		
		String sql = "select project_name from project_master";
		
		
		try 
		{
			Session session = null;
			if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
				throw new NullPointerException();
			}
			
			Query query = session.createNativeQuery(sql);
			
			projectList = query.getResultList();
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return projectList;
	}
		
	@Override
	@Transactional(rollbackOn = Exception.class)
	public boolean createTable(String createTableSQL, RepositoryDetails details) 
	{		
		
		Session session = null;
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
		//sql="CREATE TABLE IF NOT EXISTS Test (Id	INT(10) PRIMARY KEY,tables_name	VARCHAR (50) NOT NULL,File_name	VARCHAR (150), tables_types	VARCHAR (50) NOT NULL,"
		//		+ "created_by	VARCHAR (20) NOT NULL,creation_date	DATETIME NOT NULL)";

		//Query query = session.createSQLQuery("create  table IF NOT EXISTS sampletable1(col1 varchar(10),col2 int(10))");
	
		try 
		{
				
			  if( detailsRepo.save(details) != null ) 
			  {	
				  
					/*
					 * Query query = session.createSQLQuery(createTableSQL); int id =
					 * query.executeUpdate();
					 */
				  int i = session.createSQLQuery(createTableSQL).executeUpdate();
				  System.out.println("#### No of Rows Affected ::"+i);				 
				 
				 return true;
			  
			  }else 
			  {				  
				  throw new Exception(); 				  
			  }
			
		} catch (Exception e) {
			log.error("exception in createTable():", e);
			//throw new Exception("Exception "+e.getMessage(),e);
		}
		return false;
	}
	
	@Override
	public boolean isTablePresent(String tableName) {

		String sql = "";
		Session session = null;
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
		
		//sql="select * from information_schema.tables where table_name='"+tableName+"'";

		sql="select count(*) from information_schema.tables where table_name='"+tableName+"'";
		try {
			
			//BigInteger defVal = null;
			Query query = session.createSQLQuery(sql);

				// It work in MySQL START
			
			  BigInteger result = (BigInteger) query.getSingleResult();
			  if(result.intValue() > 0) { System.out.println("#### Table is present...");
			  return true; }
			 
			// It work in MySQL END
			  
			// It work in MSSQL
			/*
			 * int result = (int) query.getSingleResult();
			 * 
			 * System.out.println("#### result "+result);
			 * 
			 * if(result > 0) { System.out.println("#### Table is present..."); return true;
			 * }
			 */
			 
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("#### Exception "+ e.getMessage());
			//throw new Exception("Exception "+e.getMessage(),e);
		}
		return false;
	}
	//Create table end
	
	
	
	@Override
	public List<String> findAllColumns(String tableName) {
		
		Session session = null;
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		//tableName = "Project_001_master_test_table";
		System.out.println("#### findAllColumns : tableName "+tableName);
		Query query = session.createSQLQuery("DESCRIBE " +tableName);
		List<Object[]> list = query.getResultList();
		List<String> collect = list.stream().map( arr -> {
			return String.valueOf(arr[0]);
		}).collect(Collectors.toList());
		return collect;
	}

	
	@Modifying
	@Transactional
	@Override
	public List<String> alterTable(String tableName, String column) {
		
		Session session = null;
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}		
		
		List<String> colList = null;
		int id = 0;
		
		String sql = "alter table "+tableName+" add column "+column+" varchar(750)";
		
		try
		{
			
			Query query = session.createSQLQuery(sql);
			
			query.executeUpdate();
			
			colList = findAllColumns(tableName);
			
		} catch (Exception e) {
			//System.out.println("##### Exception :: alterTable : "+e);
			e.printStackTrace();
		}
		
		
		
		return colList;
	}

}
