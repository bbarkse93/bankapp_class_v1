package com.tenco.bankapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

	// 임시 예외 발생 확인 http://localhost:80/account/list
	@GetMapping({"/list", "/"})
	public String list() {
		
		return "account/list";
	}
	
}
