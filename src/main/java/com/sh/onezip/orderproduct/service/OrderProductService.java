package com.sh.onezip.orderproduct.service;

import com.sh.onezip.orderproduct.dto.OrderProductDto;
import com.sh.onezip.orderproduct.entity.OrderProduct;
import com.sh.onezip.orderproduct.repository.OrderProductRepository;
import com.sh.onezip.payment.repository.PaymentRepository;
import com.sh.onezip.productLog.repository.ProductLogRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderProductService {

    @Autowired
    OrderProductRepository orderProductRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    ProductLogRepository productLogRepository;
    @Autowired
    ModelMapper modelMapper;

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

    public Page<OrderProductDto> productOrderFindAllByMemberId(Pageable pageable, String memberId) {
        Page<OrderProduct> orderProductPage = orderProductRepository.productOrderFindAllByMemberId(pageable, memberId);
        return orderProductPage.map((orderProduct) -> convertToOrderProductDto(orderProduct));
    }

    private OrderProductDto convertToOrderProductDto(OrderProduct orderProduct) {
        OrderProductDto orderProductDto = modelMapper.map(orderProduct, OrderProductDto.class);
        return orderProductDto;
    }

    public List<OrderProductDto> productOrderDtoFindAllByMemberId(String memberId) {
        List<OrderProduct> OrderProducts = orderProductRepository.productOrderDtoFindAllByMemberId(memberId);
        List<OrderProductDto> productOrderDtos = new ArrayList<>();
        for (OrderProduct orderProduct : OrderProducts) {
            productOrderDtos.add(convertToOrderProductDto(orderProduct));
        }
        return productOrderDtos;
    }

    public List<OrderProduct> findAllOrderProductByMemberId(String memberId) {
        return orderProductRepository.findAllOrderProductByMemberId(memberId);
    }

    //

    public Page<OrderProductDto> productOrderFindAllByBizMemberId(Pageable pageable, String bizMemberId) {
        Page<OrderProduct> orderProductPage = orderProductRepository.productOrderFindAllByBizMemberId(pageable, bizMemberId);
        return orderProductPage.map((orderProduct) -> convertToOrderProductDto(orderProduct));
    }

    public List<OrderProductDto> productOrderDtoFindAllByBizMemberId(String bizMemberId) {
        List<OrderProduct> OrderProducts = orderProductRepository.productOrderDtoFindAllByBizMemberId(bizMemberId);
        List<OrderProductDto> productOrderDtos = new ArrayList<>();
        for (OrderProduct orderProduct : OrderProducts) {
            productOrderDtos.add(convertToOrderProductDto(orderProduct));
        }
        return productOrderDtos;
    }

    public List<OrderProduct> findAllOrderProductByBizMemberId(String bizMemberId) {
        return orderProductRepository.findAllOrderProductByBizMemberId(bizMemberId);
    }


}
