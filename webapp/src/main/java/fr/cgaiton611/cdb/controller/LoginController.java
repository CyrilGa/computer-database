package fr.cgaiton611.cdb.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

	@GetMapping
	public String doGet(@RequestParam(required = false, name = "error") String pError, Model model,
			Principal principal) {

		if (principal != null) {
			model.addAttribute("username", principal.getName());
		}

		model.addAttribute("error", pError);
		return "login";
	}
}
