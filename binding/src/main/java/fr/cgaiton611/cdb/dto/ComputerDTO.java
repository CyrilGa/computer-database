package fr.cgaiton611.cdb.dto;

public class ComputerDTO {
	private String id;
	private String name;
	private String introduced;
	private String discontinued;
	private String companyName;

	public ComputerDTO() {
	}

	public ComputerDTO(String name, String introduced, String discontinued, String companyName) {
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyName = companyName;
	}

	public ComputerDTO(String id, String name, String introduced, String discontinued, String companyName) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyName = companyName;
	}

	public ComputerDTO(ComputerForm computerForm) {
		id = computerForm.getId();
		name = computerForm.getComputerName();
		introduced = computerForm.getIntroducedDate() + " " + computerForm.getIntroducedTime();
		discontinued = computerForm.getDiscontinuedDate() + " " + computerForm.getDiscontinuedTime();
		if ("select-option-default".equals(computerForm.getCompanyName())) {
			companyName = null;
		} else {
			companyName = computerForm.getCompanyName();
		}
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
