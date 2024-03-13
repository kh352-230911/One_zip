package com.sh.onezip.member.entity;

import com.sh.onezip.authority.entity.Authority;
import com.sh.onezip.member.entity.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert // null이 아닌 필드만 등록
@DynamicUpdate // 영속성컨텍스트의 엔티티와 달라진 필드만 수정
@Table(name = "tb_member")
public class Member {

    @Id
    @GeneratedValue(generator = "seq_Member_id_generator")
    @SequenceGenerator(
            name = "seq_Member_id_generator",
            sequenceName = "tb_member_seq",
            initialValue = 1,
            allocationSize = 1)
    private Long id;
    @Column(nullable = false, unique = true)
    private String memberId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column
    private String email;
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate regDate;
    @Column(nullable = false,unique = true)
    private String nickname;
    @Column(nullable = false)
    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(nullable = false)
    private String phone;
    @Column
    private String hobby;
    @Column
    private String mbti;
    @Column(name = "PROFILE_PICTURE_URL")
    private String profileUrl;
    @Column(name= "PROFILE_PICTURE_KEY")
    private String profileKey;



    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id") //authority 테이블의 컬럼명시
    private List<Authority> authorities = new ArrayList<>();


    //여기까지가 HSH 코드

}
