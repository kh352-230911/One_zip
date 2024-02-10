package com.sh.onezip.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;

@Slf4j
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        // 로그인 후처리 작업
        // 1. 알림 테이블에서 미확인 알림 조회
        // 2. ..


        /**
         * 기본 redirect 페이지 지정
         */
        String targetUrl = "/";


        /**
         * 인증전 접근시도한 페이지가 session에 SPRING_SECURITY_SAVED_REQUEST 속성명으로 저장되어 있다.
         */
        SavedRequest savedRequest = (SavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        if (savedRequest != null) {
            targetUrl = savedRequest.getRedirectUrl();
        }

        log.debug("targetUrl = {}", targetUrl);

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
}