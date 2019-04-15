package fr.cgaiton611.cdb.dto;

public class ComputerDTO {
	private String id;
	private String name;
	private String introduced;
	private String discontinued;
	private CompanyDTO companyDTO;

	public ComputerDTO() {
	}

	public ComputerDTO(String name, String introduced, String discontinued, CompanyDTO companyDTO) {
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyDTO = companyDTO;
	}

	public ComputerDTO(String id, String name, String introduced, String discontinued, CompanyDTO companyDTO) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyDTO = companyDTO;
	}

	public ComputerDTO(ComputerForm computerForm) {
		id = computerForm.getId();
		name = computerForm.getComputerName();
		introduced = computerForm.getIntroducedDate() + " " + computerForm.getIntroducedTime();
		discontinued = computerForm.getDiscontinuedDate() + " " + computerForm.getDiscontinuedTime();
		if ("select-option-default".equals(computerForm.getCompanyName())) {
			companyDTO.setName(null);
		} else {
			companyDTO.setName(computerForm.getCompanyName());
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

	public CompanyDTO getCompanyDTO() {
		return companyDTO;
	}

	public void setCompanyDTO(CompanyDTO companyDTO) {
		this.companyDTO = companyDTO;
	}

	@Override
	public String toString() {
		return "ComputerDTO [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", companyDTO=" + companyDTO + "]";
	}
	

	
}
