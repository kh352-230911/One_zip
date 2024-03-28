package com.sh.onezip.member.entity;

import com.sh.onezip.authority.entity.Authority;
import com.sh.onezip.business.entity.Business;
import com.sh.onezip.customeranswercenter.entity.AnswerCenter;
import com.sh.onezip.customerquestioncenter.entity.QuestionCenter;
import com.sh.onezip.member.entity.Gender;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.productanswer.entity.ProductAnswer;
import com.sh.onezip.productquestion.entity.ProductQuestion;
import jakarta.persistence.*;
import lombok.*;
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

// HBK write Tostring / reason : Member Authority ,QuestionCenter, AnswerCenter stackoverflow

@ToString(exclude = {"authorities", "questionCenters", "answerCenters"})
@Table(name = "tb_member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Member_id_generator")
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
    private List<Authority> authorities;
//    private List<Authority> authorities = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Address> addresses = new ArrayList<>();
    //여기까지가 HSH 코드

    // HBK start
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<QuestionCenter> questionCenters = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<AnswerCenter> answerCenters = new ArrayList<>();
    // HBK end

    // KMJ start
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<ProductQuestion> productQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<ProductAnswer> productAnswers = new ArrayList<>();


    //

}
