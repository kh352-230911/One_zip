package com.sh.onezip.member.entity;

import com.sh.onezip.authority.entity.Authority;
import com.sh.onezip.member.entity.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "tb_member")
public class Member {
    @Id //jakarta.persistence
    @Column
    private String memberId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
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
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate regDate;
    @Column(nullable = false)
    private String memberAddr;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private List<Authority> authorities;

}
