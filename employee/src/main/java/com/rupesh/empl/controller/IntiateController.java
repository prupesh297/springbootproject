package com.rupesh.empl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IntiateController {

	@RequestMapping("/")
	public String startPage() {
		return "index";
	}
}
