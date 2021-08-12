-- create database qed_bot_db;
use qed_bot_db;


create table bot_log_tracking_table (

[sr_no] int IDENTITY(1,1) primary key,
[file_name] varchar(200) not null,
[project_name] varchar(200) not null,
[table_name] varchar (250) not null,
[log_message] varchar(5000) not null,
[log_date] datetime not null

);



create table repository_details(
	[id] [int] IDENTITY(1,1) primary key,
	[tables_name] [varchar](250) NOT NULL,
	[tables_types] [varchar](50) NOT NULL,
	[file_name] [varchar](200) NOT NULL,
	[created_by] [varchar](150) NOT NULL,
	[creation_date] [datetime] NOT NULL,
);

create table project_master(
project_id int primary key,
project_tag varchar(50) not null,
project_name varchar(75) not null,
short_description varchar(250),
created_by varchar(20) not null,
creation_date datetime not null,
status varchar(20) not null,
last_updated_by varchar(20) null,
last_updation_date datetime null
);

create table master_deliverable_mapping (
md_mappingid int primary key,
master_table varchar(50) not null,
master_field varchar(50) not null,
deliverable_table varchar(50) not null,
deliverable_field varchar(50) not null,
created_by varchar(20) not null,
creation_date datetime not null,
status varchar(20) not null,
last_updated_by varchar (20) null,
last_updation_date datetime null
);


create table project_table_mapping(
pt_mappingid int primary key,
project_id int not null,
md_mappingid int not null,
constraint fk_project_table_mapping_project_id foreign key (project_id) references project_master (project_id), 
constraint fk_project_table_mapping_mi_mappingid foreign key (md_mappingid) references master_deliverable_mapping (md_mappingid)
);


create table user_project_mapping (
up_mappingid int primary key,
userid varchar(20) not null,
project_id int not null,
constraint fk_user_project_mapping_project_id foreign key (project_id) references project_master (project_id) on delete cascade on update cascade
);

-- 1 (2)--
CREATE TABLE [dbo].[consistency_tracking_table](
	[trackingid] [int] primary key,
	[md_mappingid] [int] NOT NULL,
	[master_field_value] [varchar](150) NOT NULL,
	[deliverable_field_value] [varchar](150) NOT NULL,
	[consistency_flag] [varchar](25) NOT NULL,
	[flagged_by] [varchar](20) NOT NULL,
	[remarks] [varchar](150),
	[flagged_date] [datetime] NOT NULL,
	constraint fk_consistency_tracking_table_md_mappingid foreign key (md_mappingid) references master_deliverable_mapping (md_mappingid) on delete cascade on update cascade
)

CREATE TABLE [dbo].[bot_Iteration_Tracking](
	[id] [int] primary key,
	[Batchid] [int] not NULL,
	[Tablename] [varchar](150) not NULL,
	[Project_id] [int] not null,
	[Insertion_Date] [datetime] not NULL,
	constraint fk_bot_Iteration_Tracking_project_id foreign key (project_id) references project_master (project_id)
	)
-- 1 (2)--

CREATE TABLE [master_SPPID_Pipeline](
	[Industrial_complex] [nvarchar](50) NOT NULL,
	[Pro_cess_Area] [nvarchar](50) NOT NULL,
	[Sub_process] [nvarchar](50) NOT NULL,
	[Technical_Item] [nvarchar](50) NOT NULL,
	[Alternate_Label_LV1] [nvarchar](1) NULL,
	[Alternate_Label_LV2] [nvarchar](100) NULL,
	[From] [nvarchar](300) NULL,
	[To] [nvarchar](300) NULL,
	[Fluid_Code_Shorttext] [nvarchar](50) NOT NULL,
	[Nominal_Pressure] [nvarchar](50) NOT NULL,
	[Piping_Classifi_cation] [nvarchar](50) NOT NULL,
	[Gasket_Shorttext] [nvarchar](50) NULL,
	[Valve_Class] [nvarchar](50) NULL,
	[Alt_Piping_Classifi_cation] [nvarchar](1) NULL,
	[Insulation_Purpose_Shorttext] [nvarchar](50) NULL,
	[Insulation_Thickness] [nvarchar](50) NULL,
	[DN_Shorttext] [varchar](50) NULL,
	[DN_List_Shorttext] [nvarchar](50) NULL,
	[Construction_Status] [nvarchar](50) NOT NULL,
	[Slope] [nvarchar](50) NULL,
	[Heat_Tracing_Requirement_Shorttext] [nvarchar](1) NULL,
	[Heat_Tracing_Medium_Shorttext] [nvarchar](50) NULL,
	[Heat_Tracing_Temperature] [nvarchar](50) NULL,
	[Operating_Maximum_Pressure] [nvarchar](1) NULL,
	[Operating_Maximum_Temperature] [nvarchar](1) NULL,
	[PMC_Source_Flag] [varchar](50) NOT NULL,
	[All_Drawings_for_the_Line] [nvarchar](150) NOT NULL,
	[Report_Date] [varchar](50) NOT NULL
)


