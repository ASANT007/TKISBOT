CREATE DATABASE QED_BOT_DB;

USE QED_BOT_DB;

CREATE TABLE repository_details(
	id int IDENTITY(1,1) PRIMARY KEY,
	tables_name VARCHAR(250) NOT NULL,
	tables_types VARCHAR(50) NOT NULL,
	file_name VARCHAR(200) NOT NULL,
	created_by VARCHAR(150) NOT NULL,
	creation_date DATETIME NOT NULL,
	last_updated_by VARCHAR(150) NULL,
	last_updation_date DATETIME NULL
);

DROP TABLE project_table_mapping;
DROP TABLE consistency_tracking_table;
DROP TABLE master_deliverable_mapping;

DROP TABLE user_project_mapping;
DROP TABLE bot_Iteration_Tracking;
DROP TABLE project_master;


-- Start
CREATE TABLE project_master(
	project_id int IDENTITY(1,1) PRIMARY KEY,
	project_tag VARCHAR(50) NOT NULL,
	project_name VARCHAR(75) NOT NULL,
	short_description VARCHAR(250),
	STATUS VARCHAR(20) NOT NULL,
	created_by VARCHAR(150) NOT NULL,
	creation_date DATETIME NOT NULL,
	last_updated_by VARCHAR(150) NULL,
	last_updation_date DATETIME NULL
);

CREATE TABLE master_deliverable_mapping (
	md_mappingid int IDENTITY(1,1) PRIMARY KEY,
	master_table VARCHAR(50) NOT NULL,
	master_field VARCHAR(50) NOT NULL,
	deliverable_table VARCHAR(50) NOT NULL,
	deliverable_field VARCHAR(50) NOT NULL,
	STATUS VARCHAR(20) NOT NULL,
	created_by VARCHAR(150) NOT NULL,
	creation_date DATETIME NOT NULL,
	last_updated_by VARCHAR (150) NULL,
	last_updation_date DATETIME NULL
);


CREATE TABLE project_table_mapping(

	pt_mappingid int IDENTITY(1,1) PRIMARY KEY,
	project_id INT NOT NULL,
	md_mappingid INT NOT NULL,
	created_by VARCHAR(150) NOT NULL,
	creation_date DATETIME NOT NULL,
	last_updated_by VARCHAR (150) NULL,
	last_updation_date DATETIME NULL,
	CONSTRAINT fk_project_table_mapping_project_id FOREIGN KEY (project_id) REFERENCES project_master (project_id), 
	CONSTRAINT fk_project_table_mapping_mi_mappingid FOREIGN KEY (md_mappingid) REFERENCES master_deliverable_mapping (md_mappingid)

);


CREATE TABLE user_project_mapping (
	up_mappingid int IDENTITY(1,1) PRIMARY KEY,
	userid VARCHAR(20) NOT NULL,
	project_id INT NOT NULL,
	created_by VARCHAR(150) NOT NULL,
	creation_date DATETIME NOT NULL,
	last_updated_by VARCHAR (150) NULL,
	last_updation_date DATETIME NULL,
	CONSTRAINT fk_user_project_mapping_project_id FOREIGN KEY (project_id) REFERENCES project_master (project_id) 
);


CREATE TABLE consistency_tracking_table(
	trackingid int IDENTITY(1,1) PRIMARY KEY,
	md_mappingid INT NOT NULL,
	master_field_value VARCHAR(150) NOT NULL,
	deliverable_field_value VARCHAR(150) NOT NULL,
	consistency_flag VARCHAR(25) NOT NULL,
	flagged_by VARCHAR(20) NOT NULL,
	remarks VARCHAR(150),
	flagged_date DATETIME NOT NULL,
	CONSTRAINT fk_consistency_tracking_table_md_mappingid FOREIGN KEY (md_mappingid) REFERENCES master_deliverable_mapping (md_mappingid)
)


CREATE TABLE bot_Iteration_Tracking(
	id int IDENTITY(1,1) PRIMARY KEY,
	batchid INT NOT NULL,
	tablename VARCHAR(150) NOT NULL,
	project_id INT NOT NULL,
	insertion_Date DATETIME NOT NULL,
	CONSTRAINT fk_bot_Iteration_Tracking_project_id FOREIGN KEY (project_id) REFERENCES project_master (project_id)
)

-- End


CREATE TABLE bot_log_tracking_table (
	
	sr_no int IDENTITY(1,1) PRIMARY KEY,
	file_name VARCHAR(200) NOT NULL,
	project_name VARCHAR(200) NOT NULL,
	TABLE_NAME VARCHAR (250) NOT NULL,
	log_message VARCHAR(50) NOT NULL,
	log_date DATETIME NOT NULL

);        
        
CREATE TABLE deliverabletype_master(
	deliverabletype_id INT PRIMARY KEY AUTO_INCREMENT,
	deliverabletype_shortname VARCHAR(20) NOT NULL,
	deliverabletype_name VARCHAR(150) NOT NULL,
	status VARCHAR(20) NOT NULL,
	created_by VARCHAR(150) NOT NULL,
	creation_date DATETIME NOT NULL,
	last_updated_by VARCHAR(150) NULL,
	last_updation_date DATETIME NULL
);

CREATE TABLE rules_master(
	rule_id INT PRIMARY KEY AUTO_INCREMENT, 
	project_id VARCHAR(20) NOT NULL, 
	rule_desc VARCHAR(150) NOT NULL,
	execution_sequence INT NOT NULL,
	STATUS VARCHAR(20) NOT NULL,
	created_by VARCHAR(150) NOT NULL,
	creation_date DATETIME NOT NULL,
	last_updated_by VARCHAR(150) NULL,
	last_updation_date DATETIME NULL,
	CONSTRAINT fk_rules_master_project_id FOREIGN KEY (project_id) REFERENCES project_master (project_id)
);

-- truncate table `project_master`;`project_table_mapping`

	
INSERT INTO project_master (project_id,project_tag,project_name,short_description,created_by,creation_date,STATUS) VALUES 
(1,'prm_alpha','Project_001','','bharatm',CURRENT_TIMESTAMP,'active');
INSERT INTO project_master (project_id,project_tag,project_name,short_description,created_by,creation_date,STATUS) VALUES 
(2,'prm_beta','Project_002','','bharatm',CURRENT_TIMESTAMP,'active');

