package ru.castroy10.security_mongo.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.castroy10.security_mongo.service.AppUserDetailsService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtFilter extends OncePerRequestFilter {
private final JwtUtil jwtUtil;
private final AppUserDetailsService appUserDetailsService;

    public JwtFilter(JwtUtil jwtUtil, AppUserDetailsService appUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.appUserDetailsService = appUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeaderToken = request.getParameter("Authorization");
//        String authHeaderToken = request.getHeader("Authorization"); //не через параметры, а через заголовок GET запроса
        if (authHeaderToken != null && authHeaderToken.startsWith("Bearer ")) { //Проверяем, что токен состоит минимум из слова Bearer +" "
            try {
                String username = jwtUtil.verifyToken(authHeaderToken.substring(7)); //удаляем из пришедшего параметра слово Bearer+" ", оставляем только токен
                UserDetails userDetails = appUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (TokenExpiredException e) {
                String jsonData = getJsonString("JWT Token is expired");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(jsonData);
                return;
            } catch (JWTVerificationException e) {
                String jsonData = getJsonString("Invalid JWT Token");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(jsonData);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    //    Метод генерирует json string c текстом ответа и временем
    private static String getJsonString(String error) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> answer = new HashMap<>();
        answer.put("error", error);
        answer.put("timestamp", LocalDateTime.now().toString());
        return objectMapper.writeValueAsString(answer);
    }
}
