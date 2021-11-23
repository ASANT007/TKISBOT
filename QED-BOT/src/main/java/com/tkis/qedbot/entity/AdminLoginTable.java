package com.tkis.qedbot.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin_login_table")
public class AdminLoginTable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "srno")
	private int srNo;

	@Column(name = "userid")
	private String userId;

	@Column(name = "password")
	private String password;
	

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

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public AdminLoginTable(int srNo, String userId, String password, String status, String createdBy,
			Timestamp creationDate, String lastUpdatedBy, Timestamp lastUpdationDate) {
		super();
		this.srNo = srNo;
		this.userId = userId;
		this.password = password;
		this.status = status;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdationDate = lastUpdationDate;
	}

	public AdminLoginTable() {
	}

	@Override
	public String toString() {
		return "AdminLoginTable [srNo=" + srNo + ", userId=" + userId + ", password=" + password + ", status=" + status
				+ ", createdBy=" + createdBy + ", creationDate=" + creationDate + ", lastUpdatedBy=" + lastUpdatedBy
				+ ", lastUpdationDate=" + lastUpdationDate + "]";
	}
	
	
	
	
	

}
