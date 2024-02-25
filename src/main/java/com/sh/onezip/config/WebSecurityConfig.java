package com.sh.onezip.config;

import com.sh.onezip.auth.handler.CustomSuccessHandler;
import com.sh.onezip.auth.service.AuthService;
import com.sh.onezip.auth.service.BusinessAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    OAuth2UserService oAuth2UserService;

    @Autowired
    @Qualifier("authService")
    UserDetailsService authService;
    @Autowired
    @Qualifier("businessAuthService")
    BusinessAuthService businessAuthService;
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) ->  web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain2(HttpSecurity httpSecurity, HandlerMappingIntrospector inst) throws Exception{
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(inst);
        httpSecurity.securityMatcher("/auth/login.do").authorizeHttpRequests(authorizeRequest -> authorizeRequest.requestMatchers(mvc.pattern("/auth/login*")))
                .formLogin((formLoginConfigurer -> {
                    formLoginConfigurer
                            .loginPage("/auth/login.do") // 로그인폼페이지 (GET) (작업필요)
                            .loginProcessingUrl("/auth/login.do") // 로그인처리 (POST)
                            .successHandler(new CustomSuccessHandler())
                            .permitAll();
                })).userDetailsService(authService);
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
    public SecurityFilterChain securityFilterChain3(HttpSecurity httpSecurity, HandlerMappingIntrospector inst) throws Exception{
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(inst);
        httpSecurity.securityMatcher("/auth/bizLogin.do").authorizeHttpRequests(authorizeRequest -> authorizeRequest.requestMatchers(mvc.pattern("/auth/bizLogin*")))
                .formLogin((formLoginConfigurer -> {
                    formLoginConfigurer
                            .loginPage("/auth/bizLogin.do") // 로그인폼페이지 (GET) (작업필요)
                            .loginProcessingUrl("/auth/bizLogin.do") // 로그인처리 (POST)
                            .successHandler(new CustomSuccessHandler())
                            .permitAll();
                })).userDetailsService(businessAuthService);
        httpSecurity.logout(logoutConfigurer -> {
            logoutConfigurer
                    .logoutUrl("/auth/logout.do") // 로그아웃처리 (POST)
                    .logoutSuccessUrl("/");
        });
        return httpSecurity.build();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector inst) throws Exception{
        httpSecurity.authorizeHttpRequests((authorizeRequest -> {
            authorizeRequest
                    .requestMatchers("/", "/templates/index.html").permitAll()
                    .requestMatchers("/admin/memberList.do", "/member/selectMemberType.do").permitAll()
                    .requestMatchers("/business/createbusiness.do","/business/checkIdDuplicate.do").anonymous()
                    .requestMatchers("/member/createMember.do","/member/checkIdDuplicate.do").anonymous()
                    //.requestMatchers("/business/createbusiness.do","/business/checkIdDuplicate.do").anonymous()
                    .requestMatchers("/product/**").authenticated()
                    .requestMatchers("/board/**").authenticated()
                    .requestMatchers("/community/**").authenticated()
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
        })).userDetailsService(authService);
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
