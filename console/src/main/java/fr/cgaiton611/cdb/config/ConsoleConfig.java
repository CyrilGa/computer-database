package fr.cgaiton611.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "fr.cgaiton611.cdb.dao", "fr.cgaiton611.cdb.service", "fr.cgaiton611.cdb.cli",
		"fr.cgaiton611.cdb.page" })
public class ConsoleConfig {

}
