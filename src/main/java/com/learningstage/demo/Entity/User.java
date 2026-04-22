package com.learningstage.demo.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//USER ENTITY + AUTHORITIES
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

//@Builder
//
//Lets you create object like:
//
//User user = User.builder()
//    .username("john")
//    .password("123")
//    .role(Role.USER)
//    .build();


public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;



//    Purpose:
//
//Returns permissions / roles of user.
//
//If:
//
//role = ADMIN
//
//Then returns:
//
//ROLE_ADMIN
//
//If:
//
//role = USER
//
//Then:
//
//ROLE_USER






    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


//    Account Status Methods (Very Important)
//
//These control whether account can log in.
//
//4. isAccountNonExpired()
//return true;
//
//Means account is not expired.
//
//If false:
//
//❌ login blocked
//
//Use case:
//
//subscription ended
//temporary account expired
//5. isAccountNonLocked()
//return true;
//
//Means account is not locked.
//
//If false:
//
//❌ cannot login
//
//Use case:
//
//many wrong password attempts
//admin locked account
//suspicious activity
//6. isCredentialsNonExpired()
//return true;
//
//Means password still valid.
//
//If false:
//
//❌ user must reset password
//
//Use case:
//
//password older than 90 days
//7. isEnabled()
//return true;
//
//Means account active.
//
//If false:
//
//❌ login denied
//
//Use case:
//
//email not verified
//admin deactivated account
//user suspended
//Why You Returned true
//
//Right now all accounts are always valid.
//
//Simple beginner setup.
//
//Means:
//
//Not expired
//Not locked
//Password valid
//Enabled
//
//So everyone can login if password correct.
//
//Real Production Version
//
//You may add columns:
//
//private boolean enabled;
//private boolean locked;
//private LocalDate passwordExpiry;
//
//Then methods:
//
//public boolean isEnabled() {
//   return enabled;
//}
//public boolean isAccountNonLocked() {
//   return !locked;
//}
//Why These Methods Matter
//
//During login, Spring internally checks:
//
//isEnabled?
//isLocked?
//isExpired?
//password correct?
//
//If any fail:
//
//❌ authentication rejected
//
//Example
//
//If:
//
//isEnabled() = false
//
//Then even correct password won’t log in.
//
//Summary Table
//Method	Meaning	If False
//getAuthorities()	Roles/permissions	No access
//getUsername()	Login username	Auth issue
//getPassword()	Stored password	Cannot verify
//isAccountNonExpired()	Account valid date	Blocked
//isAccountNonLocked()	Not locked	Blocked
//isCredentialsNonExpired()	Password valid	Blocked
//isEnabled()	Active account	Blocked
}
