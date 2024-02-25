package com.sh.onezip.businessproduct.entity;

import com.sh.onezip.authority.entity.Authority;
//import com.sh.onezip.bizauthority.entity.BizAuthority;
import com.sh.onezip.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import org.codehaus.groovy.runtime.StreamGroovyMethods;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_businessmember")
@Data
@ToString(exclude = "products")
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
    private String bizLicense; // 사업자등록증 -> 권한 컬럼
    @Column(unique = true)
    private String bizRegNo;
    @Column
    @Enumerated(EnumType.STRING)
    private BizAccess bizRegStatus;

    @OneToMany(mappedBy = "businessmember", fetch = FetchType.EAGER)
    @Builder.Default
    private List<Product> products = new ArrayList<>();

//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "biz_member_id")
//    private List<BizAuthority> bizAuthorities;

}
