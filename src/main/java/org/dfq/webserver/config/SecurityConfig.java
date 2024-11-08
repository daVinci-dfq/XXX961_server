package org.dfq.webserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // 仅限管理员角色访问
                        .requestMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN") // 用户或管理员角色均可访问
                        .anyRequest().authenticated() // 其他请求均需要认证
                )
                .formLogin(form -> form
                        .permitAll() // 启用默认登录页面，允许所有人访问
                )
                .logout(logout -> logout
                        .permitAll()
                );

        return http.build();  // 必须返回 http.build() 以完成配置
    }// 启用注销功能，


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }



}



