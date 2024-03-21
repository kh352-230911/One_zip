package com.sh.onezip.member.repository;

import com.sh.onezip.authority.entity.Authority;
import com.sh.onezip.authority.entity.RoleAuth;
import com.sh.onezip.authority.repository.AuthorityRepository;
import com.sh.onezip.customerquestioncenter.entity.QuestionCenter;
import com.sh.onezip.customerquestioncenter.repository.QuestionCenterRepository;
import com.sh.onezip.member.entity.Gender;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import com.sh.onezip.member.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    QuestionCenterRepository questionCenterRepository;

    @DisplayName("MemberRepository빈은 null이 아니다.")
    @Test
    void test0(){
        assertThat(memberRepository).isNotNull();
    }


//    @DisplayName("회원 등록 및 조회")
//    @Disabled
//    @Test
//    public void test1(){
//        Member member = Member.builder()
//                .memberId("abcde")
//                .password("kkk1234")
//                .name("강감찬")
//                .nickname("alphabet")
//                .email("test123@example.com")
//                .birthday(LocalDate.of(1999, 9, 9))
//                .gender(Gender.M)
//                .phone("01012341234")
//                .regDate(LocalDate.of(2024, 2, 1))
//                .build();
//        memberRepository.save(member);
//        Member member2 = memberRepository.findByName(member.getName());
//        Authority authority = Authority.builder()
//                .memberId(member2.getMemberId())
//                .userType(RoleAuth.ROLE_USER)
//                .build();
//        authorityRepository.save(authority);
//
//        assertThat(member.getMemberId()).isNotNull();
//        assertThat(authority.getId()).isNotNull().isNotZero();
//    }
//
//
//    @DisplayName("username 회원 조회시 권한정보도 함께 조회되어야 한다.")
//    @Test
//    void test2() {
//        // given
//        Member member = Member.builder()
//                .memberId("honggd1")
//                .password(passwordEncoder.encode("1234"))
//                .name("홍길동3")
//                .nickname("alphabet345")
//                .birthday(LocalDate.of(1999, 9, 9))
//                .gender(Gender.M)
//                .phone("01012341234")
//                .regDate(LocalDate.of(2024, 2, 1))
//                .build();
//        memberRepository.save(member);
//
//        Authority authority = Authority.builder()
//                .memberId(member.getMemberId())
//                .userType(RoleAuth.ROLE_USER)
//                .build();
//        authorityRepository.save(authority);
//        // when
//        Member member2 = memberRepository.findByMemberId(member.getMemberId());
//        System.out.println(member2);
//        // then
//        assertThat(member2).isNotNull();
//
//        assertThat(member2.getAuthorities())
//                .isNotEmpty()
//                .allSatisfy((_authority) -> {
//                    assertThat(_authority.getId()).isEqualTo(authority.getId());
//                    assertThat(_authority.getMemberId()).isEqualTo(member.getMemberId());
//                    assertThat(_authority.getUserType()).isEqualTo(authority.getUserType());
//                });
//    }

// HBK start
@DisplayName("회원은 고객센터에 문의글을 작성 할 수 있다.")
@Test
void test3(){
    // 1. 일반회원으로 로그인을 한 회원 찾기
    Member member = memberRepository.findByMemberId("biztest7");
    assertThat(member).isNotNull();
    // 2. 문의글 등록
    QuestionCenter questionCenter = QuestionCenter.builder()
            .member(member)
            .qoneContent("아직 사업자 등록증이 발행 되지 않았을 경우는 사업자 회원으로 권한이 변경 될 수는 없는 건가요?")
            .build();
    // 3. 문의글 등록 정보 저장
    questionCenterRepository.save(questionCenter);
}

}

