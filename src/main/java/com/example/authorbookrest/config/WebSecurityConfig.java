package com.example.authorbookrest.config;

import com.example.authorbookrest.entity.enums.UserType;
import com.example.authorbookrest.security.JWTAuthenticationTokenFilter;
import com.example.authorbookrest.security.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
    @RequiredArgsConstructor
    public class WebSecurityConfig {

        private final PasswordEncoder passwordEncoder;
        private final UserDetailsService userDetailsService;
        private final JwtAuthenticationEntryPoint authenticationEntryPoint;
        private final JWTAuthenticationTokenFilter jwtAuthenticationTokenFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.csrf().disable()
                    .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeHttpRequests()
//                    .requestMatchers("/swagger-ui/**").permitAll()
//                    .requestMatchers("/v3/api-docs/**").permitAll()
//                    .requestMatchers(HttpMethod.POST,"/v1/users/image/**").permitAll()
//                    .requestMatchers("/v1/users/getImage/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/v1/authors").hasAuthority(UserType.ADMIN.name())
                    .requestMatchers("/v1/users").permitAll()
                    .requestMatchers("/v1/users/auth").permitAll()
                    .anyRequest().authenticated();

            httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
            return httpSecurity.build();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setUserDetailsService(userDetailsService);
            authenticationProvider.setPasswordEncoder(passwordEncoder);
            return authenticationProvider;
        }


    }

