package com.sh.onezip.bizauthority.entity;//package com.sh.onezip.bizauthority.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(
        name = "tb_biz_authority",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_biz_authority_biz_member_id_name",
                columnNames = {"biz_member_id", "bizUserType"}
        )
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BizAuthority implements Serializable {
    @Id
    @GeneratedValue(generator = "seq_BizAuthority_id_generator")
    @SequenceGenerator(
            name = "seq_BizAuthority_id_generator",
            sequenceName = "seq_tb_biz_authority_id",
            initialValue = 1,
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, name = "biz_member_id")
    private String bizMemberId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BizRoleAuth bizUserType;

}