CREATE TABLE [deliverable_3D_Pipelines](	
	[Plant] [nvarchar](50) NULL,
	[Process_Area] [nvarchar](50) NOT NULL,
	[Sub_Process] [nvarchar](50) NOT NULL,
	[PID_No] [nvarchar](50) NULL,
	[Pipeline_Name] [nvarchar](50) NOT NULL,
	[Pipeline_Max_Size] [nvarchar](50) NOT NULL,
	[Pipeline_Size_Unit] [nvarchar](50) NULL,
	[Fluid_Code] [nvarchar](100) NOT NULL,
	[Pipe_Class] [nvarchar](50) NULL,
	[Insulation_Type] [nvarchar](50) NULL,
	[Insulation_Thk] [nvarchar](50) NULL,
	[Tracing_Type] [nvarchar](50) NULL,
	[Pipe_Slope] [nvarchar](1) NULL,
	[Slope_Value] [nvarchar](50) NULL,
	[Line_Designation] [nvarchar](50) NULL,
	[Splash_Guard_Requirement] [nvarchar](50) NULL,
	[Grounding_Requirement] [nvarchar](50) NULL,
	[Construction_Unit] [nvarchar](50) NULL,
	[PipeRunSlope] [nvarchar](50) NOT NULL
);

-- 2 not created (4)--
CREATE TABLE [deliverable_S3D_Valvelist](
	[Process_Unit] [nvarchar](50) NOT NULL,
	[Construction_Unit] [nvarchar](50) NOT NULL,
	[PipeLine_Name] [nvarchar](50) NOT NULL,
	[Piping_Spec] [nvarchar](50) NOT NULL,
	[Valve_Tag] [nvarchar](50) NOT NULL,
	[Identification_Letter] [nvarchar](50) NULL,
	[Sequence_Number] [nvarchar](50) NULL,
	[Name_Rule] [nvarchar](50) NULL,
	[Valve_Size] [varchar](50) NOT NULL,
	[Valve_Type] [nvarchar](50) NULL,
	[Valve_Code] [nvarchar](50) NOT NULL,
	[Short_Material_Description] [nvarchar](200) NULL,
	[PID_Number] [nvarchar](50) NOT NULL,
	[RLT_Line_Number] [nvarchar](50) NOT NULL,
	[Valve_Rating] [nvarchar](50) NULL,
	[End_Preparation1] [nvarchar](50) NULL,
	[End_Preparation2] [nvarchar](50) NULL,
	[Reporting_Requirement] [nvarchar](50) NOT NULL
);

CREATE TABLE [deliverable_TKIS_Instrument](	
	[Sr_No] [nvarchar](1) NULL,
	[Const_Unit] [nvarchar](50) NULL,
	[Process_Unit] [nvarchar](50) NULL,
	[Pipeline] [nvarchar](50) NOT NULL,
	[Instrument_Tag] [nvarchar](100) NOT NULL,
	[S3D_LTA_Instrument_TAG] [nvarchar](50) NOT NULL,
	[Identification_Letter] [nvarchar](50) NULL,
	[Sequence] [nvarchar](50) NULL,
	[Function] [nvarchar](50) NULL,
	[NameRule] [nvarchar](50) NULL,
	[NPD] [varchar](50) NOT NULL,
	[Specification] [nvarchar](50) NOT NULL,
	[EndPreparation] [nvarchar](50) NOT NULL,
	[CATREF] [nvarchar](50) NULL,
	[Instrument_PART_Number] [nvarchar](50) NOT NULL,
	[Face_to_Face_Dimension] [varchar](50) NULL,
	[Face_to_Center] [nvarchar](50) NULL,
	[Face_to_Centre1] [nvarchar](1) NULL,
	[Face_to_Centre2] [nvarchar](1) NULL,
	[Short_Material_Description] [nvarchar](50) NULL,
	[TAG] [nvarchar](50) NULL,
	[PID_No] [nvarchar](50) NULL,
	[Instrument_Location] [nvarchar](100) NOT NULL,
	[Rating] [nvarchar](50) NULL
);

