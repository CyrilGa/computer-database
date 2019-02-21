package fr.cgaiton611.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import fr.cgaiton611.model.Company;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.service.CompanyService;
import fr.cgaiton611.util.ConvertUtil;

public class ComputerMapper {
	
	private static ConvertUtil convertUtil = new ConvertUtil();
	private static CompanyService companyService = CompanyService.getInstance();

	public static Optional<Computer> toComputer(ComputerDTO computerDTO) {
		Computer computer = new Computer();
		String name = computerDTO.getName();
		if(name == null || "".equals(name)) {
			return Optional.empty();
		}
		computer.setName(name);

		Optional<Date> introduced = convertUtil.stringToDate(computerDTO.getIntroduced());
		computer.setIntroduced(introduced.orElse(null));
		
		Optional<Date> discontinued = convertUtil.stringToDate(computerDTO.getDiscontinued());
		computer.setDiscontinued(discontinued.orElse(null));

		Optional<Company> company = companyService.findByName(computerDTO.getCompanyName());
		if(! company.isPresent()) {
			return Optional.empty();
		}
		computer.setCompany(company.get());

		return Optional.of(computer);
	}

	public static ComputerDTO toComputerDTO(Computer computer) {
		ComputerDTO computerDTO = new ComputerDTO();

		computerDTO.setId(String.valueOf(computer.getId()));

		computerDTO.setName(computer.getName());

		computerDTO.setIntroduced(convertUtil.dateToSting(computer.getIntroduced()));

		computerDTO.setDiscontinued(convertUtil.dateToSting(computer.getDiscontinued()));

		computerDTO.setCompanyName(computer.getCompany().getName());

		return computerDTO;
	}

	public static List<ComputerDTO> toComputerDTOList(List<Computer> computers) {
		List<ComputerDTO> computerDTOs = new ArrayList<>();
		for (Computer computer : computers) {
			computerDTOs.add(toComputerDTO(computer));
		}
		return computerDTOs;
	}

}
