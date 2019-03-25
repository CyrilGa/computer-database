package fr.cgaiton611.cdb.dto;

import org.hibernate.validator.constraints.NotEmpty;

import fr.cgaiton611.cdb.annotation.DateAndTimeAnnotation;

@DateAndTimeAnnotation
public class ComputerForm {
	private String id;
	@NotEmpty
	private String computerName;
	private String introducedDate;
	private String introducedTime;
	private String discontinuedDate;
	private String discontinuedTime;
	@NotEmpty
	private String companyName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getComputerName() {
		return computerName;
	}
	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}
	public String getIntroducedDate() {
		return introducedDate;
	}
	public void setIntroducedDate(String introducedDate) {
		this.introducedDate = introducedDate;
	}
	public String getIntroducedTime() {
		return introducedTime;
	}
	public void setIntroducedTime(String introducedTime) {
		this.introducedTime = introducedTime;
	}
	public String getDiscontinuedDate() {
		return discontinuedDate;
	}
	public void setDiscontinuedDate(String discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}
	public String getDiscontinuedTime() {
		return discontinuedTime;
	}
	public void setDiscontinuedTime(String discontinuedTime) {
		this.discontinuedTime = discontinuedTime;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
}
