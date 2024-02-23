package com.sh.onezip.orderproduct.repository;

import com.sh.onezip.orderproduct.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    @Query("from OrderProduct op where op.productLog.id = :id")
    List<OrderProduct> findByProductLogId(Long id);


    @Query("delete from OrderProduct op where op.productLog.id = :merchantUid")
    void deleteByPlogId(Long merchantUid);
}
