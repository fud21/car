package com.nagne.guardplatform.controller;

import com.nagne.guardplatform.dto.LoginRequestDto;
import com.nagne.guardplatform.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtUtil jwtUtil;

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "1234";

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto request) {
        if (ADMIN_USERNAME.equals(request.getUsername()) && ADMIN_PASSWORD.equals(request.getPassword())) {
            String token = jwtUtil.generateToken(request.getUsername()); // 💡 여기!
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("로그인 실패: 아이디 또는 비밀번호가 틀립니다.");
        }
    }
}
