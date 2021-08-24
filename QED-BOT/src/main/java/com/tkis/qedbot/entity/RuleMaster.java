package com.tkis.qedbot.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rules_master")
public class RuleMaster 
{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id")
    private int ruleId;
	
	@Column(name = "project_id")
    private int projectId;
	
	@Column(name = "repository_id")
    private int repositoryId;
	
	@Column(name = "rule_type")
	private String ruleType;
	
	@Column(name = "rule_desc")
	private String ruleDesc;
	
	@Column(name = "execution_sequence")
    private int executionSequence;
	
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

	
	public RuleMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RuleMaster(int ruleId, int projectId, int repositoryId, String ruleType, String ruleDesc,
			int executionSequence, String status, String createdBy, Timestamp creationDate, String lastUpdatedBy,
			Timestamp lastUpdationDate) {
		super();
		this.ruleId = ruleId;
		this.projectId = projectId;
		this.repositoryId = repositoryId;
		this.ruleType = ruleType;
		this.ruleDesc = ruleDesc;
		this.executionSequence = executionSequence;
		this.status = status;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdationDate = lastUpdationDate;
	}

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(int repositoryId) {
		this.repositoryId = repositoryId;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public int getExecutionSequence() {
		return executionSequence;
	}

	public void setExecutionSequence(int executionSequence) {
		this.executionSequence = executionSequence;
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
		return "RuleMaster [ruleId=" + ruleId + ", projectId=" + projectId + ", repositoryId=" + repositoryId
				+ ", ruleType=" + ruleType + ", ruleDesc=" + ruleDesc + ", executionSequence=" + executionSequence
				+ ", status=" + status + ", createdBy=" + createdBy + ", creationDate=" + creationDate
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdationDate=" + lastUpdationDate + "]";
	}

	
	
	
	
}
