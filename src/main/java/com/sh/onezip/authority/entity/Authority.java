package com.sh.onezip.authority.entity;

import com.sh.onezip.authority.entity.RoleAuth;
import com.sh.onezip.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(
        name = "tb_authority",
        uniqueConstraints =
            @UniqueConstraint(
                name = "uq_authority_member_id_name",
                columnNames = {"id", "member_id", "user_type"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority implements Serializable{

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(generator = "seq_Authority_id_generator")
    @SequenceGenerator(
            name = "seq_Authority_id_generator",
            sequenceName = "tb_authority_seq",
            initialValue = 300,
            allocationSize = 1)

    @Column
    private Long id;


//    @Column(nullable = false, name = "member_id")
//    private Long memberId;
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
//    @Column(nullable = false, name = "member_id")
//    private String memberId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleAuth userType;


    // 여기까지가 HSH 코드
}

