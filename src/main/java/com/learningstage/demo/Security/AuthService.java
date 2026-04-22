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

//
//        When user logs in:
//
//        POST /auth/login
//        {
//            "username": "ram",
//                "password": "1234"
//        }
//
//        This line performs the verification.
//
//                Full Breakdown
//        1. loginRequestDto.getUsername()
//        loginRequestDto.getUsername()
//
//        Gets username sent by user.
//
//                Example:
//
//        ram
//        2. loginRequestDto.getPassword()
//        loginRequestDto.getPassword()
//
//        Gets plain password from request.
//
//                Example:
//
//        1234
//        3. new UsernamePasswordAuthenticationToken(...)
//        new UsernamePasswordAuthenticationToken(
//                username,
//                password
//        )
//
//        Creates an authentication request object.
//
//                It means:
//
//        Here are credentials. Please authenticate this user.
//
//Important Note
//
//At this stage, user is not authenticated yet.
//
//This object simply contains:
//
//principal = username
//credentials = password
//authorities = empty (not verified yet)
//        3. new UsernamePasswordAuthenticationToken(...)
//new UsernamePasswordAuthenticationToken(
//    username,
//    password
//)
//
//Creates an authentication request object.
//
//It means:
//
//Here are credentials. Please authenticate this user.
//
//Important Note
//
//At this stage, user is not authenticated yet.
//
//This object simply contains:
//
//principal = username
//credentials = password
//authorities = empty (not verified yet)
//Why This Class Name?
//
//Because it is designed for:
//
//username + password login
//4. authenticationManager.authenticate(...)
//authenticationManager.authenticate(token)
//
//This sends login request to Spring Security authentication engine.
//
//What Happens Internally
//
//Usually:
//
//AuthenticationManager
//
//Implementation:
//
//ProviderManager
//
//Delegates to:
//
//DaoAuthenticationProvider
//
//Then:
//
//Step A: Load User
//
//Calls your UserDetailsService
//
//loadUserByUsername("ram")
//
//Gets user from DB.
//
//Step B: Check Password
//
//Uses PasswordEncoder
//
//passwordEncoder.matches("1234", storedHash)
//Step C: If Success
//
//Returns authenticated object.
//
//Step D: If Fail
//
//Throws exception:
//
//BadCredentialsException
//UsernameNotFoundException
//Disabled account, etc.
//5. Result Stored Here
//Authentication authentication =
//
//Now variable contains authenticated user info.
//
//Usually:
//
//principal = UserDetails/User
//authorities = roles
//authenticated = true
//Example After Success
//authentication.getName()
//
//Returns:
//
//ram
//authentication.getAuthorities()
//
//Returns:
//
//ROLE_ADMIN
//Why Save It in Variable?
//
//Because later you may use:
//
//String username = authentication.getName();
//
//or
//
//SecurityContextHolder.getContext()
//    .setAuthentication(authentication);
//
//or generate JWT token.



//        User enters credentials
//↓
//Create UsernamePasswordAuthenticationToken
//↓
//AuthenticationManager.authenticate()
//↓
//Load user from DB
//↓
//Compare password hash
//↓
//If correct → Authentication object returned
//↓
//Generate JWT token


//        Usually this happened first:        *********************************************
//
//        Authentication authentication =
//                authenticationManager.authenticate(...)
//
//        If username/password correct:
//
//        login success
//        authenticated object returned
//
//        Then this code executes.



//
//
//        After Authentication (returned)
//                Spring returns:
//        Authentication authentication
//        Contains:
//        authenticated user object
//                username
//        authorities / roles
//        authenticated = true
//        UsernamePasswordAuthenticationToken
//                [Principal=ram,
//                Credentials=[PROTECTED],
//        Authenticated=true,
//                Details=null,
//                Granted Authorities=[ROLE_USER]]
       Authentication authentication= authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword())
       );


//        Convert generic object into specific type.
//
//        Object -> User
        User user=(User) authentication.getPrincipal();//This code runs after successful login.
//        Get the authenticated user, generate JWT tokens, and send login response to frontend.
//        Returns the main logged-in user object.
//        user
//        contains:
//        id
//                username
//        role
//                authorities
//        password (usually stored object field)



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

//        What This Does
//
//Search database for same username.
//
//If found:
//
//returns User
//
//If not found:
//
//returns null

        User user = userRepository.findByUsername(signupDto.getUsername()).orElse(null);
        if(user!=null)
        {
            throw  new IllegalArgumentException("user allready exist");
        }
        user=userRepository.save(User.builder()//User.builder( Uses Lombok Builder pattern Creates object cleanly.
                .username(signupDto.getUsername())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .role(Role.USER)
                .build()//Final User entity created.
        );
        return new SignUpResponseDto(user.getId(),user.getUsername());
    }
}