CREATE TABLE [master_TKIS_Piping_Component](
	[SPID] [nvarchar](50) NOT NULL,
	[Sub_Class] [nvarchar](50) NOT NULL,
	[Piping_Component] [nvarchar](100) NULL,
	[Size] [nvarchar](50) NULL,
	[ID_Letter] [nvarchar](100) NULL,
	[Sequence_No] [nvarchar](50) NULL,
	[Item_Tag] [nvarchar](50) NULL,
	[Process_Area] [nvarchar](50) NOT NULL,
	[Sub_Process] [nvarchar](50) NOT NULL,
	[Short_Item_Tag] [nvarchar](50) NULL,
	[Tag_Seq_No] [nvarchar](50) NULL,
	[Long_Code] [nvarchar](50) NULL,
	[Sp_Part_No] [nvarchar](50) NULL,
	[Din_Valve_Class] [varchar](50) NULL,
	[Supply_By] [nvarchar](50) NULL,
	[Pipe_Item_Tag] [nvarchar](50) NULL,
	[Pipe_Seq_No] [nvarchar](50) NULL,
	[Pipe_Fluid] [nvarchar](100) NULL,
	[Pipe_Class] [nvarchar](50) NULL,
	[Drawing_Name] [nvarchar](50) NOT NULL,
	[Drawing_Number] [nvarchar](50) NOT NULL
);


CREATE TABLE [deliverable_3D_Piping_Speciality](
	[Process_Unit] [nvarchar](50) NOT NULL,
	[Construction_Unit] [nvarchar](50) NOT NULL,
	[PipeLine_Name] [nvarchar](50) NOT NULL,
	[Piping_Spec] [nvarchar](50) NOT NULL,
	[Specialty_TAG] [nvarchar](50) NULL,
	[Size] [varchar](50) NOT NULL,
	[Component_Type] [nvarchar](50) NULL,
	[Isometric_TAG] [nvarchar](50) NOT NULL,
	[Short_Material_Description] [nvarchar](100) NULL,
	[PID_Number] [nvarchar](50) NOT NULL,
	[Specialty_Name_in_S3D] [nvarchar](50) NOT NULL,
	[RLT_PipeLine_Number] [nvarchar](50) NOT NULL,
	[Reporting_requirement] [nvarchar](50) NOT NULL,
	[Pressure_Rating] [nvarchar](50) NULL,
	[End_Preparation1] [nvarchar](50) NULL,
	[End_Preparation2] [nvarchar](50) NULL
);


-- 3 (2) --

CREATE TABLE [deliverable_Pipe_Status_CU](	
	[PLANT] [nvarchar](50) NULL,
	[AREA] [nvarchar](50) NOT NULL,
	[UNIT] [nvarchar](50) NOT NULL,
	[Number] [nvarchar](50) NOT NULL,
	[LINE_ID] [nvarchar](50) NOT NULL,
	[LineName_Without_split] [nvarchar](50) NULL,
	[Line_Split_Character] [nvarchar](50) NULL,
	[_Construction__Unit_] [nvarchar](50) NULL,
	[P1] [varchar](50) NULL,
	[P2] [varchar](50) NULL,
	[P3] [varchar](50) NULL,
	[P4] [varchar](50) NULL,
	[P5] [varchar](50) NULL,
	[P6] [varchar](50) NULL,
	[P7] [varchar](50) NULL,
	[P8] [varchar](50) NULL,
	[Status] [varchar](50) NOT NULL
	);


	CREATE TABLE [dbo].[deliverable_Pipe_Run](
	[Oid] [nvarchar](50) NOT NULL,
	[Pipeline] [nvarchar](50) NOT NULL,
	[LineName_Without_split] [nvarchar](50) NOT NULL,
	[Line_Split_Character] [nvarchar](1) NULL,
	[PipeRun] [nvarchar](50) NOT NULL,
	[NPD] [varchar](50) NOT NULL,
	[Piping_Spec_] [nvarchar](50) NOT NULL,
	[Fluid_Code] [varchar](50) NOT NULL,
	[Insul_Spec] [nvarchar](1) NULL,
	[Insul_Matrl] [nvarchar](50) NULL,
	[Insul_Temp] [nvarchar](1) NULL,
	[Insul_Purp] [nvarchar](50) NULL,
	[Paint_Spec] [nvarchar](1) NULL,
	[Op__Temp] [nvarchar](50) NOT NULL,
	[Op__Pressure] [nvarchar](50) NOT NULL,
	[Slope] [nvarchar](1) NULL,
	[Slope_Direction] [nvarchar](50) NOT NULL
	
);


-- Added on 10-08-2021  START --
	
	alter table repository_details add last_updated_by VARCHAR(20);
	alter table repository_details add last_updation_date DATETIME;
	
	alter table project_table_mapping add created_by VARCHAR(150) not null;
	alter table project_table_mapping add creation_date DATETIME not null;
	alter table project_table_mapping add last_updated_by VARCHAR(20);
	alter table project_table_mapping addlast_updation_date DATETIME;
	
	alter table user_project_mapping add created_by VARCHAR(150) not null;
	alter table user_project_mapping add creation_date DATETIME not null;
	alter table user_project_mapping add last_updated_by VARCHAR(20);
	alter table user_project_mapping add last_updation_date DATETIME;
	
-- Added on 10-08-2021  END --
