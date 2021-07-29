package com.tkis.qedbot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
			
		}
		
		return projectList;
	}

	/*
	 * @Override
	 * 
	 * @Transactional(rollbackOn = Exception.class) public boolean isTableCreated()
	 * {
	 * 
	 * Session session = null; if(entityManager == null || session =
	 * entityManager.unwrap(Session.class) == null) { throw new
	 * NullPointerException(); }
	 * 
	 * 
	 * String sql = "Create table test (col1 int(10), name varchar(20))"; Query
	 * query = entityManager.createNativeQuery(sql);
	 * 
	 * return false; }
	 */

		
	@Override
	@Transactional(rollbackOn = Exception.class)
	public boolean createTable(String createTableSQL, RepositoryDetails details) {
		
		
		Session session = null;
		if (entityManager == null || (session = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException();
		}
		
		//sql="CREATE TABLE IF NOT EXISTS Test (Id	INT(10) PRIMARY KEY,tables_name	VARCHAR (50) NOT NULL,File_name	VARCHAR (150), tables_types	VARCHAR (50) NOT NULL,"
		//		+ "created_by	VARCHAR (20) NOT NULL,creation_date	DATETIME NOT NULL)";

		//Query query = session.createSQLQuery("create  table IF NOT EXISTS sampletable1(col1 varchar(10),col2 int(10))");
		
		//String createTableSQL = createTableSQL;
		
		Query query = session.createSQLQuery(createTableSQL);
		

		try {

				int id =  query.executeUpdate();
				System.out.println("#### No of Rows Affected ::"+id);
				
				RepositoryDetails details2 = null;
				System.out.println("### "+details.getFileName());
				System.out.println("### "+details.getTablesName());
				System.out.println("### "+details.getTableTypes());
				System.out.println("### "+details.getUserId());
				System.out.println("### "+details.getDate());
				System.out.println("### "+details.getId());
				   
				
				  details2 = detailsRepo.save(details);
				  System.out.println("###  details2"+details2);
				  if(details2 != null) {
				  
				  return true;
				  
				  }else {
				  
					  throw new Exception(); 
				  
				  }
				 
			
			
		} catch (Exception e) {
			log.error("exception in createTable():", e);
			//throw new Exception("Exception "+e.getMessage(),e);
		}
		return false;
	}

	private int saveFileInRepository(String fileName) {
		
		int i = 0;
		String sql = "insert into repository_details";
		
		detailsRepo.save(null);
		
		return i;
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

			// It work in MySQL 
			/*
			 * BigInteger result = (BigInteger) query.getSingleResult();
			 * if(result.intValue() > 0) { System.out.println("#### Table is present...");
			 * return true; }
			 */
			
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

	




/*
 * @Override public boolean isTablePresent(String tableName) {
 * 
 * String sql = "select * from information_schema.tables where table_name='" +
 * tableName + "'";
 * 
 * entityManager.createNativeQuery(sql);
 * 
 * return false; }
 */

}
