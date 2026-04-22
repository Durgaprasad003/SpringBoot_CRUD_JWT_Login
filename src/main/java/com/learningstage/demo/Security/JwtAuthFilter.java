package com.learningstage.demo.Security;

import com.learningstage.demo.DAO.UserRepository;
import com.learningstage.demo.Entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


//READ JWT FROM THE REQUEST
//JwtAuthFilter is a custom Spring Security filter that runs for every request.
//
//        What It Does
//        Reads the Authorization header
//        Checks if JWT token exists (Bearer token)
//        Validates the token
//        Extracts username from token
//        Loads user from database
//        Sets authentication in Spring Security context
//        Passes request to next filter/controller
@Component
@Slf4j
//This is a Lombok annotation that automatically creates a logger object for your class.
//        It is equivalent to writing: private static final Logger log = LoggerFactory.getLogger(MyClass.class);
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private  final UserRepository userRepository;
    private  final AuthUtil authUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("incoming request {}",request.getRequestURI());

        final String requestokenheader=request.getHeader("Authorization");
         System.out.println(requestokenheader);
        if(requestokenheader==null|| !requestokenheader.startsWith("Bearer "))
        {


//            If this if block executes, all code below it will NOT run. **************************
//            No Authorization header.
                Then:
//            goes to next filter
//            no token validation
//            no username extraction
//            no authentication setup



//            If:
//            header is missing, OR
//            header does not start with "Bearer "
//            Then your JWT filter says:
//“I cannot authenticate using JWT. Continue request without JWT login.”


            filterChain.doFilter(request,response);
//            This passes request to the next filter in Spring Security chain.
            return;
//            Stops this method immediately.
        }



        String token=requestokenheader.substring(7);
        if(!authUtil.validateToken(token))
        {
//            If token is:
//        expired
//            invalid signature
//            malformed
//            fake token
//            Then:
//            Skip login and continue request.
//            Again
//            next filter runs
//            this method stops
//            code below does NOT run

//            Checks whether token is:
//            valid
//            not expired
//            correctly signed
//            trusted token
            filterChain.doFilter(request,response);
            return;
        }


//        If username is null, it means:
//
//        token is invalid
//        token is empty
//        username claim missing
//        parsing failed
//
//        Without username, Spring cannot load user details.

            String username=authUtil.getUsernamefromtoke(token);
//        If a username was found and no user is currently authenticated for this request, then proceed to authenticate the user.
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
//            This block takes the username from JWT, loads the real user, creates a Spring Security authentication object, and marks the request as logged in.
//            user = User(id=1, username=john, role=ADMIN)
//            But Spring needs complete user info:
//
//            password (sometimes)
//            roles
//                    authorities
//            account enabled
//            locked or not
//            custom fields
//
//            So we load full user object.
            User user=userRepository.findByUsername(username).orElseThrow();




//            This creates a Spring Security Authentication object representing a logged-in user.
//It creates an Authentication object.
//
//Spring Security uses Authentication interface to represent logged-in user.
//
//UsernamePasswordAuthenticationToken is a common implementation.
//            Without authorities, user may authenticate but cannot access role-based endpoints.
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());




            //            This line stores the authenticated user object in Spring Security’s context so the current request is recognized as logged in.
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            What happens after setAuthentication()
//
//            Spring now knows:
//
//✅ user is authenticated
//✅ username = john
//✅ roles = ADMIN
//
//            Now protected endpoints can be accessed.





//            “This request belongs to this user. Treat them as logged in.”
//            Read token from request
//            Extract username
//            Load user from DB
//            Create Authentication object
//            Put it into SecurityContext
//            Spring allows protected APIs
        }
        filterChain.doFilter(request,response);
    }

}
