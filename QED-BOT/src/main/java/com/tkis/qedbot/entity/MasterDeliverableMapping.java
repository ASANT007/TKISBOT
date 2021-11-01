package com.tkis.qedbot.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="master_deliverable_mapping")
public class MasterDeliverableMapping {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="md_mappingid")
	private int mdMappingId;
	
	@Column(name="project_id")
	private int projectId;
	
	@Column(name="master_table")
	private String masterTable;
	
	@Column(name="master_field")
	private String masterField;
	
	@Column(name="deliverable_table")
	private String deliverableTable;
	
	@Column(name="deliverable_field")
	private String deliverableField;
	
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

	public MasterDeliverableMapping() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MasterDeliverableMapping(int mdMappingId, int projectId, String masterTable, String masterField,
			String deliverableTable, String deliverableField, String status, String createdBy, Timestamp creationDate,
			String lastUpdatedBy, Timestamp lastUpdationDate) {
		super();
		this.mdMappingId = mdMappingId;
		this.projectId = projectId;
		this.masterTable = masterTable;
		this.masterField = masterField;
		this.deliverableTable = deliverableTable;
		this.deliverableField = deliverableField;
		this.status = status;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdationDate = lastUpdationDate;
	}

	public int getMdMappingId() {
		return mdMappingId;
	}

	public void setMdMappingId(int mdMappingId) {
		this.mdMappingId = mdMappingId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getMasterTable() {
		return masterTable;
	}

	public void setMasterTable(String masterTable) {
		this.masterTable = masterTable;
	}

	public String getMasterField() {
		return masterField;
	}

	public void setMasterField(String masterField) {
		this.masterField = masterField;
	}

	public String getDeliverableTable() {
		return deliverableTable;
	}

	public void setDeliverableTable(String deliverableTable) {
		this.deliverableTable = deliverableTable;
	}

	public String getDeliverableField() {
		return deliverableField;
	}

	public void setDeliverableField(String deliverableField) {
		this.deliverableField = deliverableField;
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
		return "MasterDeliverableMapping [mdMappingId=" + mdMappingId + ", projectId=" + projectId + ", masterTable="
				+ masterTable + ", masterField=" + masterField + ", deliverableTable=" + deliverableTable
				+ ", deliverableField=" + deliverableField + ", status=" + status + ", createdBy=" + createdBy
				+ ", creationDate=" + creationDate + ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdationDate="
				+ lastUpdationDate + "]";
	}

		

}
