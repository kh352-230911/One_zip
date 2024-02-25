package com.sh.onezip.bizauthority.handler;//package com.sh.onezip.bizauthority.handler;

import com.sh.onezip.bizauthority.vo.BizDetails;
import com.sh.onezip.businessproduct.entity.Businessmember;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;

@Slf4j
public class BizCustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 사용자가 로그인에 성공했을 때의 동작을 정의합니다.
        // 예를 들어, 성공 메시지를 출력하거나 특정 페이지로 리다이렉트할 수 있습니다.

        // 성공 메시지 출력
        System.out.println("사업자 회원 로그인에 성공했습니다.");

        // 사용자 정보나 로그인 시간 등을 세션에 저장하거나 추가적인 작업을 수행할 수 있습니다.
        HttpSession session = request.getSession();
        session.setAttribute("user", authentication.getName()); // 인증된 사용자의 이름을 세션에 저장
        Businessmember businessmember = new Businessmember();
        // 로그인 성공 후 특정 페이지로 리다이렉트
        response.sendRedirect(request.getContextPath() + "/businessproduct/businessproductlist.do?bizMemberId=" + businessmember.getBizMemberId());
    }
}