package com.learningstage.demo.Security;

import com.learningstage.demo.DAO.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//LOAD USER FROM DB
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


//    Because Spring Security expects a UserDetails object, not specifically your User entity.

//    Your class implements:
//
//UserDetailsService
//
//That interface defines:
//
//UserDetails loadUserByUsername(String username)
//
//So you must use same return type.
//
//What is UserDetails?
//
//UserDetails is a Spring Security interface representing a logged-in user.
//
//It provides methods like:
//
//getUsername()
//getPassword()
//getAuthorities()
//isAccountNonLocked()
//isEnabled()
//
//Spring uses these during authentication and authorization.
//
//Why Not Return User Directly?
//
//You can return your User object if your User class implements UserDetails.
//
//Example:
//
//public class User implements UserDetails
//
//Then this works:
//
//return userrepo.findByUsername(username).orElseThrow();
//
//Because:
//
//User IS-A UserDetails
//
//That is polymorphism.
//
//Real Example
//User user = new User();
//UserDetails details = user;
//
//Valid only if User implements UserDetails.
//
//Why Spring Designed It This Way
//
//Spring doesn’t want to depend on your database entity.
//
//Some projects use:
//
//Customer
//Employee
//AccountUser
//
//Different classes.
//
//So Spring says:
//
//Just give me anything that implements UserDetails.
//
//Flexible design.
//
//If Your User Does NOT Implement UserDetails
//
//Then convert manually:
//
//return org.springframework.security.core.userdetails.User
//    .withUsername(user.getUsername())
//    .password(user.getPassword())
//    .authorities("ROLE_USER")
//    .build();
//Short Answer
//
//Return type is UserDetails because:
//
//Interface requires it
//Spring Security works with UserDetails abstraction
//Your User can be returned if it implements UserDetails
    private final UserRepository userrepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userrepo.findByUsername(username).orElseThrow();
    }
}
