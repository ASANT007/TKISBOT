package com.tkis.qedbot.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "deliverabletype_master")
public class DeliverabletypeMaster 
{
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliverabletype_id")
    private int deliverableTypeId;
	
	@Column(name = "deliverabletype_shortname")
    private String deliverableTypeShortname;
	
	@Column(name = "deliverabletype_name")
	private String deliverableTypeName;	
	
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
	
	

	public DeliverabletypeMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeliverabletypeMaster(int deliverableTypeId, String deliverableTypeShortname, String deliverableTypeName,
			String status, String createdBy, Timestamp creationDate, String lastUpdatedBy, Timestamp lastUpdationDate) {
		super();
		this.deliverableTypeId = deliverableTypeId;
		this.deliverableTypeShortname = deliverableTypeShortname;
		this.deliverableTypeName = deliverableTypeName;
		this.status = status;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdationDate = lastUpdationDate;
	}

	public int getDeliverableTypeId() {
		return deliverableTypeId;
	}

	public void setDeliverableTypeId(int deliverableTypeId) {
		this.deliverableTypeId = deliverableTypeId;
	}

	public String getDeliverableTypeShortname() {
		return deliverableTypeShortname;
	}

	public void setDeliverableTypeShortname(String deliverableTypeShortname) {
		this.deliverableTypeShortname = deliverableTypeShortname;
	}

	public String getDeliverableTypeName() {
		return deliverableTypeName;
	}

	public void setDeliverableTypeName(String deliverableTypeName) {
		this.deliverableTypeName = deliverableTypeName;
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
		return "DeliverabletypeMaster [deliverableTypeId=" + deliverableTypeId + ", deliverableTypeShortname="
				+ deliverableTypeShortname + ", deliverableTypeName=" + deliverableTypeName + ", status=" + status
				+ ", createdBy=" + createdBy + ", creationDate=" + creationDate + ", lastUpdatedBy=" + lastUpdatedBy
				+ ", lastUpdationDate=" + lastUpdationDate + "]";
	}
	
	

}
