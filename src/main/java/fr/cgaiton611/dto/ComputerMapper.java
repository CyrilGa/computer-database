package fr.cgaiton611.dto;

import java.util.Date;
import java.util.Optional;

import fr.cgaiton611.cli.util.ConvertUtil;
import fr.cgaiton611.model.Company;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.service.CompanyService;

public class ComputerMapper {
	
	private static ConvertUtil convertUtil = new ConvertUtil();
	private static CompanyService companyService = CompanyService.getInstance();
	
	public static Optional<Computer> toComputer(ComputerDTO computerDTO) {
		Computer computer = new Computer();
		
		Optional<Long> id = convertUtil.stringToLong(computerDTO.getId());
		if (! id.isPresent()) return Optional.empty();
		computer.setId(id.get());
		
		computer.setName(computerDTO.getName());
		
		Optional<Date> introduced = convertUtil.stringToDate(computerDTO.getIntroduced());
		if (! introduced.isPresent()) return Optional.empty();
		computer.setIntroduced(introduced.get());
		
		Optional<Date> discontinued = convertUtil.stringToDate(computerDTO.getDiscontinued());
		if (! discontinued.isPresent()) return Optional.empty();
		computer.setDiscontinued(discontinued.get());
		
		Optional<Company> company = companyService.findByName(computerDTO.getCompanyName());
		if (! company.isPresent()) return Optional.empty();
		computer.setCompany(company.get());
		
		return Optional.of(computer);
	}
	
	public static ComputerDTO toComputerDTO(Computer computer) {
		ComputerDTO computerDTO = new ComputerDTO();
		
		computerDTO.setId(String.valueOf(computer.getId()));
		
		computerDTO.setName(computerDTO.getName());
		
		computerDTO.setIntroduced(convertUtil.dateToSting(computer.getIntroduced()));
		
		computerDTO.setDiscontinued(convertUtil.dateToSting(computer.getDiscontinued()));
		
		computerDTO.setCompanyName(computer.getCompany().getName());
		
		return computerDTO;
	}
	
}
