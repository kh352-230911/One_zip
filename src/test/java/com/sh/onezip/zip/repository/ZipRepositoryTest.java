package com.sh.onezip.zip.repository;

import com.sh.onezip.authority.entity.Authority;
import com.sh.onezip.authority.entity.RoleAuth;
import com.sh.onezip.authority.repository.AuthorityRepository;
import com.sh.onezip.member.entity.Gender;
import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.repository.MemberRepository;
import com.sh.onezip.zip.entity.Zip;
import com.sh.onezip.zipattachment.entity.ZipAttachment;
import com.sh.onezip.zipattachment.repository.ZipAttachmentRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
//@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ZipRepositoryTest {
    @Autowired
    ZipRepository zipRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ZipAttachmentRepository zipAttachmentRepository;

    @DisplayName("ZipRepository빈은 null이 아니다.")
    @Test
    void test0(){
        assertThat(zipRepository).isNotNull();
    }

    @DisplayName("집 등록 및 조회")
    @Test
    @Transactional
    void test1() {
        Member member = Member.builder()
                .memberId("abcde")
                .password("kkk1234")
                .name("강감찬")
                .nickname("alphabet")
                .birthday(LocalDate.of(1999, 9, 9))
                .gender(Gender.M)
                .phone("01012341234")
                .memberAddr("경기도 태백시")
                .regDate(LocalDate.of(2024, 2, 1))
                .build();
        memberRepository.save(member);
        assertThat(member).isNotNull();
        System.out.println("member : " + member);

        Zip zip = Zip.builder()
                .id(1L)
                .colorCode("#F00000")
                .content("새내용")
                .member(member)
                .regDate(LocalDate.of(2024, 2, 1))
                .build();
        zipRepository.save(zip);
        assertThat(zip.getId()).isNotNull().isNotZero();
        System.out.println("zip : " + zip);
    }

//    @Disabled
//    @DisplayName("전체 집 조회")
//    @Test
//    void test2(){
//        List<Zip> zips = zipRepository.findAll(Sort.by("id").descending());
//        assertThat(zips)
//                .isNotEmpty();
////                .isSortedAccordingTo(Collections.reverseOrder());
//        System.out.println("zips : " + zips);
//    }

//    @DisplayName("멤버id로 집 조회")
//    @Test
//    void test3(){
//        Member member = Member.builder().memberId("abcde").build();
//        System.out.println(member);
//        List<Zip> zips = zipRepository.findAll(Sort.by("id").descending());
//        assertThat(zips).isNotNull();
//    }
//
//    @DisplayName("집 수정")
//    @Test
//    void test4(){
//        Member member = Member.builder().memberId("honggd1").build();
//        Zip zip = zipRepository.findByMemberId(member).orElseThrow();
//        String newContent = "새내용";
//        String newColorCode = "#000000";
//        zip.setContent(newContent);
//        zip.setColorCode(newColorCode);
//        zipRepository.save(zip);
//
//        Zip zip2 = zipRepository.findByMemberId(member).orElseThrow();
//        assertThat(zip2).isNotNull()
//                .satisfies((_zip -> {
//                    assertThat(_zip.getContent()).isEqualTo(newContent);
//                    assertThat(_zip.getColorCode()).isEqualTo(newColorCode);
//                }));
//    }
//
//    @Disabled
//    @DisplayName("집 삭제")
//    @Test
//    void test5(){
//        Member member = Member.builder().memberId("honggd1").build();
//        Zip zip = zipRepository.findByMemberId(member).orElseThrow();
//        assertThat(zip).isNotNull();
//
//        zipRepository.delete(zip);
//
//        zipRepository.findByMemberId(member).orElse(null);
//        assertThat(zip.getId()).isNull();
//    }
}