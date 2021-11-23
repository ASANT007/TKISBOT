package com.tkis.qedbot.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bot_Iteration_Tracking")
public class IterationTracking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int Id ;
	
	@Column(name="batchid")
	private int batchId;
	
	@Column(name="tablename")
	private String tableName;
	
	@Column(name="project_id")
	private int projectId;
	
	@Column(name="insertion_Date") 
	private Timestamp insertionDate;

	public IterationTracking() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IterationTracking(int id, int batchId, String tableName, int projectId, Timestamp insertionDate) {
		super();
		Id = id;
		this.batchId = batchId;
		this.tableName = tableName;
		this.projectId = projectId;
		this.insertionDate = insertionDate;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public Timestamp getInsertionDate() {
		return insertionDate;
	}

	public void setInsertionDate(Timestamp insertionDate) {
		this.insertionDate = insertionDate;
	}

	@Override
	public String toString() {
		return "IterationTracking [Id=" + Id + ", batchId=" + batchId + ", tableName=" + tableName + ", projectId="
				+ projectId + ", insertionDate=" + insertionDate + "]";
	}
	
	
}
