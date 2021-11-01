package com.tkis.qedbot.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="group_master")
public class GroupMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="groupid")
	private int groupId ;
	
	@Column(name="group_name")
	private String groupName;
	
	@Column(name="role")
	private String role ;
	
	@Column(name="status")		 
	private String status;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="creation_date") 
	private Timestamp creationDate;
	
	@Column(name="last_updated_by")
	private String last_updated_by;
	
	
	@Column(name="last_updation_date")	
	private Timestamp last_updation_date;


	public int getGroupId() {
		return groupId;
	}


	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}


	public String getGroupName() {
		return groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}



	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
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


	public String getLast_updated_by() {
		return last_updated_by;
	}


	public void setLast_updated_by(String last_updated_by) {
		this.last_updated_by = last_updated_by;
	}


	public Timestamp getLast_updation_date() {
		return last_updation_date;
	}


	public void setLast_updation_date(Timestamp last_updation_date) {
		this.last_updation_date = last_updation_date;
	}


	public GroupMaster(int groupId, String groupName, String role, String status, String createdBy,
			Timestamp creationDate, String last_updated_by, Timestamp last_updation_date) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
		this.role = role;
		this.status = status;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.last_updated_by = last_updated_by;
		this.last_updation_date = last_updation_date;
	}


	public GroupMaster() {
		super();
	}


	@Override
	public String toString() {
		return "GroupMaster [groupId=" + groupId + ", groupName=" + groupName + ", role=" + role + ", status=" + status
				+ ", createdBy=" + createdBy + ", creationDate=" + creationDate + ", last_updated_by=" + last_updated_by
				+ ", last_updation_date=" + last_updation_date + "]";
	}


	
	
	
}
