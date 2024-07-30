package com.scaffolding.optimization.Security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaffolding.optimization.Entities.Response.AuthCredentials;
import com.scaffolding.optimization.Entities.Response.AuthResponse;
import com.scaffolding.optimization.Entities.Response.ResponseApi;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        AuthCredentials authCredentials = null;
        try {
            authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authCredentials.getEmail(),
                authCredentials.getPassword(),
                Collections.emptyList());

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();

            Map<String, Object> claims = new HashMap<>();
            claims.put("id", userDetails.getId());
            claims.put("name", userDetails.getUsername());
            claims.put("authorities", userDetails.getAuthorities());
            claims.put("email", userDetails.getEmail());

            String token = jwtUtil.createToken(claims, userDetails.getUsername());

            String dateExpiredFormated = LocalDateTime.now().plusHours(10).toString();

            AuthResponse authResponse = new AuthResponse();
            authResponse.setId(userDetails.getId());
            authResponse.setToken(token);
            authResponse.setUserName(userDetails.getEmail());
            authResponse.setEmail(userDetails.getUsername());
            authResponse.setAuthorities(userDetails.getAuthorities());
            authResponse.setExpirationDate(dateExpiredFormated);

            response.getWriter()
                .write(new ObjectMapper().writeValueAsString(new ResponseApi<AuthResponse>() {
                    {
                        setData(authResponse);
                        setMessage("User authenticated successfully");
                        setSuccess(true);
                    }
            }));
            response.setContentType("application/json");
            response.getWriter().flush();
            super.successfulAuthentication(request, response, chain, authResult);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
