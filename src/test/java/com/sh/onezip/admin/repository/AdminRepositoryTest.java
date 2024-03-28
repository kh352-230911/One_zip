package com.sh.onezip.admin.repository;

import com.sh.onezip.authority.repository.AuthorityRepository;
import com.sh.onezip.business.entity.BizAccess;
import com.sh.onezip.business.entity.Business;
import com.sh.onezip.business.repository.BusinessRepository;
import com.sh.onezip.customeranswercenter.entity.AnswerCenter;
import com.sh.onezip.customeranswercenter.repository.AnswerCenterRepository;
import com.sh.onezip.customerquestioncenter.entity.QuestionCenter;
import com.sh.onezip.customerquestioncenter.repository.QuestionCenterRepository;
import com.sh.onezip.member.repository.MemberRepository;
import com.sh.onezip.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AdminRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    BusinessRepository businessRepository;
    @Autowired
    AnswerCenterRepository answerCenterRepository;
    @Autowired
    QuestionCenterRepository questionCenterRepository;

    // HBK start
    @DisplayName("AuthorityRepository빈은 null이 아니다.")
    @Test
    void test0(){
        assertThat(authorityRepository).isNotNull();
    }

    @DisplayName("관리자 전체 회원 관리")
    @Test
    void test1(){
    // 컨트롤러에서 처리
    }
    @DisplayName("사업자 회원 변경 요청 승인 처리")
    @Test
    void test2(){
        // 사업자회원고유번호로 사업자 회원 찾기
        Optional<Business> business = businessRepository.findById(25L);
        assertThat(business).isNotNull();

        // 찾은 사업자 엔티티가 존재하는 경우 .isPresent -> 값이 존재 하는지
        if (business.isPresent()) {
        // 기존의 사업자 엔티티를 가져옴
        }
        Business newbusiness = business.get();
        // 사업자 승인 상태로 변경
        newbusiness.setBizRegStatus(BizAccess.W);
        businessRepository.save(newbusiness);

        // 승인 상태인지 확인
        Business approvedBusiness = businessRepository.findById(business.get().getId()).orElse(null);
        assertThat(approvedBusiness).isNotNull();
        assertThat(approvedBusiness.getBizRegStatus()).isEqualTo(BizAccess.W);
    }
    @DisplayName("사업자 회원 변경 요청 반려 처리")
    @Test
    void test3(){
        // 사업자회원고유번호로 사업자 회원 찾기
        Optional<Business> business = businessRepository.findById(23L);
        assertThat(business).isNotNull();

        // 찾은 사업자 엔티티가 존재하는 경우 .isPresent -> 값이 존재 하는지
        if (business.isPresent()) {
            // 기존의 사업자 엔티티를 가져옴
        }
        Business newbusiness = business.get();
        // 사업자 승인 상태로 변경
        newbusiness.setBizRegStatus(BizAccess.D);
        businessRepository.save(newbusiness);

        // 승인 상태인지 확인
        Business rejectedBusiness = businessRepository.findById(business.get().getId()).orElse(null);
        assertThat(rejectedBusiness).isNotNull();
        assertThat(rejectedBusiness.getBizRegStatus()).isEqualTo(BizAccess.D);
    }

    @DisplayName("관리자 사업자 회원 전체 관리")
    @Test
    void test4(){
    // 컨트롤러에서 처리
    }
    @DisplayName("관리자 전체 문의글 내역")
    @Test
    void test5(){
        // 컨트롤러에서 처리
    }
    @DisplayName("관리자 문의글 답변 작성")
    @Test
    void test6(){
    //  문의 회원 찾기
    Member member = memberRepository.findByMemberId("tangtong");
    assertThat(member).isNotNull();

    // 문의글 가져오기
    QuestionCenter questionCenter = questionCenterRepository.findByQId(2L);
    assertThat(questionCenter).isNotNull();
    // 고객센터 문의글 답변 등록 하기
    AnswerCenter answerCenter = AnswerCenter.builder()
            .member(member)
            .questionCenter(questionCenter)
            .aoneContent("고객님 진정하세요 조만간 업데이트 해드릴게요!_!")
            .build();
    // 답변 저장
    answerCenterRepository.save(answerCenter);
    }

    @DisplayName("관리자 문의글 답변 수정")
    @Test
    void test7(){
     // 답변 고유번호 찾기
     Optional<AnswerCenter> answerCenter = answerCenterRepository.findById(79L);
     assertThat(answerCenter).isPresent();
        if(answerCenter.isPresent()) {
            AnswerCenter newanswer = answerCenter.get();
        // 답변 고유번호와 회원 고유번호가 맞는지 비교 했어야 더 완벽한 코드 였음
        // 답글 수정
        newanswer.setAoneContent("음원 저작권 때문에 비지엠 가져오지 못해요ㅠㅠ.. 죄송합니다.");

        // 수정된 정보 엔티티에 저장
        answerCenterRepository.save(newanswer);
        }
        }
    @DisplayName("관리자 문의글 삭제")
    @Test
    void test8(){
    // 컨트롤러에서 처리
    }
    @DisplayName("관리자 사업자 등록 상품 삭제")
    @Test
    void test9(){
    // 컨트롤러에서 처리
    }

    @DisplayName("관리자 메인페이지 배너 선택")
    @Test
    void test11(){

    }
}


