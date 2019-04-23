package fr.cgaiton611.cdb.dto;

public class ComputerDTO {
	private String id;
	private String name;
	private String introduced;
	private String discontinued;
	private CompanyDTO company;

	public ComputerDTO() {
	}

	public ComputerDTO(String name, String introduced, String discontinued, CompanyDTO company) {
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	public ComputerDTO(String id, String name, String introduced, String discontinued, CompanyDTO company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	public ComputerDTO(ComputerForm computerForm) {
		id = computerForm.getId();
		name = computerForm.getComputerName();
		introduced = computerForm.getIntroducedDate() + " " + computerForm.getIntroducedTime();
		discontinued = computerForm.getDiscontinuedDate() + " " + computerForm.getDiscontinuedTime();
		if ("select-option-default".equals(computerForm.getCompanyName())) {
			company.setName(null);
		} else {
			company.setName(computerForm.getCompanyName());
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

	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "ComputerDTO [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", company=" + company + "]";
	}
	

	
}
