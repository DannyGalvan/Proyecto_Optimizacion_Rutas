package com.scaffolding.optimization.config;

import java.util.List;

import com.scaffolding.optimization.api.filters.JwtAuthenticationFilter;
import com.scaffolding.optimization.api.filters.JwtRequestFilter;
import com.scaffolding.optimization.config.Security.JwtUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.CorsFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaffolding.optimization.database.Entities.Response.ResponseApi;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    /*
     * This method is used to configure the security filter chain.
     * It is used to configure the security filter chain to permit all the requests
     * that are not secured and to authorize the requests that are secured.
     *
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager)
            throws Exception {

        var jwtAuthenticationFilter = new JwtAuthenticationFilter(new JwtUtil());
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");

        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(request -> permitAllRequestMatchers().stream()
                            .anyMatch(matcher -> matcher.matches(request))).permitAll()
                            .requestMatchers(request -> authenticatedRequestMatchers().stream()
                            .anyMatch(matcher -> matcher.matches(request))).authenticated();
                })
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> {
                    ex.authenticationEntryPoint((request, response, failed) -> {
                        var responseWrapper = new ResponseApi<String>();
                        responseWrapper.setSuccess(false);
                        responseWrapper.setMessage("Authentication failed");
                        responseWrapper.setData(null);

                        response.getWriter().write(
                                new ObjectMapper().writeValueAsString(
                                        responseWrapper));
                        response.setContentType("application/json");
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().flush();
                    });
                    ex.accessDeniedHandler((request, response, failed) -> {
                        var responseWrapper = new ResponseApi<String>();
                        responseWrapper.setSuccess(false);
                        responseWrapper.setMessage("Access denied");
                        responseWrapper.setData(null);

                        response.getWriter().write(
                                new ObjectMapper().writeValueAsString(
                                        responseWrapper));
                        response.setContentType("application/json");
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.getWriter().flush();
                    });
                })
                .cors(cors -> cors.configurationSource(WebConfigCors.corsConfigurationSource()))
                .build();
    }

    /*
     * This method is used to load the user by username.
     * It is used by the authentication manager to validate the user if exists in
     * the record.
     *
     */
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder)
            throws Exception {
        var authenticationManagerBuilder = new DaoAuthenticationProvider();
        authenticationManagerBuilder.setUserDetailsService(userDetailsService);
        authenticationManagerBuilder.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationManagerBuilder);

    }

    /*
     * This method is used to encode the password.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilterFilterRegistrationBean() {
        FilterRegistrationBean<CorsFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new CorsFilter(WebConfigCors.corsConfigurationSource()));
        filterFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterFilterRegistrationBean;
    }

    private List<RequestMatcher> permitAllRequestMatchers() {
        return List.of(
                new AntPathRequestMatcher("/api/v1/customer/register", "POST"),
                new AntPathRequestMatcher("/api/v1/customer/all", "GET"),
                new AntPathRequestMatcher("/api/v1/products/upload", "POST"),
                new AntPathRequestMatcher("/api/v1/supplier/upload", "POST"),
                new AntPathRequestMatcher("/api/v1/customer/addresses/**", "GET"),
                new AntPathRequestMatcher("/api/v1/classification/all", "GET"),
                new AntPathRequestMatcher("/api/v1/gas-price/all", "POST"),
                new AntPathRequestMatcher("/doc/**", "GET"),
                new AntPathRequestMatcher("/v3/api-docs/**", "GET"),
                new AntPathRequestMatcher("/swagger-ui/**", "GET"),                
                new AntPathRequestMatcher("/api/v1/login", "POST"));
    }

    private List<RequestMatcher> authenticatedRequestMatchers() {
        return List.of(
            new AntPathRequestMatcher("/api/v1/users/**", "GET"),
            new AntPathRequestMatcher("/api/v1/products/filter/**", "GET"),
            new AntPathRequestMatcher("/api/v1/orders/**", "POST"),
            new AntPathRequestMatcher("/api/v1/shipments/**", "GET"),
            new AntPathRequestMatcher("/api/v1/shipments/**", "POST"));
            
    }
}
