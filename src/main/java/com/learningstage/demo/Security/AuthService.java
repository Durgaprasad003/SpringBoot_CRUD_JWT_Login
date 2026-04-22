package com.learningstage.demo.Security;


import com.learningstage.demo.DAO.UserRepository;
import com.learningstage.demo.Dto.*;
import com.learningstage.demo.Entity.Role;
import com.learningstage.demo.Entity.User;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private  final AuthenticationManager authenticationManager;
    private  final AuthUtil authUtil;
    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public @Nullable LoginResponseDto login(LoginRequestDto loginRequestDto) {
       Authentication authentication= authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword())
       );
        User user=(User) authentication.getPrincipal();
        String token=authUtil.generateAccesstoken(user);
        String refreshToken = authUtil.generateRefreshToken(user);

        return  new LoginResponseDto(token,user.getId(),user.getRole().name(),refreshToken);

    }
    public RefreshResponseDto refresh(RefreshRequestDto dto) {

        if(!authUtil.validateToken(dto.getRefreshToken())) {
            throw new RuntimeException("Invalid refresh token");
        }

        String username =
                authUtil.getUsernamefromtoke(dto.getRefreshToken());

        User user = userRepository.findByUsername(username).orElseThrow();

        String newAccessToken =
                authUtil.generateAccesstoken(user);

        return new RefreshResponseDto(newAccessToken);
    }

    public @Nullable SignUpResponseDto signup(LoginRequestDto signupDto) {

        User user = userRepository.findByUsername(signupDto.getUsername()).orElse(null);
        if(user!=null)
        {
            throw  new IllegalArgumentException("user allready exist");
        }
        user=userRepository.save(User.builder()
                .username(signupDto.getUsername())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .role(Role.USER)
                .build()
        );
        return new SignUpResponseDto(user.getId(),user.getUsername());
    }
}
