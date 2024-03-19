package com.sh.onezip.product.service;

import com.sh.onezip.attachment.repository.AttachmentRepository;
import com.sh.onezip.product.dto.ProductListDto;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {


    // variable 선언 start

    @Autowired
    ProductRepository productRepository;
    @Autowired
    AttachmentRepository attachmentRepository;


    @Autowired
    ModelMapper modelMapper;

    // variable 선언 end


    // KMJ start

    public Page<ProductListDto> productListDtoFindAllByPrice(Pageable pageable, int price) {
        Page<Product> productPage = null;
        if ((price == 0) || (price == 100001)) {
            productPage = productRepository.findAllByPriceUpper(pageable, price);
        } else {
            productPage = productRepository.findAllByPriceUnder(pageable, price);
        }

        return productPage.map((product) -> convertToProductListDto(product));
    }

    public List<ProductListDto> productListDtoFindAllByPrice(int price) {
        List<Product> products = new ArrayList<>();
        if ((price == 0) || (price == 100001)) {
            products = productRepository.findAllByPriceUpper(price);
        } else {
            products = productRepository.findAllByPriceUnder(price);
        }
        List<ProductListDto> productListDtos = new ArrayList<>();
        for (Product product : products) {
            productListDtos.add(convertToProductListDto(product));
        }
        return productListDtos;
    }

    private ProductListDto convertToProductListDto(Product product) {
        ProductListDto productListDto = modelMapper.map(product, ProductListDto.class);
        productListDto.setMemberName(product.getMember().getName());
        productListDto.setOriginalPrice((int) (product.getProductPrice() * (1 - product.getDiscountRate())));
        productListDto.setAttachmentList(attachmentRepository.findProductAttachmentToList(productListDto.getId(), "SP"));
        return productListDto;
    }

    // KMJ end


}




