package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.photogramstart.config.oauth.Oauth2DetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity // 해당 파일로 시큐리티를 활성화
@Configuration // IoC
public class SecurityConfig {

        private final Oauth2DetailsService oauth2DetailsService;

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
                                                .defaultSuccessUrl("/"))
                                .oauth2Login(oauth2 -> oauth2 // form로그인도 하는데, oauth2로그인도 할 거야
                                                .userInfoEndpoint(endpoint -> endpoint // oauth2로그인을 하면 최종응답을 회원정보를 바로 받을 수 있다.
                                                                .userService(oauth2DetailsService)));
                return http.build();
        }
}
