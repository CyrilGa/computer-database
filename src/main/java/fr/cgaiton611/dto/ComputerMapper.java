package fr.cgaiton611.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cgaiton611.exception.dao.DAOException;
import fr.cgaiton611.exception.mapping.IdNotConvertibleException;
import fr.cgaiton611.exception.mapping.MappingException;
import fr.cgaiton611.model.Company;
import fr.cgaiton611.model.Computer;
import fr.cgaiton611.service.CompanyService;
import fr.cgaiton611.util.ConvertUtil;

@Service
public class ComputerMapper {

	private final Logger logger = LoggerFactory.getLogger(ComputerMapper.class);

	@Autowired
	private CompanyService companyService;

	private ConvertUtil convertUtil = new ConvertUtil();

	public Computer toComputer(ComputerDTO computerDTO) throws MappingException{
		Computer computer = new Computer();
		
		Optional<Long> id = convertUtil.stringToLong(computerDTO.getId());
		if (id.isPresent()) {
			computer.setId(id.get());
		}

		computer.setName(computerDTO.getName());

		Optional<Date> introduced = convertUtil.stringToDate(computerDTO.getIntroduced());
		computer.setIntroduced(introduced.orElse(null));

		Optional<Date> discontinued = convertUtil.stringToDate(computerDTO.getDiscontinued());
		computer.setDiscontinued(discontinued.orElse(null));

		if (computerDTO.getCompanyName() != null && computerDTO.getCompanyName() != "") {
			Company company;
			try {
				company = companyService.findByName(computerDTO.getCompanyName());
				computer.setCompany(company);
			} catch (DAOException e) {
				logger.warn(e.getMessage());
				computer.setCompany(null);
			}
		} else {
			computer.setCompany(null);
		}
		return computer;
	}

	public ComputerDTO toComputerDTO(Computer computer) {
		ComputerDTO computerDTO = new ComputerDTO();

		computerDTO.setId(String.valueOf(computer.getId()));

		computerDTO.setName(computer.getName());

		computerDTO.setIntroduced(convertUtil.dateToString(computer.getIntroduced()));

		computerDTO.setDiscontinued(convertUtil.dateToString(computer.getDiscontinued()));

		computerDTO.setCompanyName(computer.getCompany().getName());

		return computerDTO;
	}

	public List<ComputerDTO> toComputerDTOList(List<Computer> computers) {
		List<ComputerDTO> computerDTOs = new ArrayList<>();
		for (Computer computer : computers) {
			computerDTOs.add(toComputerDTO(computer));
		}
		return computerDTOs;
	}

}
