package com.tenco.bankapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bankapp.dto.SignInFormDTO;
import com.tenco.bankapp.dto.SignUpFormDTO;
import com.tenco.bankapp.handler.exception.CustomRestfulException;
import com.tenco.bankapp.repository.entity.User;
import com.tenco.bankapp.service.UserService;
import com.tenco.bankapp.utils.Define;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    // 회원가입 페이지 요청
    // http://localhost:80/user/sign-up
    @GetMapping("/sign-up")
    public String signUp() {
        return "user/signUp";
    }

    // 로그인 페이지 요청
    // http://localhost:80/user/sign-in
    @GetMapping("/sign-in")
    public String signIn() {
        return "user/signIn";
    }

    /**
     * 회원가입 처리
     *
     * @param dto
     * @return 리다이렉트 로그인 페이지
     */
    @PostMapping("/sign-up")
    public String signUpProc(SignUpFormDTO dto) {

        // 1. 유효성 검사
        if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
            throw new CustomRestfulException("username을 입력하세요", HttpStatus.BAD_REQUEST);
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new CustomRestfulException("password를 입력하세요", HttpStatus.BAD_REQUEST);
        }
        if (dto.getFullname() == null || dto.getFullname().isEmpty()) {
            throw new CustomRestfulException("fullname을 입력하세요", HttpStatus.BAD_REQUEST);
        }

        int resultRowCount = userService.signUp(dto);
        if (resultRowCount != 1) {
            // 다른 처리
        }

        return "redirect:/user/sign-in";
    }

    @PostMapping("/sign-in")
    public String signInProc(SignInFormDTO dto) {
        // 1. 유효성 검사
        if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
            throw new CustomRestfulException("username을 입력하세요", HttpStatus.BAD_REQUEST);
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new CustomRestfulException("password를 입력하세요", HttpStatus.BAD_REQUEST);
        }

        // 2. 서비스 호출
        User principal = userService.signIn(dto);
        session.setAttribute(Define.PRINCIPAL, principal); // 세션 메모리에 사용자 정보를 저장

        return "redirect:/account/list";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();

        return "redirect:/user/sign-in";
    }

}
