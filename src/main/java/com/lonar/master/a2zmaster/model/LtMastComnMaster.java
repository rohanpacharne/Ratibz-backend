/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lonar.master.a2zmaster.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

//import javax.persistence.Basic;
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToMany;
//import javax.persistence.OrderBy;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.persistence.Transient;
//import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "LT_MAST_COMN_MASTER")
@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class LtMastComnMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Basic(optional = false)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comnMaster_seq")
	//@SequenceGenerator(name = "comnMaster_seq", sequenceName = "LT_MAST_COMN_MASTER_S", allocationSize = 1)
	@Column(name = "MASTER_ID")
	private Long masterId;

	@Basic(optional = false)
	// @NotNull(message="notnull")
	// @Size(min = 1, max = 80)
	@Column(name = "MASTER_NAME")
	private String masterName;

	// @Size(max = 240)
	@JsonView(DataTablesOutput.View.class)
	@Column(name = "MASTER_DESC")
	private String masterDesc;

	// @Size(max = 240)
	@JsonView(DataTablesOutput.View.class)
	@Column(name = "STATUS")
	private String status;

	@Basic(optional = false)
	// @NotNull(message="notnull")
	@Column(name = "START_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Column(name = "END_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	@Basic(optional = false)
	// @NotNull(message="notnull")
	@Column(name = "CREATED_BY")
	private long createdBy;

	@Basic(optional = false)
	// @NotNull(message="notnull")
	@Column(name = "CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@Basic(optional = false)
	// @NotNull(message="notnull")
	@Column(name = "LAST_UPDATE_LOGIN")
	private Long lastUpdateLogin;

	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;

	@Column(name = "LAST_UPDATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateDate;

	@Transient
	private Long draw;

	@Transient
	private Long start;

	@Transient
	private Long length;

	@Transient
	private String stDate;
	@Transient
	private String enDate;
	@Transient
	private int columnNo;

	@Transient
	private String sort;

	public int getColumnNo() {
		return columnNo;
	}

	public void setColumnNo(int columnNo) {
		this.columnNo = columnNo;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Long getDraw() {
		return draw;
	}

	public void setDraw(Long draw) {
		this.draw = draw;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public String getStDate() {
		return stDate;
	}

	public void setStDate(String stDate) {
		this.stDate = stDate;
	}

	public String getEnDate() {
		return enDate;
	}

	public void setEnDate(String enDate) {
		this.enDate = enDate;
	}

	public Long getMasterId() {
		return masterId;
	}

	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public String getMasterDesc() {
		return masterDesc;
	}

	public void setMasterDesc(String masterDesc) {
		this.masterDesc = masterDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Long lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Override
	public String toString() {
		return "LtMastComnMaster [masterId=" + masterId + ", masterName=" + masterName + ", masterDesc=" + masterDesc
				+ ", status=" + status + ", startDate=" + startDate + ", endDate=" + endDate + ", createdBy="
				+ createdBy + ", creationDate=" + creationDate + ", lastUpdateLogin=" + lastUpdateLogin
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdateDate=" + lastUpdateDate + ", draw=" + draw
				+ ", start=" + start + ", length=" + length + ", stDate=" + stDate + ", enDate=" + enDate
				+ ", columnNo=" + columnNo + ", sort=" + sort + ", LtMastComnMasterValues=" + LtMastComnMasterValues
				+ "]";
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "MASTER_ID", referencedColumnName = "MASTER_ID")
	@OrderBy("valueName")
	private List<LtMastComnMasterValues> LtMastComnMasterValues;

	public LtMastComnMaster() {
	}

	public LtMastComnMaster(Long masterId) {
		this.masterId = masterId;
	}

	/*
	 * @PrePersist void preInsert() { PrePersistUtil.pre(this); }
	 */

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (masterId != null ? masterId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof LtMastComnMaster)) {
			return false;
		}
		LtMastComnMaster other = (LtMastComnMaster) object;
		if ((this.masterId == null && other.masterId != null)
				|| (this.masterId != null && !this.masterId.equals(other.masterId))) {
			return false;
		}
		return true;
	}

	public List<LtMastComnMasterValues> getLtMastComnMasterValues() {
		return LtMastComnMasterValues;
	}

	public void setLtMastComnMasterValues(List<LtMastComnMasterValues> ltMastComnMasterValues) {
		LtMastComnMasterValues = ltMastComnMasterValues;
	}

}
