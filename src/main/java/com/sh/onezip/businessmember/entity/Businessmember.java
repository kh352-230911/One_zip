package com.sh.onezip.businessmember.entity;

import com.sh.onezip.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
@Builder
// table 명
@Table(name = "tb_businessmember")
@NoArgsConstructor
@AllArgsConstructor
@Entity
// tostring 생성 시 products 필드 출력을 제외하도록 지정
//@ToString(exclude = "products")
public class Businessmember {
    @Id
    @Column
    private String bizMemberId;
    @Column
    private String bizPassword;
    @Column
    private String bizName;
    @Column(unique = true)
    private String bizPhone;
    @Column
    @CreationTimestamp
    private LocalDate bizRegDate;
    @Column
    private String bizAddr;
    @Column
    private String bizImageKey;
    @Column
    private String bizImageUrl;
    @Column
    private String bizLicense;
    @Column(unique = true)
    private String bizRegNo;
    @Column
    @Enumerated(EnumType.STRING)
    private BizAccess bizRegStatus;
//    @OneToMany(mappedBy = "businessmember", fetch = FetchType.LAZY)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="id")
    @Builder.Default
    private List<Product> products = new ArrayList<>();

}
