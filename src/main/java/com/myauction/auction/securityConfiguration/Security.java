package com.myauction.auction.securityConfiguration;

import com.myauction.auction.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Slf4j
public class Security {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Bean
    public SecurityFilterChain securityF(HttpSecurity httpSec, JwtFilter jwtFilter) throws Exception {
        return httpSec

                .csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth
                .requestMatchers( "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/auth/register/**",
                        "/auth/login/**").permitAll()
                        .requestMatchers("/auction/**","/product/**").hasRole("USER")
                        .requestMatchers("/placeBid/**").hasRole("USER")
                .requestMatchers("/admin/**").hasAnyRole("SUPER_ADMIN", "ADMIN")
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())

                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .logout(LogoutConfigurer::permitAll)
//                .formLogin(formlogin -> formlogin.loginPage("/auth/login").permitAll())
                .build();

    }
//    .formLogin(formlogin -> formlogin.loginPage("/login").permitAll()
//                        .failureUrl("/login?error=true"))
    @Bean
    public AuthenticationProvider authPro() throws Exception{
        DaoAuthenticationProvider authProvide = new DaoAuthenticationProvider();
        authProvide.setPasswordEncoder(encoderr());
        authProvide.setUserDetailsService(customUserDetailsService);
        return authProvide;

    }
    @Bean
    public PasswordEncoder encoderr() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
