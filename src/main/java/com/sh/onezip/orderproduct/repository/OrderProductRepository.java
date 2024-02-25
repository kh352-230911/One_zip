package com.sh.onezip.orderproduct.repository;

import com.sh.onezip.orderproduct.entity.OrderProduct;
import com.sh.onezip.productReview.entity.ProductReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    @Query("from OrderProduct op where op.productLog.id = :id")
    List<OrderProduct> findByProductLogId(Long id);


    @Query("delete from OrderProduct op where op.productLog.id = :merchantUid")
    void deleteByPlogId(Long merchantUid);

    @Query("from OrderProduct op where op.productLog.member.memberId = :memberId order by op.id desc")
    Page<OrderProduct> productOrderFindAllByMemberId(Pageable pageable, String memberId);

    @Query("from OrderProduct op where op.productLog.member.memberId = :memberId")
    List<OrderProduct> productOrderDtoFindAllByMemberId(String memberId);

    @Query("from OrderProduct op where op.productLog.member.memberId = :memberId")
    List<OrderProduct> findAllOrderProductByMemberId(String memberId);

    @Query("from OrderProduct op where op.product.businessmember.bizMemberId = :bizMemberId order by op.id desc")
    Page<OrderProduct> productOrderFindAllByBizMemberId(Pageable pageable, String bizMemberId);

    @Query("from OrderProduct op where op.product.businessmember.bizMemberId = :bizMemberId")
    List<OrderProduct> productOrderDtoFindAllByBizMemberId(String bizMemberId);
    @Query("from OrderProduct op where op.product.businessmember.bizMemberId = :bizMemberId")
    List<OrderProduct> findAllOrderProductByBizMemberId(String bizMemberId);
}
