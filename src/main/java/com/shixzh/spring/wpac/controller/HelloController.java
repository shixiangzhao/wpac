package com.shixzh.spring.wpac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

	@RequestMapping("/redis")
	public ModelAndView hello() {
		String message = "Hello, world.";
		return new ModelAndView("hello.jsp", "message", message);
	}
}
