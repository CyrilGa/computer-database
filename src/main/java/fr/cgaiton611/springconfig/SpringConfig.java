package fr.cgaiton611.springconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(basePackages= {
		"fr.cgaiton611.model",
		"fr.cgaiton611.persistence",
		"fr.cgaiton611.service",
		"fr.cgaiton611.dto",
		"fr.cgaiton611.cli"})
public class SpringConfig {
	
}
