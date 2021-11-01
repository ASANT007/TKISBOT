package com.tkis.qedbot.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "repository_details")
public class RepositoryDetails
{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repository_id")
    private int repositoryId;
	
	@Column(name = "project_id")
	private int projectId;
	
	
	@Column(name = "tables_name")
	private String tablesName;
	
	@Column(name = "tables_types")
	private String tableTypes;
	
	@Column(name = "file_name")
	private String fileName;
	
	@Column(name = "key_field")
	private String keyField;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "creation_date")
	private Timestamp creationDate;
	
	@Column(name = "last_updated_by")
	private String lastUpdatedBy;	
	
	@Column(name = "last_updation_date")
	private Timestamp lastUpdationDate;	
	
	public RepositoryDetails() {
		super();		
	}

	public RepositoryDetails(int repositoryId, int projectId, String tablesName, String tableTypes, String fileName,
			String keyField, String createdBy, Timestamp creationDate, String lastUpdatedBy,
			Timestamp lastUpdationDate) {
		super();
		this.repositoryId = repositoryId;
		this.projectId = projectId;
		this.tablesName = tablesName;
		this.tableTypes = tableTypes;
		this.fileName = fileName;
		this.keyField = keyField;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdationDate = lastUpdationDate;
	}

	public int getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(int repositoryId) {
		this.repositoryId = repositoryId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getTablesName() {
		return tablesName;
	}

	public void setTablesName(String tablesName) {
		this.tablesName = tablesName;
	}

	public String getTableTypes() {
		return tableTypes;
	}

	public void setTableTypes(String tableTypes) {
		this.tableTypes = tableTypes;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getKeyField() {
		return keyField;
	}

	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Timestamp getLastUpdationDate() {
		return lastUpdationDate;
	}

	public void setLastUpdationDate(Timestamp lastUpdationDate) {
		this.lastUpdationDate = lastUpdationDate;
	}

	@Override
	public String toString() {
		return "RepositoryDetails [repositoryId=" + repositoryId + ", projectId=" + projectId + ", tablesName="
				+ tablesName + ", tableTypes=" + tableTypes + ", fileName=" + fileName + ", keyField=" + keyField
				+ ", createdBy=" + createdBy + ", creationDate=" + creationDate + ", lastUpdatedBy=" + lastUpdatedBy
				+ ", lastUpdationDate=" + lastUpdationDate + "]";
	}
	
	
	
	
}
