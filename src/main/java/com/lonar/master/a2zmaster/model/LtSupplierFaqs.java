package com.lonar.master.a2zmaster.model;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
import jakarta.persistence.*;

@Entity
@Table(name="LT_SUPPLIER_FAQS")
public class LtSupplierFaqs extends BaseClass{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "FAQ_ID")
	private Long faqId;
	
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "FAQ")
	private String faq;
	
	@Column(name = "FAQ_ANS")
	private String faqAns;

	public Long getFaqId() {
		return faqId;
	}

	public void setFaqId(Long faqId) {
		this.faqId = faqId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getFaq() {
		return faq;
	}

	public void setFaq(String faq) {
		this.faq = faq;
	}

	public String getFaqAns() {
		return faqAns;
	}

	public void setFaqAns(String faqAns) {
		this.faqAns = faqAns;
	}

	@Override
	public String toString() {
		return "LtSupplierFaqs [faqId=" + faqId + ", supplierId=" + supplierId + ", faq=" + faq + ", faqAns=" + faqAns
				+ "]";
	}

	
}
