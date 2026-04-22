package com.learningstage.demo.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


// SECURITY RULES AND FILTER CHAIN SETUP
//This class configures Spring Security for your application.
        //
//        It defines:
//
//        Security rules
//        Which URLs are public/private
//        JWT authentication filter
//        Stateless session mode
//        Role-based access control
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class    WebSecurityConfig {

    private final  JwtAuthFilter jwtAuthFilter;
    private final CustomAuthenticationEntryPoint authEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                // For REST API testing (Postman / frontend JSON requests)
//                .csrf(AbstractHttpConfigurer::disable)

                .csrf(csrfConfigurer ->csrfConfigurer.disable() )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // Public URLs
                        .requestMatchers(
                                "/auth/**"
                        ).permitAll()
                                // Public read access
                                .requestMatchers(HttpMethod.GET, "/products/get", "/products/{id}")
                                .permitAll()

                                // Admin only write access
                                .requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/products/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")

//
//                                .requestMatchers(HttpMethod.GET, "/products/**")
//                                .hasAnyRole("USER","ADMIN")
//                                .requestMatchers(HttpMethod.POST, "/products/**")
//                                .hasRole("ADMIN")
//                                .requestMatchers("/products/", "/products/get", "/products/*").permitAll()
//                                .requestMatchers(HttpMethod.PUT, "/products/**")
//                                .hasRole("ADMIN")
//
//                                .requestMatchers(HttpMethod.PATCH, "/products/**")
//                                .hasRole("ADMIN")
//
//                                .requestMatchers(HttpMethod.DELETE, "/products/**")
//                                .hasRole("ADMIN")
//                                // Protected product write APIs
//                                .requestMatchers("/products/save", "/products/update/**").authenticated()

                        // Only ADMIN can create/update/delete
//                        .requestMatchers(
//                                "/products/save",
//                                "/products/update/**",
//                                "/products/**"
//                        ).hasRole("ADMIN")

                        // Any other request must login
                        .anyRequest().authenticated()
                )

//                // Login page
//                .formLogin(form -> form
//                        .defaultSuccessUrl("/products/get", true)
//                        .permitAll()
//                )

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
//        Add my custom filter (jwtAuthFilter) into the security filter chain before the built-in UsernamePasswordAuthenticationFilter.
//        What is UsernamePasswordAuthenticationFilter.class?
//                This is a built-in Spring Security filter class.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                // Logout
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/login?logout")
//                        .permitAll()
//                )

                // For Postman Basic Auth
//                .httpBasic(Customizer.withDefaults());

//        Finish the HttpSecurity configuration, create the SecurityFilterChain, and return it as a bean.
        return http.build();
    }


//    passwordencoder is a interface
        @Bean
   public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
   }

//   VALIDATES USERNAME AND PASSWORD
//It is the main authentication engine in Spring Security.
//Receive authentication request → verify credentials → return authenticated user or throw exception.
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}




























//package com.learningstage.demo.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//
//public class WebSecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
////                .csrf(csrf -> csrf.disable())   // disable csrf for testing
//
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/register", "/login").permitAll()
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
//                        .anyRequest().authenticated()
//                )
//
//                .formLogin(form -> form
//                        .loginPage("/login")              // custom login page
//                        .defaultSuccessUrl("/home", true)
//                        .permitAll()
//                )
//
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                        .permitAll()
//                )
//
//                .httpBasic(Customizer.withDefaults()); // optional for Postman
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}