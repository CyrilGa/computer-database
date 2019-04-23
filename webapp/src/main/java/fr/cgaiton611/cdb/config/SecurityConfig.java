package fr.cgaiton611.cdb.config;

import javax.annotation.PostConstruct;

import fr.cgaiton611.cdb.authentication.JwtConfigurer;
import fr.cgaiton611.cdb.authentication.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.model.User;
import fr.cgaiton611.cdb.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
@ComponentScan(basePackages = {"fr.cgaiton611.cdb.service", "fr.cgaiton611.cdb.dao", "fr.cgaiton611.cdb.authentication"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private UserService userService;
    private JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(UserService userService, JwtTokenProvider jwtTokenProvider) {
        logger.info("##### SecurityConfig is being initialized ... #####");
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
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

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/users/register").permitAll()
                .antMatchers("/api/v1/users/signin").permitAll()
                //.anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

}
