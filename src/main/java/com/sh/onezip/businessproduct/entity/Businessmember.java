package com.sh.onezip.businessproduct.entity;

import com.sh.onezip.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;
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
    private String bizLicense;
    @Column(unique = true)
    private String bizRegNo;
    @Column
    @Enumerated(EnumType.STRING)
    private BizAccess bizRegStatus;

    @OneToMany(mappedBy = "businessmember", fetch = FetchType.EAGER)
    @Builder.Default
    private List<Product> products = new ArrayList<>();

}
