package com.sh.onezip.product.service;

import com.sh.onezip.businessproduct.entity.Businessmember;
import com.sh.onezip.product.dto.BusinessProductCreateDto;
import com.sh.onezip.product.dto.ProductDetailDto;
import com.sh.onezip.product.dto.ProductListDto;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ModelMapper modelMapper;



    public Page<ProductListDto> findAll(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map((product) -> convertToProductListDto(product));
    }

    private ProductListDto convertToProductListDto(Product product) {
        ProductListDto productListDto = modelMapper.map(product, ProductListDto.class);
        productListDto.setBizName(product.getBusinessmember().getBizName());
        productListDto.setSellPrice((int)(product.getProductPrice() * (1 - ((double)product.getDiscountRate() / 100))));
        return productListDto;
    }

    public ProductDetailDto productdetailDtofindById(Long id) {
        return productRepository.findById(id)
                .map((product) -> convertToProductDetailDto(product))
                .orElseThrow();
    }

    private ProductDetailDto convertToProductDetailDto(Product product) {
        ProductDetailDto productDetailDto = modelMapper.map(product, ProductDetailDto.class);
        productDetailDto.setSellPrice((int)(product.getProductPrice() * (1 - ((double)product.getDiscountRate() / 100))));
        return productDetailDto;
    }


    public List<ProductListDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductListDto> productListDtos = new ArrayList<>();
        for(Product product : products){
            productListDtos.add(convertToProductListDto(product));
        }
        return productListDtos;
    }

    public Page<ProductListDto>  findAllBiz(Pageable pageable) {
        Page<Product> businessproductPage = productRepository.findAllBiz(pageable);
        return businessproductPage.map((product) -> convertTobusinessproductList(product));
    }
    // ProductListDto사용함 (사업자 상품 전체 조회 페이지)
    private ProductListDto convertTobusinessproductList(Product product) {
        ProductListDto productListDto = modelMapper.map(product, ProductListDto.class);
        productListDto.setSellPrice((int)(product.getProductPrice() * (1 - ((double)product.getDiscountRate() / 100))));
        return productListDto;
    }


    public List<ProductListDto> findByBusinessmemberBizMemberId(String bizMemberId) {
        List<Product> products = productRepository.findByBusinessmemberBizMemberId(bizMemberId);
        List<ProductListDto> productListDtos = new ArrayList<>();
        for(Product product : products){
            productListDtos.add(convertTobusinessproductList(product));
        }
        return productListDtos;
    }

    public Product findById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        Product product = productOptional.orElse(null);
        return product;
    }

    public void businessproductcreate(BusinessProductCreateDto businessProductCreateDto) {
        // Product 객체로 변환
        System.out.println(businessProductCreateDto + "fffffff");
        Product product = modelMapper.map(businessProductCreateDto, Product.class);
        System.out.println(businessProductCreateDto + "hello");
        System.out.println(product + "bye");
        productRepository.save(product);
        System.out.println(product);
    }
}
//
//    ProductListDto productListDto = modelMapper.map(product, ProductListDto.class);
//        productListDto.setBizName(product.getBusinessmember().getBizName());
//                productListDto.setSellPrice((int)(product.getProductPrice() * (1 - ((double)product.getDiscountRate() / 100))));