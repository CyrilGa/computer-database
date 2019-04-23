package fr.cgaiton611.cdb.config;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.model.User;
import fr.cgaiton611.cdb.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
@ComponentScan(basePackages = { "fr.cgaiton611.cdb.service", "fr.cgaiton611.cdb.dao" })
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	private UserService userService;
	
	public SecurityConfig(UserService userService) {
		logger.info("##### SecurityConfig is being initialized ... #####");
		this.userService = userService;
	}
	
	@PostConstruct
	public void init() {
		try {
			userService.loadUserByUsername("user");
		} catch (UsernameNotFoundException e) {
			try {
				userService.create(new User("user", passwordEncoder().encode("user"), "USER"));
			} catch (DAOException e1) {
				logger.debug(e.getMessage());
			}
		}

		try {
			userService.loadUserByUsername("admin");
		} catch (UsernameNotFoundException e) {
			try {
				userService.create(new User("admin", passwordEncoder().encode("admin"), "ADMIN"));
			} catch (DAOException e1) {
				logger.debug(e.getMessage());
			}
		}
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider(userService));
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http
    .httpBasic()
    .and()
    .authorizeRequests()
    .antMatchers("/login**").permitAll()
    .antMatchers("/register*").permitAll()
    .antMatchers("/api/**").permitAll()
    .anyRequest().authenticated()
    .and()
    .formLogin()
    .and()
    .logout();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
