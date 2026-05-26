package com.Auth.Authcore.config;
import com.Auth.Authcore.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/",
                                "/login",
                                "/register",
                                "/otp-verify",
                                "/forgot-password",
                                "/reset-password",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/styles.css",
                                "/app.js",
                                "/favicon.ico",
                                "/auth/login",
                                "/auth/register",
                                "/auth/logout",
                                "/auth/verifyotp",
                                "/auth/forgetpass",
                                "/auth/resetpass",
                                "/auth/me"
                        ).permitAll()

                        .requestMatchers("/admin-dashboard")
                        .permitAll()

                        .requestMatchers("/dashboard").permitAll()
                        .requestMatchers("/api/admin/**")
                        .hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}