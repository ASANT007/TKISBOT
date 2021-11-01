package com.tkis.qedbot.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ad_service_master")
public class ADServiceMaster {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serviceid")
    private int serviceId;
	
	@Column(name = "userid")
	private String userId;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "ldap_url")
	private String ldapUrl;
	
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

	public ADServiceMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ADServiceMaster(int serviceId, String userId, String password, String ldapUrl,
			String status, String createdBy, Timestamp creationDate, String lastUpdatedBy, Timestamp lastUpdationDate) {
		super();
		this.serviceId = serviceId;
		this.userId = userId;
		this.password = password;		
		this.ldapUrl = ldapUrl;
		this.status = status;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdationDate = lastUpdationDate;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
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

	public String getLdapUrl() {
		return ldapUrl;
	}

	public void setLdapUrl(String ldapUrl) {
		this.ldapUrl = ldapUrl;
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
		return "ADServiceMaster [serviceId=" + serviceId + ", userId=" + userId + ", password=" + password
				+ ", ldapUrl=" + ldapUrl + ", status=" + status
				+ ", createdBy=" + createdBy + ", creationDate=" + creationDate + ", lastUpdatedBy=" + lastUpdatedBy
				+ ", lastUpdationDate=" + lastUpdationDate + "]";
	}

	

	
}
