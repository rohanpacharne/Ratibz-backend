package com.lonar.master.a2zmaster.model;

import java.util.List;

public class LtCity {
	
	private Long cityId;
	private String city;
	private List<LtArea> area;

	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public List<LtArea> getArea() {
		return area;
	}
	public void setArea(List<LtArea> area) {
		this.area = area;
	}
	
}
