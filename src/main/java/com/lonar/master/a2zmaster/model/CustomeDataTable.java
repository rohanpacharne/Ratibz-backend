package com.lonar.master.a2zmaster.model;

import java.util.List;

public class CustomeDataTable {

	private Integer draw;
	private Long recordsTotal;
	private Long recordsFiltered;
	private Double balanceQty;
	private Object header;
	private List<?> data;
	//private List<LtExpModuleApprovals> moduleApprovalData;
	
	public Integer getDraw() {
		return draw;
	}
	public void setDraw(Integer draw) {
		this.draw = draw;
	}
	public Long getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(Long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public Long getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(Long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	public Double getBalanceQty() {
		return balanceQty;
	}
	public void setBalanceQty(Double balanceQty) {
		this.balanceQty = balanceQty;
	}
	public Object getHeader() {
		return header;
	}
	public void setHeader(Object header) {
		this.header = header;
	}
	
}
