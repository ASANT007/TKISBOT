package com.tkis.qedbot.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.micrometer.core.annotation.Counted;

@Entity
@Table(name="inconsistency_logs_table")
public class InconsistencyLogs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srno")
    private int srNo;
	
	@Column(name = "project_id")
    private int projectId;		
	
	@Column(name = "initialcount")
    private int initialCount;	
	
	@Column(name = "resolvedcount")
    private int resolvedCount;	
	
	@Column(name = "date_of_entry")
	private Timestamp dateOfEntry;
	
	@Column(name="batchid")
	private int batchId;

	public InconsistencyLogs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InconsistencyLogs(int srNo, int projectId, int initialCount, int resolvedCount, Timestamp dateOfEntry, int batchId) {
		super();
		this.srNo = srNo;
		this.projectId = projectId;
		this.initialCount = initialCount;
		this.resolvedCount = resolvedCount;
		this.dateOfEntry = dateOfEntry;
		this.batchId = batchId;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getInitialCount() {
		return initialCount;
	}

	public void setInitialCount(int initialCount) {
		this.initialCount = initialCount;
	}

	public int getResolvedCount() {
		return resolvedCount;
	}

	public void setResolvedCount(int resolvedCount) {
		this.resolvedCount = resolvedCount;
	}

	public Timestamp getDateOfEntry() {
		return dateOfEntry;
	}

	public void setDateOfEntry(Timestamp dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}
	
	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	@Override
	public String toString() {
		return "InconsistencyLogs [srNo=" + srNo + ", projectId=" + projectId + ", initialCount=" + initialCount
				+ ", resolvedCount=" + resolvedCount + ", dateOfEntry=" + dateOfEntry + ", batchId=" + batchId +"]";
	}
	
	
}
