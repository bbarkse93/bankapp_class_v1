package com.tenco.bankapp.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bankapp.dto.WithdrawFormDTO;
import com.tenco.bankapp.dto.saveFormDTO;
import com.tenco.bankapp.handler.exception.CustomRestfulException;
import com.tenco.bankapp.handler.exception.UnAuthorizedException;
import com.tenco.bankapp.repository.entity.Account;
import com.tenco.bankapp.repository.entity.User;
import com.tenco.bankapp.service.AccountService;
import com.tenco.bankapp.utils.Define;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private HttpSession session;

    @Autowired
    private AccountService accountService;


    // 임시 예외 발생 확인 http://localhost:80/account/list
    @GetMapping({"/list", "/"})
    public String list(Model model) {

        // 인증 검사
        User principal = (User) session.getAttribute(Define.PRINCIPAL);
        if (principal == null) {
            throw new UnAuthorizedException("인증된 사용자가 아닙니다.", HttpStatus.UNAUTHORIZED);
        }

        List<Account> accountList = accountService.readAccountList(principal.getId());
        if (accountList.isEmpty()) {
            model.addAttribute("accountList", null);
        } else {
            model.addAttribute("accountList", accountList);
        }
        System.out.println("accountList : " + accountList.toString());
        return "account/list";
    }

    // 계좌 생성 페이지 요청
    @GetMapping("/save")
    public String save() {
        // 인증 검사
        User principal = (User) session.getAttribute(Define.PRINCIPAL);
        if (principal == null) {
            throw new UnAuthorizedException("로그인 먼저 해주세요", HttpStatus.UNAUTHORIZED);
        }

        return "account/save";
    }

    // 계좌 생성 기능
    @PostMapping("/save")
    public String saveProc(saveFormDTO dto) {
        // 인증 검사
        User principal = (User) session.getAttribute(Define.PRINCIPAL);
        if (principal == null) {
            throw new UnAuthorizedException("로그인 먼저 해주세요", HttpStatus.UNAUTHORIZED);
        }

        // 2. 유효성 검사
        if (dto.getNumber() == null || dto.getNumber().isEmpty()) {
            throw new CustomRestfulException("계좌 번호를 입력하세요", HttpStatus.BAD_REQUEST);
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new CustomRestfulException("계좌 비밀번호를 입력하세요", HttpStatus.BAD_REQUEST);
        }
        if (dto.getBalance() == null || dto.getBalance() <= 0) {
            throw new CustomRestfulException("잘못된 입력입니다.", HttpStatus.BAD_REQUEST);
        }

        accountService.createAccount(dto, principal.getId());

        return "account/list";
    }
    
    // 출금 페이지 요청
    @GetMapping("/withdraw")
    public String withdraw() {
    	// 1. 인증 검사
    	User principal = (User) session.getAttribute(Define.PRINCIPAL);
        if (principal == null) {
            throw new UnAuthorizedException("로그인 먼저 해주세요", HttpStatus.UNAUTHORIZED);
        }
        
    	return "account/withdraw";
    }

    @PostMapping("/withdraw")
    public String withdrawProc(WithdrawFormDTO dto) {
    	// 1. 인증검사 
    	User principal = (User) session.getAttribute(Define.PRINCIPAL);
        if (principal == null) {
            throw new UnAuthorizedException("로그인 먼저 해주세요", HttpStatus.UNAUTHORIZED);
        }
        
        // 2. 유효성 검사
        if (dto.getAmount() == null) {
			throw new CustomRestfulException("금액을 입력하세요", HttpStatus.BAD_REQUEST);
		}
        if (dto.getAmount().longValue() <= 0) {
			throw new CustomRestfulException("출금 금액이 0원 이하일 수 없습니다.", HttpStatus.BAD_REQUEST);
		}
        if (dto.getWAccountNumber() == null || dto.getWAccountNumber().isEmpty()) {
        	throw new CustomRestfulException("계좌번호를 입력하세요", HttpStatus.BAD_REQUEST);		
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
        	throw new CustomRestfulException("계좌 비밀번호를 입력하세요", HttpStatus.BAD_REQUEST);		
		}
        
        // 서비스 호출
        
        accountService.updateAccountWithdraw(dto, principal.getId());
        
    	return "redirect:/account/list";
    }
    

}
