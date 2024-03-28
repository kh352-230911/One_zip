package com.sh.onezip.oauth.service;

import com.sh.onezip.auth.service.AuthService;
import com.sh.onezip.auth.vo.MemberDetails;
import com.sh.onezip.member.entity.Address;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.service.MemberService;

import com.sh.onezip.oauth.utils.OAuth2UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class OAuth2UserServiceIml extends DefaultOAuth2UserService {

    @Autowired
    AuthService authService;

    @Autowired
    MemberService memberService;

    /**
     * 액세스토큰을 이용해 사용자정보(resource)를 획득한 이후 호출되는 메소드
     * - hello-springboot앱 내에서 구현된 OAuth2User객체를 반환 (SecurityContext에 Authentication으로 저장)
     *
     * @param userRequest the user request
     * @return
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        MemberDetails memberDetails = null;
        // ClientRegistration : IDP
        ClientRegistration clientRegistration = userRequest.getClientRegistration(); // google, kakao 알쉬브이
        OAuth2AccessToken accessToken = userRequest.getAccessToken(); // security가 session에 자동저장후 관리
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        log.debug("clientRegistration = {}", clientRegistration);
//        log.debug("accessToken = {}", accessToken);
        log.debug("oAuth2User = {}", oAuth2User);
//        log.debug("attributes = {}", attributes);

        String provider = clientRegistration.getRegistrationId(); // google, kakao
//
//        // google인 경우, {sub}@google이 username이 된다.
        String username = OAuth2UserUtils.getUsername(oAuth2User, provider);
        //기본주소정보 생성
        Address address = new Address();
        address.setBaseAddress("기본 주소");
        address.setDetailAddress("상세 주소");
        try {
            memberDetails = (MemberDetails) authService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
//            // username이 존재하지않는 경우 회원가입처리
            Member member = OAuth2UserUtils.of(oAuth2User, provider);
            memberService.createMember(member, address);
            memberDetails = (MemberDetails) authService.loadUserByUsername(username);
        }
            return memberDetails;
        }

}
