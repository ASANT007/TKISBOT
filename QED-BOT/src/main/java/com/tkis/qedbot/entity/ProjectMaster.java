package com.tkis.qedbot.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project_master")
public class ProjectMaster {


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private int projectId;	
	
	@Column(name = "deliverabletype_id")
    private int deliverableTypeId;
	
	@Column(name = "project_tag")
	private String projectTag;
	
	@Column(name = "project_name")
	private String projectName;
	
	@Column(name = "short_description")
	private String shortDesc;
	
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

	public ProjectMaster(int projectId, int deliverableTypeId, String projectTag, String projectName, String shortDesc,
			String status, String createdBy, Timestamp creationDate, String lastUpdatedBy, Timestamp lastUpdationDate) {
		super();
		this.projectId = projectId;
		this.deliverableTypeId = deliverableTypeId;
		this.projectTag = projectTag;
		this.projectName = projectName;
		this.shortDesc = shortDesc;
		this.status = status;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdationDate = lastUpdationDate;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getDeliverableTypeId() {
		return deliverableTypeId;
	}

	public void setDeliverableTypeId(int deliverableTypeId) {
		this.deliverableTypeId = deliverableTypeId;
	}

	public String getProjectTag() {
		return projectTag;
	}

	public void setProjectTag(String projectTag) {
		this.projectTag = projectTag;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
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
		return "ProjectMaster [projectId=" + projectId + ", deliverableTypeId=" + deliverableTypeId + ", projectTag="
				+ projectTag + ", projectName=" + projectName + ", shortDesc=" + shortDesc + ", status=" + status
				+ ", createdBy=" + createdBy + ", creationDate=" + creationDate + ", lastUpdatedBy=" + lastUpdatedBy
				+ ", lastUpdationDate=" + lastUpdationDate + "]";
	}

	
	
	
	
}
