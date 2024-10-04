package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity // 해당 파일로 시큐리티를 활성화
@Configuration // IoC
public class SecurityConfig {

        @Bean
        public BCryptPasswordEncoder encode() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                // super 삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화 됨.
                http.csrf(csrf -> csrf.disable());
                http.authorizeHttpRequests(requests -> requests
                                .requestMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**",
                                                "/api/**")
                                .authenticated()
                                .anyRequest().permitAll())
                                .formLogin(login -> login
                                                .loginPage("/auth/signin") // GET
                                                .loginProcessingUrl("/auth/signin") // POST -> 스프링 시큐리티가 로그인 프로세스 진행
                                                .defaultSuccessUrl("/"));
                return http.build();
        }
}
