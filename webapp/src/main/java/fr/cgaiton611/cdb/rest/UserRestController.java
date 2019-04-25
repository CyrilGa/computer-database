package fr.cgaiton611.cdb.rest;

import fr.cgaiton611.cdb.authentication.JwtTokenProvider;
import fr.cgaiton611.cdb.dto.CompanyDTO;
import fr.cgaiton611.cdb.exception.DAOException;
import fr.cgaiton611.cdb.exception.MappingException;
import fr.cgaiton611.cdb.exception.UpdateException;
import fr.cgaiton611.cdb.exception.entityValidation.EntityValidationException;
import fr.cgaiton611.cdb.mapper.CompanyMapper;
import fr.cgaiton611.cdb.model.Company;
import fr.cgaiton611.cdb.model.User;
import fr.cgaiton611.cdb.rest.parametersmanager.GetAllParametersManager;
import fr.cgaiton611.cdb.service.CompanyService;
import fr.cgaiton611.cdb.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {
    private final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    public UserRestController(UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity signUp(@Valid @RequestBody User user, BindingResult bindingResult) throws DAOException {
        if (bindingResult.hasErrors()) {
            Map<String, String> error = new HashMap<>();
            error.put("messsage", "ValidationException");
            return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        }

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");

        try {
            this.userService.create(user);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (UpdateException e) {
            Map<String, String> error = new HashMap<>();
            error.put("messsage", "ValidationException");
            return new ResponseEntity(error, HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @CrossOrigin
    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody User user) {

        try {
            String username = user.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, user.getPassword()));
            String token = jwtTokenProvider.createToken(username, Collections.singletonList(user.getRole()));

            user = userService.loadUser(username);

            Map<String, String> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            model.put("role", user.getRole());
            return ok(model);
        } catch (AuthenticationException e) {
            logger.warn(e.getMessage());
            return new ResponseEntity("AuthenticationException", HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin
    @PostMapping("/refresh_token")
    public ResponseEntity refreshToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return ok(jwtTokenProvider.refreshToken((org.springframework.security.core.userdetails.User) authentication.getPrincipal()));
    }
}
