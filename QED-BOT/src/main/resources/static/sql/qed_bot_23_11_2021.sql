

CREATE DATABASE qed_bot_db;

USE qed_bot_db;


CREATE TABLE [bot_log_tracking_table] (

	[sr_no] INT IDENTITY(1,1) PRIMARY KEY,
	[file_name] VARCHAR(200) NOT NULL,
	[project_name] VARCHAR(200) NOT NULL,
	[table_name] VARCHAR (250) NOT NULL,
	[log_message] VARCHAR(50) NOT NULL,
	[log_date] DATETIME NOT NULL

);  

CREATE TABLE [deliverabletype_master](
	[deliverabletype_id] INT IDENTITY(1,1) PRIMARY KEY,
	[deliverabletype_shortname] VARCHAR(20) NOT NULL,
	[deliverabletype_name] VARCHAR(150) NOT NULL,
	[status] VARCHAR(20) NOT NULL,
	[created_by] VARCHAR(75) NOT NULL,
	[creation_date] DATETIME NOT NULL,
	[last_updated_by] VARCHAR(75) NULL,
	[last_updation_date] DATETIME NULL
);



CREATE TABLE [project_master](
	[project_id] INT IDENTITY(1,1) PRIMARY KEY,
	[deliverabletype_id] INT NOT NULL,
	[project_tag] VARCHAR(50) NOT NULL unique,
	[project_name] VARCHAR(75) NOT NULL,
	[short_description] VARCHAR(250),
	[status] VARCHAR(20) NOT NULL,
	[created_by] VARCHAR(75) NOT NULL,
	[creation_date] DATETIME NOT NULL,
	[last_updated_by] VARCHAR(75) NULL,
	[last_updation_date] DATETIME NULL,
	[files_path] varchar(750) NULL --Add in UAT NOT NUll 27-10-2021
	CONSTRAINT fk_project_master_deliverabletype_id FOREIGN KEY ([deliverabletype_id]) REFERENCES [deliverabletype_master] ([deliverabletype_id]) on delete cascade on update cascade
);

CREATE TABLE [repository_details](
	[repository_id] INT  IDENTITY(1,1) PRIMARY KEY,
	[project_id] INT NOT NULL,
	[tables_name] VARCHAR(250) NOT NULL,
	[tables_types] VARCHAR(50) NOT NULL,
	[file_name] VARCHAR(200) NOT NULL,
	[key_field] VARCHAR(1000) NULL,--Added in UAT--
	[created_by] VARCHAR(75) NOT NULL,
	[creation_date] DATETIME NOT NULL,
	[last_updated_by] VARCHAR(75) NULL,
	[last_updation_date] DATETIME NULL,
	CONSTRAINT fk_repository_details_project_id FOREIGN KEY ([project_id]) REFERENCES [project_master] ([project_id]) on delete cascade on update cascade
);

CREATE TABLE [rules_master](
	[rule_id] INT IDENTITY(1,1) PRIMARY KEY, 	
	[repository_id] INT NOT NULL,
	[rule_type] VARCHAR(50) NOT NULL,
	[rule_desc] VARCHAR(150) NOT NULL,
	[execution_sequence] INT NOT NULL,
	[status] VARCHAR(20) NOT NULL,
	[created_by] VARCHAR(75) NOT NULL,
	[creation_date] DATETIME NOT NULL,
	[last_updated_by] VARCHAR(75) NULL,
	[last_updation_date] DATETIME NULL
	CONSTRAINT fk_rules_master_repository_id FOREIGN KEY ([repository_id]) REFERENCES [repository_details] ([repository_id])  on delete cascade on update cascade
);


CREATE TABLE [bot_Iteration_Tracking](
	[id] INT IDENTITY(1,1) PRIMARY KEY,
	[batchid] INT NOT NULL,
	[tablename] VARCHAR(150) NOT NULL,
	[project_id] INT NOT NULL,
	[insertion_Date] DATETIME NOT NULL,
	CONSTRAINT fk_bot_Iteration_Tracking_project_id FOREIGN KEY ([project_id]) REFERENCES [project_master] ([project_id]) on delete cascade on update cascade
);


