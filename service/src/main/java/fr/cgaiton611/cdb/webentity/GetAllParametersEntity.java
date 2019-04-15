package fr.cgaiton611.cdb.webentity;

import java.util.Arrays;
import java.util.List;

public class GetAllParametersEntity {

	private int numPage = 0;
	private int nbElements = 10;
	private String computerName = "";
	private String companyName = "";
	private String orderAttribute = "id";
	private String orderType = "ASC";
	
	public static final List<String> ORDER_ATTRIBUTES_AUTORISED = Arrays.asList("id", "computerName", "introduced",
			"discontinued", "companyName");;
	private static final List<String> ORDER_ATTRIBUTES_AUTORISED_TRANSLATED = Arrays.asList("cpu.id", "cpu.name",
			"cpu.introduced", "cpu.discontinued", "cpa.name");
	
	public int getNumPage() {
		return numPage;
	}
	public void setNumPage(int numPage) {
		this.numPage = numPage;
	}
	public int getNbElements() {
		return nbElements;
	}
	public void setNbElements(int nbElements) {
		this.nbElements = nbElements;
	}
	public String getComputerName() {
		return computerName;
	}
	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getOrderAttribute() {
		return orderAttribute;
	}
	public void setOrderAttribute(String orderAttribute) {
		this.orderAttribute = orderAttribute;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	public void translateOrderAttribute() {
		orderAttribute = ORDER_ATTRIBUTES_AUTORISED_TRANSLATED.get(ORDER_ATTRIBUTES_AUTORISED.indexOf(orderAttribute));
	}
	
}
