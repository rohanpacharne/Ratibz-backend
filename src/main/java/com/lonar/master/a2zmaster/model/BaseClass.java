package com.lonar.master.a2zmaster.model;

import java.util.Date;

//import javax.persistence.Column;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.Transient;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass
@JsonInclude(Include.NON_NULL)
public class BaseClass {

	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "CREATED_BY")
	private Long createdBy;
	
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
	@Column(name = "LAST_UPDATE_LOGIN")
	private Long lastUpdateLogin;
	
	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;
	
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Transient
	private String stDate;
	
	@Transient
	private String enDate;
	
	@Transient
	private Integer start;

	@Transient
	private Integer length;
	
	@Transient
	private Integer columnNo;
	
	@Transient
	private String sort;
	
	@Transient
	private String search;
	
	public BaseClass() {
	    // Default constructor for JPA and Jackson
	}
	
	@JsonCreator
    public BaseClass(
        @JsonProperty("status") String status,
        @JsonProperty("startDate") Date startDate,
        @JsonProperty("endDate") Date endDate,
        @JsonProperty("createdBy") Long createdBy,
        @JsonProperty("creationDate") Date creationDate,
        @JsonProperty("lastUpdateLogin") Long lastUpdateLogin,
        @JsonProperty("lastUpdatedBy") Long lastUpdatedBy,
        @JsonProperty("lastUpdateDate") Date lastUpdateDate,
        @JsonProperty("stDate") String stDate,
        @JsonProperty("enDate") String enDate,
        @JsonProperty("start") Integer start,
        @JsonProperty("length") Integer length,
        @JsonProperty("columnNo") Integer columnNo,
        @JsonProperty("sort") String sort,
        @JsonProperty("search") String search
    ) {
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
        this.lastUpdateLogin = lastUpdateLogin;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdateDate = lastUpdateDate;
        this.stDate = stDate;
        this.enDate = enDate;
        this.start = start;
        this.length = length;
        this.columnNo = columnNo;
        this.sort = sort;
        this.search = search;
    }

	
	

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
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

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getColumnNo() {
		return columnNo;
	}

	public void setColumnNo(Integer columnNo) {
		this.columnNo = columnNo;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "BaseClass [status=" + status + ", startDate=" + startDate + ", endDate=" + endDate + ", createdBy="
				+ createdBy + ", creationDate=" + creationDate + ", lastUpdateLogin=" + lastUpdateLogin
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdateDate=" + lastUpdateDate + ", stDate=" + stDate
				+ ", enDate=" + enDate + ", start=" + start + ", length=" + length + ", columnNo=" + columnNo
				+ ", sort=" + sort + "]";
	}
	
	
}
