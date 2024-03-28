package com.sh.onezip.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    OAuth2UserService oAuth2UserService;

    @Autowired
    @Qualifier("authService")
    UserDetailsService authService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) ->  web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/templates/index.html").permitAll()
                        .requestMatchers("/auth/**","/admin/memberList.do").permitAll()
                        .requestMatchers("/member/createMember.do","/member/checkIdDuplicate.do", "/member/selectMemberType.do").anonymous()
                        .requestMatchers("/board/**").authenticated()
                        .requestMatchers("/business/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/business/**").hasRole("BUSINESS")
                        .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/auth/login.do") // 로그인 페이지 URL
                        .loginProcessingUrl("/auth/login.do") // 로그인 처리 URL
                        .permitAll())
                .logout((logout) -> logout
                        .logoutUrl("/auth/logout.do") // 로그아웃 처리 URL
                        .logoutSuccessUrl("/")) // 로그아웃 성공 후 리다이렉트 URL
                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/auth/login.do")
                        .userInfoEndpoint((user) -> user.userService(oAuth2UserService)));

        return http.build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
