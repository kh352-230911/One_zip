package com.sh.onezip.zip.repository;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.member.repository.MemberRepository;
import com.sh.onezip.zip.entity.Zip;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @DisplayName("ZipRepository빈은 null이 아니다.")
    @Test
    void test0(){
        assertThat(zipRepository).isNotNull();
    }

//    @Disabled
    @DisplayName("집 등록 및 조회")
    @Test
    @Transactional
    void test1() {
        // given
        Member member = memberRepository.findByName("홍길동3");
        System.out.println(member);
        Zip zip = Zip.builder()
                .member(member)
                .content("내용")
                .colorCode("#FFFFFF")
                .build();
        // wen & then
        zipRepository.save(zip);
        assertThat(zip.getId()).isNotNull().isNotZero();
        System.out.println(zip);

        Zip zip2 = zipRepository.findById(zip.getId()).orElse(null);
        System.out.println(zip2);
        assertThat(zip2)
                .isNotNull()
                .satisfies((_zip -> {
                    assertThat(_zip.getMember()).isNotNull();
                    assertThat(_zip.getContent()).isNotNull();
                    assertThat(_zip.getColorCode()).isNotNull();
                    assertThat(_zip.getRegDate()).isNotNull();
                }));
    }

    @Disabled
    @DisplayName("집 수정")
    @Test
    void test2(){
        Member member = memberRepository.findByName("홍길동3");
        System.out.println(member);
        Zip zip = Zip.builder()
                .member(member)
                .content("내용")
                .colorCode("#FFFFFF")
                .build();
        zipRepository.save(zip);

        String newContent = "새내용";
        String newColorCode = "#000000";
        zip.setContent(newContent);
        zip.setColorCode(newColorCode);
        zipRepository.save(zip);

        Zip zip2 = zipRepository.findById(zip.getId()).orElse(null);
        System.out.println(zip2);
        assertThat(zip2)
                .isNotNull()
                .satisfies((_zip -> {
                    assertThat(_zip.getContent()).isEqualTo(newContent);
                    assertThat(_zip.getColorCode()).isEqualTo(newColorCode);
                }));
    }

    @Disabled
    @DisplayName("집 삭제")
    @Test
    void test3(){
        Member member = memberRepository.findByMemberId("sinsa1");
        Zip zip = Zip.builder()
                .member(member)
                .content("내용")
                .colorCode("#FFFFFF")
                .build();
        zipRepository.save(zip);
        assertThat(zip.getId()).isNotNull().isNotZero();
        System.out.println(zip);

        Zip zip2 = zipRepository.findById(zip.getId()).orElse(null);

        zipRepository.delete(zip2);

//        zipRepository.findByMemberId(member).orElse(null);
        assertThat(zip.getId()).isNull();
        System.out.println(zip2);
    }
}