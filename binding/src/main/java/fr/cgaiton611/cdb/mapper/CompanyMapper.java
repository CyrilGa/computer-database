package fr.cgaiton611.cdb.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.cgaiton611.cdb.dto.CompanyDTO;
import fr.cgaiton611.cdb.model.Company;
import fr.cgaiton611.cdb.util.ConvertUtil;

@Service
public class CompanyMapper {

	private ConvertUtil convertUtil = new ConvertUtil();

	public Company toCompany(CompanyDTO companyDTO) {
		Company company = new Company();

		Optional<Long> id = convertUtil.stringToLong(companyDTO.getId());
		if (id.isPresent())
			company.setId(id.get());

		company.setName(companyDTO.getName());

		return company;
	}

	public CompanyDTO toCompanyDTO(Company company) {
		CompanyDTO companyDTO = new CompanyDTO();

		companyDTO.setId(String.valueOf(company.getId()));

		companyDTO.setName(company.getName());

		return companyDTO;
	}

	public List<CompanyDTO> toCompanyDTOList(List<Company> companies) {
		List<CompanyDTO> companiesDTO = new ArrayList<>();
		for (Company company : companies) {
			companiesDTO.add(toCompanyDTO(company));
		}
		return companiesDTO;
	}
}
