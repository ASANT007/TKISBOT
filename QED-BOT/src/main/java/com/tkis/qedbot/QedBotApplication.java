package com.tkis.qedbot;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.tkis.qedbot.entity.RepositoryDetails;
import com.tkis.qedbot.repo.RepositoryDetailsCRUD;
import com.tkis.qedbot.repo.RepositoryDetailsRepo;

@SpringBootApplication
public class QedBotApplication {
	
	

	public static void main(String[] args) {
		
		SpringApplication.run(QedBotApplication.class, args);
		
		/*
		 Working Code
		ApplicationContext context = SpringApplication.run(QedBotApplication.class, args);		
		
		RepositoryDetailsCRUD repo = context.getBean(RepositoryDetailsCRUD.class);
		
		List<String> repoDet = repo.getAllTablesName("Project_001");
		
		System.out.println(repoDet);
		*/
		
		/*
		ApplicationContext context = SpringApplication.run(QedBotApplication.class, args);
		CustomTableDao dao = context.getBean(CustomTableDao.class);
		List<String>list = dao.findAllColumns("Project_001_master_test_table");
		System.out.println(list);
		*/
		
		// Update JPQL
		/*
		ApplicationContext context = SpringApplication.run(QedBotApplication.class, args);		
		
		//test_table.xlsx
		 RepositoryDetailsCRUD repo = context.getBean(RepositoryDetailsCRUD.class);
		System.out.println(repo.updateFileName("New_table.xlsx", "Project_001_master_test_table"));
		*/
		
		
		
	}

}
