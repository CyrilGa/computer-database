package fr.cgaiton611.cdb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
        .authorizeRequests()
        .antMatchers("/addComputer").authenticated()
        .anyRequest().permitAll()
		.and()
        .formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/loginProcess")
        .defaultSuccessUrl("/dashboard", true)
        .failureUrl("/login?error=true");
	}

}
