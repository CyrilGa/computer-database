package fr.cgaiton611.dto;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.cgaiton611.exception.mapping.MappingException;
import fr.cgaiton611.springconfig.SpringConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class ComputerMapperTest {

	@Autowired
	ComputerMapper computerMapper;

	@Test
	public void toComputer() {
		ComputerDTO computerDTO = new ComputerDTO("20", "slt", null, null, null);
		try {
			computerMapper.toComputer(computerDTO);
		} catch (MappingException e) {
			fail("mapping error");
		}
	}

	@Test
	public void toComputerFail() {
		ComputerDTO computerDTO = new ComputerDTO("non", "slt", null, null, null);
		try {
			computerMapper.toComputer(computerDTO);
			fail("mapping error");
		} catch (MappingException e) {
			// ok
		}
	}

}
