package com.learningstage.demo.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

//
//What is it?
//
//Runs when:
//
//User logged in
//BUT no permission
//
//Example:
//
//USER tries DELETE /products/1
//
//Spring returns 403.
//
//You customize JSON response.
@Component
public class CustomAccessDeniedHandler  implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException ex) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        response.getWriter().write("""
        {
          "success": false,
          "message": "Access Denied"
        }
        """);
    }
}