package fr.cgaiton611.cdb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import fr.cgaiton611.cdb.service.UserService;

@Configuration
@EnableGlobalMethodSecurity(
  prePostEnabled = true, 
  securedEnabled = true, 
  jsr250Enabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
  private final Logger logger = LoggerFactory.getLogger(MethodSecurityConfig.class);
  
  public MethodSecurityConfig() {
    logger.info("##### MethodSecurityConfig is being initialized ... #####");
  }
}
