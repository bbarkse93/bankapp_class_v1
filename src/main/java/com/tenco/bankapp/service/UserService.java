package com.tenco.bankapp.service;

import com.tenco.bankapp.dto.SignUpFormDTO;
import com.tenco.bankapp.handler.exception.CustomRestfulException;
import com.tenco.bankapp.repository.entity.User;
import com.tenco.bankapp.repository.interfaces.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public int signUp(SignUpFormDTO dto) {
    	// 중복 여부 확인 생략
    	
        User user = User.builder().username(dto.getUsername()).password(dto.getPassword()).fullname(dto.getFullname()).build();
        int resultRowCount = userRepository.insert(user);

        if (resultRowCount != 1) {
            throw new CustomRestfulException("회원가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return resultRowCount;
    }
}
