package com.sh.onezip.businessmember.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_businessmember")
@Data
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

}
