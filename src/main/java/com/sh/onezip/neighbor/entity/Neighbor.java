package com.sh.onezip.neighbor.entity;

import com.sh.onezip.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_neighbor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Neighbor {
    @Id
    @GeneratedValue(generator = "seq_tb_neighbor_id_generator")
    @SequenceGenerator(
            name = "seq_tb_neighbor_id_generator",
            sequenceName = "seq_tb_neighbor_id",
            initialValue = 1,
            allocationSize = 1)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id_1")
    private Member member1;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id_2")
    private Member member2;
    private String status; // default status is PENDING
    @Column(name = "requested_at")
    private LocalDateTime requestedAt;
    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;
}
