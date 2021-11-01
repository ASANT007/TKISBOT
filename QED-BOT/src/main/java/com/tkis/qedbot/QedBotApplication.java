package com.tkis.qedbot;

import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.tkis.qedbot.entity.UserProjectMapping;
import com.tkis.qedbot.repo.InconsistencyLogsRepo;
import com.tkis.qedbot.repo.UserProjectMappingRepo;

@SpringBootApplication
public class QedBotApplication {

	public static void main(String[] args) {

		SpringApplication.run(QedBotApplication.class, args);

		//ApplicationContext context = SpringApplication.run(QedBotApplication.class, args);
		//START 4
		/*
		UserProjectMappingRepo repo = context.getBean(UserProjectMappingRepo.class);
		Optional<UserProjectMapping> optional = repo.findByUserIdAndProjectId("10672206",1);
		System.out.println("isPresent"+optional.isPresent());
		System.out.println("isEmpty"+optional.isEmpty());
		if(optional.isPresent()) {
			UserProjectMapping upm = optional.get();
			System.out.println("### MappingId"+upm.getUpMappingId());
		}
		*/
		//END 4
		
		// RepositoryDetailsRepo repo = context.getBean(RepositoryDetailsRepo.class);

		// List<RepositoryDetails> repolist = repo.findByProjectId(1);

		// List<Object[]> repolist = repo.findByProjectId(1);

		// repolist.forEach(e->{ System.out.println(e); });

		/*
		 * for(Object[] kfRow : repolist) { System.out.println("Repo_Id "+kfRow[0]
		 * +" "+kfRow[1]); }
		 */

		// 11-10-2021 START
		// Scenario 2
		// ProjectMasterRepo repo = context.getBean(ProjectMasterRepo.class,args);

		// Solution 1
		/*
		 * Optional<ProjectMaster> optional = repo.findById(1); ProjectMaster pm =
		 * optional.get(); System.out.println("#### ProjectName"+pm.getProjectName());
		 */

		// Solution 2
		// System.out.println("#### ProjectName "+repo.findById(1).get().getProjectName());
		// 11-10-2021 END

		// Part 3 START
		
		/*
		 * InconsistencyLogsRepo repo =
		 * context.getBean(InconsistencyLogsRepo.class,args); if(
		 * inconsistencyLogsTableCount(repo) > 0){
		 * 
		 * }else { System.out.println("#### Else"); }
		 */
		 

		// Part 3 END

	}

	
	  private static int inconsistencyLogsTableCount(InconsistencyLogsRepo repo) {
	  
	  
	  int count = 0;
	  
	  try { 
		//count = repo.findByProjectId(1).getInitialCount();  
	  //count = repo.findById(1).get().getInitialCount();
	  //Optional<InconsistencyLogs> optional = repo.findTopByOrderByDesc(1);
	  //repo.findTopByOrderBySrNoDesc(); //count =
	  //repo.findByProjectIdOrderBySrNoDesc(1).getResolvedCount();
	  //repo.findTopByOrderByProjectIdDesc(); //count =
	  //repo.findTopResolvedCountByProjectIdOrderBySrNoDesc(1).getResolvedCount();
	  
	  //count = repo.findResolvedCountByProjectIdOrderBySrNoDesc(1);
	  
	  }catch(Exception e) {
	  
	  } 
	  //Solution 1
	  
	  //Optional<ProjectMaster> optional = repo.findById(1); ProjectMaster pm =
	  //optional.get(); System.out.println("#### ProjectName"+pm.getProjectName());
	  
	  
	  //Solution 2 //System.out.println("#### ProjectName "+repo.findById(1).get().
	  //getProjectName()); //11-10-2021 END 
	  System.out.println(count); return count;
	  }
	 

}
