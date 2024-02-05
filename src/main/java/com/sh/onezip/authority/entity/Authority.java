package com.sh.onezip.authority.entity;

import com.sh.onezip.authority.entity.RoleAuth;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "tb_authority",
        uniqueConstraints =
        @UniqueConstraint(
                name = "uq_authority_member_id_name",
                columnNames = {"member_id", "user_type"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, name = "member_id")
    private String memberId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleAuth userType;

}
