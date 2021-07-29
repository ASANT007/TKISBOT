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
public class RepositoryDetails {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
	
	@Column(name = "tables_name")
	private String tablesName;
	
	@Column(name = "tables_types")
	private String tableTypes;
	
	@Column(name = "file_name")
	private String fileName;
	
	@Column(name = "created_by")
	private String userId;
	
	@Column(name = "creation_date")
	private Timestamp date;
	
	public RepositoryDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RepositoryDetails(int id, String tablesName, String tableTypes, String fileName, String userId,
			Timestamp date) {
		super();
		this.id = id;
		this.tablesName = tablesName;
		this.tableTypes = tableTypes;
		this.fileName = fileName;
		this.userId = userId;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "RepositoryDetails [id=" + id + ", tablesName=" + tablesName + ", tableTypes=" + tableTypes
				+ ", fileName=" + fileName + ", userId=" + userId + ", date=" + date + "]";
	}
	
	
	
}
