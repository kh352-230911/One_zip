package com.sh.onezip.zip.repository;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.zip.entity.Zip;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
//@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ZipRepositoryTest {
    @Autowired
    ZipRepository zipRepository;

    @DisplayName("ZipRepository빈은 null이 아니다.")
    @Test
    void test0(){
        assertThat(zipRepository).isNotNull();
    }

    @DisplayName("집 등록")
    @Test
    @Transactional
    void test1() {
        // given
        Member member = Member.builder().memberId("honggd1").build();
        Zip zip = Zip.builder()
                .content("내용")
                .member(member)
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
                    assertThat(_zip.getContent()).isNotNull();
                    assertThat(_zip.getColorCode()).isNotNull();
                    assertThat(_zip.getRegDate()).isNotNull();
                }));
    }

    @DisplayName("전체 집 조회")
    @Test
    void test2(){
        List<Zip> zips = zipRepository.findAll(Sort.by("id").descending());
        assertThat(zips)
                .isNotEmpty();
//                .isSortedAccordingTo(Collections.reverseOrder());
    }
//
//    @DisplayName("멤버id로 집 조회")
//    @Test
//    void test3(){
//        Member member = Member.builder().memberId("honggd1").build();
//        System.out.println(member);
//        Zip zip = zipRepository.findByMember(member).orElse(null);
//        assertThat(zip).isNotNull();
//    }
//
//    @DisplayName("집 수정")
//    @Test
//    void test4(){
//        Member member = Member.builder().memberId("honggd1").build();
//        Zip zip = zipRepository.findByMember(member).orElseThrow();
//        String newContent = "새내용";
//        String newColorCode = "#000000";
//        zip.setContent(newContent);
//        zip.setColorCode(newColorCode);
//        zipRepository.save(zip);
//
//        Zip zip2 = zipRepository.findByMember(member).orElseThrow();
//        assertThat(zip2).isNotNull()
//                .satisfies((_zip -> {
//                    assertThat(_zip.getContent()).isEqualTo(newContent);
//                    assertThat(_zip.getColorCode()).isEqualTo(newColorCode);
//                }));
//    }
//
//    @DisplayName("집 삭제")
//    @Test
//    void test5(){
//        Member member = Member.builder().memberId("honggd1").build();
//        Zip zip = zipRepository.findByMember(member).orElseThrow();
//        assertThat(zip).isNotNull();
//
//        zipRepository.delete(zip);
//
//        zipRepository.findByMember(member).orElse(null);
//        assertThat(zip.getId()).isNull();
//    }
}