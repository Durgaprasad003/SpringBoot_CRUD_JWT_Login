package com.learningstage.demo.ProductController;

import com.learningstage.demo.Dto.*;
import com.learningstage.demo.Security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private  final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
           return ResponseEntity.ok(authService.login(loginRequestDto));
    }
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signup(@RequestBody LoginRequestDto signupDto){
        return ResponseEntity.ok(authService.signup(signupDto));
    }
    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponseDto> refresh(
            @RequestBody RefreshRequestDto dto) {

        return ResponseEntity.ok(authService.refresh(dto));
    }
}
