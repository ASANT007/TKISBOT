insert into repository_details (id,tables_name,tables_types,file_name,created_by,creation_date) values 
(1,'master_SPPID_Pipeline','master','SPPID Pipeline List.xls','bharatm',CURRENT_TIMESTAMP);
insert into repository_details (id,tables_name,tables_types,file_name,created_by,creation_date) values 
(2,'deliverable_3D_Pipelines','deliverable','3D_Pipelines List.xls','bharatm',CURRENT_TIMESTAMP);

--insert into repository_details(tables_name,tables_types,file_name,created_by,creation_date) values ('Test','master','test.xls','ASANT',CURRENT_TIMESTAMP);


insert into project_master (project_id,project_tag,project_name,short_description,created_by,creation_date,status) values 
(1,'prm_alpha','Project_001','','bharatm',CURRENT_TIMESTAMP,'active');
insert into project_master (project_id,project_tag,project_name,short_description,created_by,creation_date,status) values 
(2,'prm_beta','Project_002','','bharatm',CURRENT_TIMESTAMP,'active');

insert into master_deliverable_mapping(md_mappingid,master_table,master_field,deliverable_table,deliverable_field,created_by,creation_date,status) values
 (2001,'master_SPPID_Pipeline','Process_Area','deliverable_3D_Pipelines','Process_Area','bharatm',CURRENT_TIMESTAMP,'active');
 insert into master_deliverable_mapping(md_mappingid,master_table,master_field,deliverable_table,deliverable_field,created_by,creation_date,status) values
 (2002,'master_SPPID_Pipeline','Sub_Process','deliverable_3D_Pipelines','Sub_Process','bharatm',CURRENT_TIMESTAMP,'active');
 insert into master_deliverable_mapping(md_mappingid,master_table,master_field,deliverable_table,deliverable_field,created_by,creation_date,status) values
 (2003,'master_SPPID_Pipeline','Fluid_code','deliverable_3D_Pipelines','Fluid_code','bharatm',CURRENT_TIMESTAMP,'active');
 insert into master_deliverable_mapping(md_mappingid,master_table,master_field,deliverable_table,deliverable_field,created_by,creation_date,status) values
 (2004,'master_SPPID_Pipeline','Piping class','deliverable_3D_Pipelines','Pipe class','bharatm',CURRENT_TIMESTAMP,'active');
 insert into master_deliverable_mapping(md_mappingid,master_table,master_field,deliverable_table,deliverable_field,created_by,creation_date,status) values
 (2005,'master_SPPID_Pipeline','tracing type','deliverable_3D_Pipelines','tracing type','bharatm',CURRENT_TIMESTAMP,'active');
 insert into master_deliverable_mapping(md_mappingid,master_table,master_field,deliverable_table,deliverable_field,created_by,creation_date,status) values
 (2006,'master_SPPID_Nozzle','Equipment No','deliverable_3D_Nozzle','Equipment Name','bharatm',CURRENT_TIMESTAMP,'active');
 insert into master_deliverable_mapping(md_mappingid,master_table,master_field,deliverable_table,deliverable_field,created_by,creation_date,status) values
 (2007,'master_SPPID_Nozzle','Nozzle Item Tag','deliverable_3D_Nozzle','Nozzle Name','bharatm',CURRENT_TIMESTAMP,'active');
 insert into master_deliverable_mapping(md_mappingid,master_table,master_field,deliverable_table,deliverable_field,created_by,creation_date,status) values
 (2008,'master_SPPID_Nozzle','Nozzle size','deliverable_3D_Nozzle','Nozzle size','bharatm',CURRENT_TIMESTAMP,'active');


 insert into project_table_mapping (pt_mappingid,project_id,md_mappingid) values (1001,1,2001);
 insert into project_table_mapping (pt_mappingid,project_id,md_mappingid) values (1002,1,2002);
 insert into project_table_mapping (pt_mappingid,project_id,md_mappingid) values (1003,1,2003);
 insert into project_table_mapping (pt_mappingid,project_id,md_mappingid) values (1004,1,2004);
 insert into project_table_mapping (pt_mappingid,project_id,md_mappingid) values (1005,1,2005);
 insert into project_table_mapping (pt_mappingid,project_id,md_mappingid) values (1006,2,2006);
 insert into project_table_mapping (pt_mappingid,project_id,md_mappingid) values (1007,2,2007);
 insert into project_table_mapping (pt_mappingid,project_id,md_mappingid) values (1008,2,2008);


 insert into user_project_mapping (up_mappingid,userid,project_id) values (1,'dhananjayj',1);
 insert into user_project_mapping (up_mappingid,userid,project_id) values (2,'niravk',2);
 insert into user_project_mapping (up_mappingid,userid,project_id) values (3,'sameers',1);

