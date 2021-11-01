package com.tkis.qedbot.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_project_mapping")
public class UserProjectMapping {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "up_mappingid")
    private int upMappingId;
	
	@Column(name = "userid")
    private String userId;
	
	@Column(name = "project_id")
    private int projectId;	
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "creation_date")
	private Timestamp creationDate;
	
	@Column(name = "last_updated_by")
	private String lastUpdatedBy;	
	
	@Column(name = "last_updation_date")
	private Timestamp lastUpdationDate;

	public UserProjectMapping() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserProjectMapping(int upMappingId, String userId, int projectId, String status, String createdBy,
			Timestamp creationDate, String lastUpdatedBy, Timestamp lastUpdationDate) {
		super();
		this.upMappingId = upMappingId;
		this.userId = userId;
		this.projectId = projectId;
		this.status = status;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdationDate = lastUpdationDate;
	}

	public int getUpMappingId() {
		return upMappingId;
	}

	public void setUpMappingId(int upMappingId) {
		this.upMappingId = upMappingId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		return "UserProjectMapping [upMappingId=" + upMappingId + ", userId=" + userId + ", projectId=" + projectId
				+ ", status=" + status + ", createdBy=" + createdBy + ", creationDate=" + creationDate
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdationDate=" + lastUpdationDate + "]";
	}
	
	

}