CREATE TABLE [master_deliverable_mapping] (
	[md_mappingid] INT IDENTITY(1,1) PRIMARY KEY,
	[project_id] INT NOT NULL,
	[master_table] VARCHAR(250) NOT NULL,
	[master_field] VARCHAR(150) NOT NULL,
	[deliverable_table] VARCHAR(250) NOT NULL,
	[deliverable_field] VARCHAR(150) NOT NULL,
	[status] VARCHAR(20) NOT NULL,
	[created_by] VARCHAR(75) NOT NULL,
	[creation_date] DATETIME NOT NULL,
	[last_updated_by] VARCHAR(75) NULL,
	[last_updation_date] DATETIME NULL,
	CONSTRAINT fk_project_table_mapping_project_id FOREIGN KEY ([project_id]) REFERENCES [project_master] ([project_id]) on delete cascade on update cascade
);

CREATE TABLE [consistency_tracking_table](
	[trackingid] INT IDENTITY(1,1) PRIMARY KEY,
	[key_field] VARCHAR(1000) NOT NULL,--Added in UAT--
	[md_mappingid] INT NOT NULL,
	[master_field_value] VARCHAR(150) NOT NULL,
	[deliverable_field_value] VARCHAR(150) NOT NULL,
	[consistency_flag] VARCHAR(25) NOT NULL,
	[flagged_by] VARCHAR(20) NOT NULL,
	[remarks] VARCHAR(150),
	[flagged_date] DATETIME NOT NULL,
	flag_count int not null, -- Add in UAT 09-11-2021
	CONSTRAINT fk_consistency_tracking_table_md_mappingid FOREIGN KEY ([md_mappingid]) REFERENCES [master_deliverable_mapping] ([md_mappingid]) on delete cascade on update cascade
);

CREATE TABLE [user_project_mapping] (
	[up_mappingid] INT IDENTITY(1,1) PRIMARY KEY,
	[userid] VARCHAR(20) NOT NULL,
	[project_id] INT NOT NULL,	
	[status] VARCHAR(20) NOT NULL,
	[created_by] VARCHAR(75) NOT NULL,
	[creation_date] DATETIME NOT NULL,
	[last_updated_by] VARCHAR(75) NULL,
	[last_updation_date] DATETIME NULL
	CONSTRAINT fk_user_project_mapping_project_id FOREIGN KEY([project_id]) REFERENCES [project_master] ([project_id]) on delete cascade on update cascade
);

create table [group_master](
	
	[groupid] int IDENTITY(1,1) PRIMARY KEY, 
	[group_name] varchar(25) not null,
	[role] varchar(25) not null,
	[status] VARCHAR(20) NOT NULL,
	[created_by] VARCHAR(75) NOT NULL,
	[creation_date] DATETIME NOT NULL,
	[last_updated_by] VARCHAR(75) NULL,
	[last_updation_date] DATETIME NULL
);


create table [ad_service_master](

	[serviceid] int IDENTITY(1,1) PRIMARY KEY, 
	[userid] varchar(75) not null,
	[password] varchar(75) not null,	
	[ldap_url] varchar(150) not null,
	[status] VARCHAR(20) NOT NULL,
	[created_by] VARCHAR(75) NOT NULL,
	[creation_date] DATETIME NOT NULL,
	[last_updated_by] VARCHAR(75) NULL,
	[last_updation_date] DATETIME NULL

);


create table [admin_login_table](

	[srno] int IDENTITY(1,1) PRIMARY KEY, 
	[userid] varchar(75) not null,
	[password] varchar(75) not null,	
	[status] VARCHAR(20) NOT NULL,
	[created_by] VARCHAR(75) NOT NULL,
	[creation_date] DATETIME NOT NULL,
	[last_updated_by] VARCHAR(75) NULL,
	[last_updation_date] DATETIME NULL

);


create table [inconsistency_logs_table] (

[srno] int identity(1,1) primary key,
[project_id] INT NOT NULL,	
[initialcount] INT NOT NULL,
[resolvedcount] INT NOT NULL,
[date_of_entry]  DATETIME NULL

);

