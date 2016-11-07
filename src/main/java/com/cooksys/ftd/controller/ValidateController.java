package com.cooksys.ftd.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.ftd.service.ValidateService;

@RestController
@RequestMapping("validate")
public class ValidateController {
	
	ValidateService validateService;

	public ValidateController (ValidateService service) {
		validateService = service;
	}
	
	//Checks whether or not a given username exists.
	//return a boolean
	//done
	@GetMapping("/username/exists/@{username}")
	public boolean checkName(@PathVariable String username) {
		return validateService.checkName(username);
	}
	
	//Checks whether or not a given username is available.
	//return a boolean
	//done
	@GetMapping("/username/available/@{username}")
	public boolean availableName(@PathVariable String username) {
		return validateService.availableName(username);
	}
	
	//Checks whether or not a given hashtag exists.
	//return a boolean 
	//done
	@GetMapping("/tag/exists/{label}")
	public boolean checkTag(@PathVariable String label) {
		return validateService.checkTag(label);
	}
}
