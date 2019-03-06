package fr.cgaiton611.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cgaiton611.model.Company;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.service.CompanyService;
import fr.cgaiton611.util.ConvertUtil;

@Service
public class ComputerMapper {

	@Autowired
	private static CompanyService companyService;
	
	private static ConvertUtil convertUtil = new ConvertUtil();

	public static Optional<Computer> toComputer(ComputerDTO computerDTO) {
		Computer computer = new Computer();

		String idS = computerDTO.getId();
		Long id = 0l;
		if (idS != null) {
			id = Long.parseLong(idS);
		}
		computer.setId(id);

		computer.setName(computerDTO.getName());

		Optional<Date> introduced = convertUtil.stringToDate(computerDTO.getIntroduced());
		computer.setIntroduced(introduced.orElse(null));

		Optional<Date> discontinued = convertUtil.stringToDate(computerDTO.getDiscontinued());
		computer.setDiscontinued(discontinued.orElse(null));

		if (computerDTO.getCompanyName() != null) {
			Optional<Company> company = companyService.findByName(computerDTO.getCompanyName());
			if (!company.isPresent()) {
				computer.setCompany(null);
			}
			computer.setCompany(company.get());
		}
		else {
			computer.setCompany(null);
		}
		return Optional.of(computer);
	}

	public static ComputerDTO toComputerDTO(Computer computer) {
		ComputerDTO computerDTO = new ComputerDTO();

		computerDTO.setId(String.valueOf(computer.getId()));

		computerDTO.setName(computer.getName());

		computerDTO.setIntroduced(convertUtil.dateToString(computer.getIntroduced()));

		computerDTO.setDiscontinued(convertUtil.dateToString(computer.getDiscontinued()));

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
