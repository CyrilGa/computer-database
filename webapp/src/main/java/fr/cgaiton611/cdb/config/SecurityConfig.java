package fr.cgaiton611.cdb.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.model.User;
import fr.cgaiton611.cdb.service.UserService;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = { "fr.cgaiton611.cdb.service" })
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	@PostConstruct
	private void init() {
		try {
			userService.create(new User("user", passwordEncoder().encode("user"), "USER"));
		} catch (DAOException e) {
			logger.warn("User cannot be created");
		}
		
		try {
			userService.create(new User("admin", passwordEncoder().encode("admin"), "ADMIN"));
		} catch (DAOException e) {
			logger.warn("User cannot be created");
		}
	}

	@Autowired
	private UserService userService;

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

//	@Autowired
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("password")).roles("USER");
//		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/addComputer").authenticated().anyRequest().permitAll().and().formLogin()
				.defaultSuccessUrl("/dashboard", true).failureUrl("/login?error=true");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
