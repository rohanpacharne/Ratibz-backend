package com.lonar.master.a2zmaster.model;

import java.util.Date;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="LT_Scheduler")
@JsonInclude(Include.NON_NULL)
public class LtScheduler {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "scheduler_id")
	private Long schedulerId;
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "completed_date")
	private Date completedDate;
	
	@Column(name = "scheduler_name")
	private String schedulerName;

	@Column(name = "exception_msg")
	private String exceptionMsg;

	@Column(name = "description")
	private String description;
	
	
	
	public LtScheduler() {
		super();
	}

	public LtScheduler(Long schedulerId, Date startDate, Date completedDate, String schedulerName,
			String exceptionMsg, String description) {
		super();
		this.schedulerId = schedulerId;
		this.startDate = startDate;
		this.completedDate = completedDate;
		this.schedulerName = schedulerName;
		this.exceptionMsg = exceptionMsg;
		this.description = description;
	}

	public Long getSchedulerId() {
		return schedulerId;
	}

	public void setSchedulerId(Long schedulerId) {
		this.schedulerId = schedulerId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public String getSchedulerName() {
		return schedulerName;
	}

	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
