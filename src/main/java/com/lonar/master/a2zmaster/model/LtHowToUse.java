package com.lonar.master.a2zmaster.model;

import java.util.Date;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "lt_how_to_use")
@JsonInclude(Include.NON_NULL)
public class LtHowToUse {

	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "how_to_use_id")
	private Long howToUseId;
	
	@Column(name="link")
	private String link;
	
	@Column(name="question")
	private String question;
	
	@Column(name="que_sequence")
	private Integer queSequence;
	
	@Column(name="user_type")
	private String userType;
	
	@Column(name="description")
	private String description;
	
	@Column(name="status")
	private String status;
 	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "CREATED_BY")
	private Long createdBy;
	
	@Column(name = "CREATION_DATE")
	private Date creationDate;

	@Column(name = "updated_by")
	private Long updatedBy;
	
	@Column(name = "last_update_date")
	private Date lastUpdateDate;

	@Column(name = "thumbnail")
	private String thumbnail;
	
	public Long getHowToUseId() {
		return howToUseId;
	}

	public void setHowToUseId(Long howToUseId) {
		this.howToUseId = howToUseId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Integer getQueSequence() {
		return queSequence;
	}

	public void setQueSequence(Integer queSequence) {
		this.queSequence = queSequence;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
}
