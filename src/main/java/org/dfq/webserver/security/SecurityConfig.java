package org.dfq.webserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // 所有请求都允许
                )
                .formLogin(form -> form.disable())  // 禁用登录界面
                .httpBasic(httpBasic -> httpBasic.disable());  // 禁用 HTTP Basic 认证

        return http.build();
    }
}
