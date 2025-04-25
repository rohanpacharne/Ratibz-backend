package com.lonar.master.a2zmaster.security;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.lonar.master.a2zmaster.model.CodeMaster;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
 
@Component
public class TokenInterceptor implements HandlerInterceptor,CodeMaster{
 
//    private static final String AUTH_TOKEN = "zYbAUeDCjKoopFsccssaavbHdjDCbdop";
//    private static final String AUTH_TOKEN = TokenProvider.getRandomToken();

 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
 
        if (token == null || !token.equals(AUTH_TOKEN)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            response.getWriter().write("Unauthorized: Missing or invalid token.");
            return false;
        }
        
        //use for random Token in tokenlist
//        System.out.println("Received token: [" + token + "]");
//        System.out.println("Token is valid: " + TokenProvider.isValidToken(token));
//        
//        if (token == null || !TokenProvider.isValidToken(token)) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
//            response.getWriter().write("Unauthorized: Missing or invalid token.");
//            return false;
//        }
 
        return true; // token is valid, proceed to controller
    }
}