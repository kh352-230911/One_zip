package com.sh.onezip.member.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_address")
public class Address {
    @Id
    @GeneratedValue(generator = "seq_address_id_generator")
    @SequenceGenerator(
            name = "seq_address_id_generator",
            sequenceName = "address_seq",
            initialValue = 1,
            allocationSize = 1)
    private Long id;
//    @Column(nullable = false, unique = true)
//    private String memberId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id") // 데이터베이스의 컬럼명과 일치시키세요.
    private Member member;

    @Column(nullable = false)
    private String recipientName;
    @Column(nullable = false)
    private String recipientPhone;
    @Column(nullable = false)
    private AddressType addressType;
    @Column
    private String baseAddress;
    @Column
    private String detailAddress;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
