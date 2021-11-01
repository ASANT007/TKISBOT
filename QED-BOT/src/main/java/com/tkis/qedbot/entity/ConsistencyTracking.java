package com.tkis.qedbot.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="consistency_tracking_table")
public class ConsistencyTracking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trackingid")
    private int trackingid;
	
	@Column(name = "key_field")
	private String keyField;	
	
	@Column(name = "md_mappingid")
    private int mdMappingid;
	
	@Column(name = "master_field_value")
	private String masterFieldValue;
	
	@Column(name = "deliverable_field_value")
	private String deliverableFieldValue;	
	
	@Column(name = "consistency_flag")
	private String consistencyFlag;	
	
	@Column(name = "flagged_by")
	private String flaggedBy;
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "flagged_date")
	private Timestamp flaggedDate;

	public ConsistencyTracking() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConsistencyTracking(int trackingid, int mdMappingid,String keyField, String masterFieldValue, String deliverableFieldValue,
			String consistencyFlag, String flaggedBy, String remarks, Timestamp flaggedDate) {
		super();
		this.trackingid = trackingid;
		this.keyField = keyField;
		this.mdMappingid = mdMappingid;
		this.masterFieldValue = masterFieldValue;
		this.deliverableFieldValue = deliverableFieldValue;
		this.consistencyFlag = consistencyFlag;
		this.flaggedBy = flaggedBy;
		this.remarks = remarks;
		this.flaggedDate = flaggedDate;
	}

	public int getTrackingid() {
		return trackingid;
	}

	public void setTrackingid(int trackingid) {
		this.trackingid = trackingid;
	}	
	
	public String getKeyField() {
		return keyField;
	}

	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}

	public int getMdMappingid() {
		return mdMappingid;
	}

	public void setMdMappingid(int mdMappingid) {
		this.mdMappingid = mdMappingid;
	}

	public String getMasterFieldValue() {
		return masterFieldValue;
	}

	public void setMasterFieldValue(String masterFieldValue) {
		this.masterFieldValue = masterFieldValue;
	}

	public String getDeliverableFieldValue() {
		return deliverableFieldValue;
	}

	public void setDeliverableFieldValue(String deliverableFieldValue) {
		this.deliverableFieldValue = deliverableFieldValue;
	}

	public String getConsistencyFlag() {
		return consistencyFlag;
	}

	public void setConsistencyFlag(String consistencyFlag) {
		this.consistencyFlag = consistencyFlag;
	}

	public String getFlaggedBy() {
		return flaggedBy;
	}

	public void setFlaggedBy(String flaggedBy) {
		this.flaggedBy = flaggedBy;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Timestamp getFlaggedDate() {
		return flaggedDate;
	}

	public void setFlaggedDate(Timestamp flaggedDate) {
		this.flaggedDate = flaggedDate;
	}

	@Override
	public String toString() {
		return "ConsistencyTracking [trackingid=" + trackingid + ", keyField=" + keyField + ", mdMappingid="
				+ mdMappingid + ", masterFieldValue=" + masterFieldValue + ", deliverableFieldValue="
				+ deliverableFieldValue + ", consistencyFlag=" + consistencyFlag + ", flaggedBy=" + flaggedBy
				+ ", remarks=" + remarks + ", flaggedDate=" + flaggedDate + "]";
	}
	
	
	
}

