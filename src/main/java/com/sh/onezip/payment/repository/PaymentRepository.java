package com.sh.onezip.payment.repository;

import com.sh.onezip.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("delete from Payment p where p.productLog.id = :merchantUid")
    void deleteByPlogId(Long merchantUid);
}
