package com.sh.onezip.orderproduct.service;

import com.sh.onezip.member.entity.Member;
import com.sh.onezip.orderproduct.repository.OrderProductRepository;
import com.sh.onezip.payment.repository.PaymentRepository;
import com.sh.onezip.productLog.repository.ProductLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderProductService {

    @Autowired
    OrderProductRepository orderProductRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    ProductLogRepository productLogRepository;

    public void orderRollBack(Map<String, String> requestData) {
        Long merchantUid = Long.parseLong(requestData.get("merchant_uid")); // 주문 고유번호
        String impUid = requestData.get("imp_uid");

        orderProductRepository.deleteByPlogId(merchantUid);
        System.out.println("orderProduct 삭제 완료");
        paymentRepository.deleteByPlogId(merchantUid);
        System.out.println("payment 삭제 완료");
        productLogRepository.deleteById(merchantUid);
        System.out.println("productLog 삭제 완료");


    }
}
