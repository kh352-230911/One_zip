package com.sh.onezip.product.dto;

import com.sh.onezip.attachment.dto.AttachmentCreateDto;
import com.sh.onezip.attachment.entity.Attachment;
import com.sh.onezip.product.entity.ProductType;
import com.sh.onezip.productoption.dto.ProductOptionDto;
import com.sh.onezip.productoption.entity.ProductOption;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDetailDto {

    private Long id;
    private String productName;
    private String businessmember;
    private ProductType productTypeCode;
    private int productPrice;
    private int applyPrice; // product에 없는 값. // 실제 판매 가격
    private double discountRate;
    private LocalDate regDate;
    private List<ProductOption> productOptions;
    // 사업자 상품 등록 용
    private List<ProductOptionDto> productOptionlist;
    private String bizMemberId; // product에 없는 값.
    private List<Attachment> attachmentList; // product에 없는 값.
    private List<AttachmentCreateDto> attachments = new ArrayList<>(); // 사업자 상품 등록 용
    private Long memberId; // 사업자 등록 용 #Product#memberId
    private int OriginalPrice;
    // 사업자 상품 등록 용
    public void addAttachmentCreateDto(AttachmentCreateDto attachmentCreateDto) {
        this.attachments.add(attachmentCreateDto);
    }
}
