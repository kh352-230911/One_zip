package com.sh.onezip.business.entity;

import com.sh.onezip.member.entity.Member;
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
@Table(name="tb_business")
public class Business {
    @Id
    @GeneratedValue(generator = "seq_tb_business_id_generator")
    @SequenceGenerator(
            name="seq_tb_business_id_generator",
            sequenceName = "seq_tb_business_id",
            initialValue = 1,
            allocationSize = 1)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id") // tb_business member_id = tb_member id
    private Member member;
    @Column
    private String bizName;
    @Column(unique = true)
    private String bizRegNo;
    @Column
    @Enumerated(EnumType.STRING)
    private BizAccess bizRegStatus;
// HBK end


}
