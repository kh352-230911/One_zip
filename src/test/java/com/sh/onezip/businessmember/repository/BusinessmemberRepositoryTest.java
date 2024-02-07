package com.sh.onezip.businessmember.repository;

import com.sh.onezip.businessmember.entity.BizAccess;
import com.sh.onezip.businessmember.entity.Businessmember;
import com.sh.onezip.businessmember.service.BusinessmemberService;
import com.sh.onezip.product.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BusinessmemberRepositoryTest {

    @Autowired
    BusinessmemberRepository businessmemberRepository;

    @DisplayName("BusinessmemberRepository빈은 null이 아닙니다.")
    @Test
    public void test0(){
        assertThat(businessmemberRepository).isNotNull();
    }

    @DisplayName("사업자 회원 등록 및 조회")
    @Test
    public void test1(){
        Businessmember businessmember = Businessmember.builder()
                .bizMemberId("biz1234")
                .bizPassword("1234")
                .bizName("홍찰찰")
                .bizPhone("010-1234-1234")
                .bizAddr("경기도 용인시")
                .bizLicense("제출 완료")
                .bizRegNo("13DFG3489")
                .bizRegStatus(BizAccess.D)
                .build();
        businessmemberRepository.save(businessmember);
        Businessmember businessmember2 = businessmemberRepository.findByBizMemberId(businessmember.getBizMemberId());
        assertThat(businessmember2).isNotNull();
        assertThat(businessmember2.getBizName()).isEqualTo("홍찰찰");

    }


}