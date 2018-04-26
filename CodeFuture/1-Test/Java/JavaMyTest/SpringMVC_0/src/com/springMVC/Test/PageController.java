package com.springMVC.Test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping("/login")
	public String hello(Model model) {
		model.addAttribute("msg", "controller-login");
		return "login";
	}
}
