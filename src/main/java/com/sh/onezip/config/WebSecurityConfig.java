package com.sh.onezip.config;

import com.sh.onezip.auth.handler.CustomSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    OAuth2UserService oAuth2UserService;
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) ->  web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests((authorizeRequest -> {
            authorizeRequest
                    .requestMatchers("/", "/templates/index.html").permitAll()
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/member/createMember.do","/member/checkIdDuplicate.do").anonymous()
                    .requestMatchers("/business/createbusiness.do","/member/checkIdDuplicate.do").anonymous()
                    .requestMatchers("/product/**").authenticated()
                    .requestMatchers("/board/**").authenticated()
//                    .requestMatchers("/community/**").authenticated()
                    .requestMatchers("/zip/**").authenticated()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated();
        }));

        httpSecurity.formLogin((formLoginConfigurer -> {
            formLoginConfigurer
                    .loginPage("/auth/login.do") // 로그인폼페이지 (GET) (작업필요)
                    .loginProcessingUrl("/auth/login.do") // 로그인처리 (POST)
                    .successHandler(new CustomSuccessHandler())
                    .permitAll();
        }));
        httpSecurity.logout(logoutConfigurer -> {
            logoutConfigurer
                    .logoutUrl("/auth/logout.do") // 로그아웃처리 (POST)
                    .logoutSuccessUrl("/");
        });
        httpSecurity.oauth2Login(httpSecurityOAuth2LoginConfigurer -> {
            httpSecurityOAuth2LoginConfigurer
                    .loginPage("/auth/login.do")
                    .userInfoEndpoint(userInfoEndpointConfig -> {
                        userInfoEndpointConfig.userService(oAuth2UserService);
                    });
        });

        return httpSecurity.build();
    }
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
