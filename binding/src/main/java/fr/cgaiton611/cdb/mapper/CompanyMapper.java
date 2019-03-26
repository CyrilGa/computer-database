package fr.cgaiton611.cdb.mapper;

import java.util.Optional;

import fr.cgaiton611.cdb.dto.CompanyDTO;
import fr.cgaiton611.cdb.model.Company;
import fr.cgaiton611.cdb.util.ConvertUtil;


public class CompanyMapper {

	private static ConvertUtil convertUtil = new ConvertUtil();

	public static Optional<Company> toCompany(CompanyDTO companyDTO) {
		Company company = new Company();

		Optional<Long> id = convertUtil.stringToLong(companyDTO.getId());
		if (!id.isPresent())
			return Optional.empty();
		company.setId(id.get());

		company.setName(companyDTO.getName());

		return Optional.of(company);
	}

	public static CompanyDTO toCompanyDTO(Company company) {
		CompanyDTO companyDTO = new CompanyDTO();

		companyDTO.setId(String.valueOf(company.getId()));

		companyDTO.setName(company.getName());

		return companyDTO;
	}
}