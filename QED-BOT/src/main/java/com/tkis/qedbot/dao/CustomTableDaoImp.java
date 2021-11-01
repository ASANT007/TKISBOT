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
	public boolean createTable(String createTableSQL, RepositoryDetails details) throws Exception 
	{	
		Session session = null;
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
		if( detailsRepo.save(details) != null ) 
		{	
			int i = session.createSQLQuery(createTableSQL).executeUpdate();
			System.out.println("#### No of Rows Affected :: "+i);
			return true;
		  
		}else 
		{				  
			throw new Exception(); 				  
		}	
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
			
				/*
				 * BigInteger result = (BigInteger) query.getSingleResult();
				 * if(result.intValue() > 0) { System.out.println("#### Table is present...");
				 * return true; }
				 */
			 
			// It work in MySQL END
			  
			// It work in MSSQL
			
			  int result = (int) query.getSingleResult();
			  
			  System.out.println("#### result "+result);
			  
			  if(result > 0) {
				  System.out.println("#### Table is present..."); 
				  return true;
			  }
			 
			 
			
			
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
		
		//Query query = session.createSQLQuery("DESCRIBE " +tableName);//MySQL
		//SQLServer e.g
		//EXEC sp_columns Project_002_deliverable_big_file_test
		//table_qualifier, table_owner, table_name, columns_name, data_type_type_name,........
		//String.valueOf(arr[3]) is columns_name
		Query query = session.createSQLQuery("EXEC sp_columns " +tableName);//SQLServer
		
		List<Object[]> list = query.getResultList();
		List<String> collect = list.stream().map( arr -> {
			//return String.valueOf(arr[0]);
			return String.valueOf(arr[3]);
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
		
		//String sql = "alter table "+tableName+" add column "+column+" varchar(750)";// My SQL
		
		String sql = "alter table "+tableName+" add "+column+" varchar(750)";// SQL Server
		
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
	
	@Override
	public int getRowCount(String tableName) {

		String sql = "";
		int row = 0;
		Session session = null;
		
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
		sql="select count(*) from "+tableName;
		try 
		{			
			
			Query query = session.createSQLQuery(sql);
			row = (int) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("#### Exception "+ e.getMessage());
			
		}
		return row;
	}

	@Override
	public String getkeyfield(String tableName) {
		String sql = "", response = "";
		
		Session session = null;
		
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
		sql = "select [key_field] from [repository_details] where tables_name = '"+tableName+"'";
		try 
		{			
			
			Query query = session.createSQLQuery(sql);
			response = query.getSingleResult().toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("#### Exception "+ e.getMessage());
			
		}
		return response;
	}

	@Transactional
	@Modifying
	@Override
	public String updateKeyField(String tableName, String keyField) {
		String sql = "", response = "";
		
		Session session = null;
		
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
		sql = "update [repository_details] set [key_field] = '"+keyField+"' where tables_name = '"+tableName+"'";
		try 
		{			
			
			Query query = session.createSQLQuery(sql);
			int i = query.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("#### Exception "+ e.getMessage());
			
		}
		return "success";
	}

}
