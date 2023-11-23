package com.tenco.bankapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/*
 * 실행의 제어권을 가지고 있으면 라이브러리 
 * 실행의 제어권을 가지고 있지 않으면 프레임워크
*/
@Controller // IoC에 대상(제어의 역전)
@RequestMapping("/temp") // 공통적인 부분을 반복해서 사용해야할 경우 사용하는 어노테이션
public class TestController {
	
	// 주소설계 - http://localhost:80/temp-test
	@GetMapping("/temp-test")
	public String tempTest() {
		return "temp";  
	}
	
	// 주소설계 - http://localhost:80/temp/main-page
	@GetMapping("/main-page")
	public String tempMainPage() {
		return "main";
	}
	
}
