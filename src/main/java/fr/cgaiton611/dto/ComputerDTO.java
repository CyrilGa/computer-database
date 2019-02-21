package fr.cgaiton611.dto;


public class ComputerDTO {
	private String id;
	private String name;
	private String introduced;
	private String discontinued;
	private String companyName;
	
	
	public ComputerDTO() {
		super();
	}

	public ComputerDTO(String name, String introduced, String discontinued, String companyName) {
		this.name = name;
		this.introduced = introduced;
		if (introduced != null && ! "".equals(introduced)) this.introduced +=" 00:00";
		this.discontinued = discontinued;
		if (discontinued != null && ! "".equals(discontinued)) this.discontinued +=" 00:00";
		this.companyName = companyName;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntroduced() {
		return introduced;
	}
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}
	public String getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "ComputerDTO [name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", companyName=" + companyName + "]";
	}

	
	
}
