package com.sh.onezip.product.service;

import com.sh.onezip.attachment.repository.AttachmentRepository;
import com.sh.onezip.attachment.service.AttachmentService;
import com.sh.onezip.product.dto.ProductDetailDto;
import com.sh.onezip.product.dto.ProductListDto;
import com.sh.onezip.product.dto.ProductPurchaseInfoDto;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.repository.ProductRepository;
import com.sh.onezip.productoption.dto.ProductOptionDto;
import com.sh.onezip.productoption.entity.ProductOption;
import com.sh.onezip.productoption.service.ProductOptionService;
import com.sh.onezip.productquestion.entity.ProductQuestion;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@Transactional
public class ProductService {


    // variable 선언 start

    @Autowired
    ProductRepository productRepository;
    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    ProductOptionService productOptionService;
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

    public ProductDetailDto productDetailDtofindById(Long id) {
        return productRepository.findById(id)
                .map((product) -> convertToProductDetailDto(product))
                .orElseThrow();
    }

    private ProductDetailDto convertToProductDetailDto(Product product) {
        ProductDetailDto productDetailDto = modelMapper.map(product, ProductDetailDto.class);
        productDetailDto.setApplyPrice((int) (product.getProductPrice() * (1 - product.getDiscountRate())));
        return productDetailDto;
    }

    public ProductPurchaseInfoDto productPurchaseInfoDtofindById(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        Product product = productOpt.orElse(null);
        ProductPurchaseInfoDto productPurchaseInfoDto = modelMapper.map(product, ProductPurchaseInfoDto.class);
        return productPurchaseInfoDto;
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // KMJ end

    // HBK start

    public Page<ProductListDto> findAllBizIdProduct(Long id, Pageable pageable) {
        Page<Product> productListDtoPage = productRepository.findAllBizIdProduct(id, pageable);
        return productListDtoPage.map(product -> convertToProductListDto(product));
    }

    // 사업자 상품 등록
    public void createProductBiz(ProductDetailDto productDetailDto) {
        Product product = productRepository.save(convertToProductDetailInsertDto(productDetailDto));
        List<ProductOptionDto> productOptionList = productDetailDto.getProductOptionlist();
        if (productOptionList != null) {
            for (ProductOptionDto productOptionDto : productOptionList) {
                productOptionDto.setProductId(product.getId());
                productOptionService.createProductOption(productOptionDto);
            }
        }
        productDetailDto.getAttachments().forEach(attachmentCreateDto -> {
            attachmentCreateDto.setRefId(product.getId());
            attachmentCreateDto.setRefType("SP");
            attachmentService.createAttachment(attachmentCreateDto);
        });

    }

    private Product convertToProductDetailInsertDto(ProductDetailDto productDetailDto) {
        Product product = modelMapper.map(productDetailDto, Product.class);
        return product;
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public ProductListDto findByBizProductId(Long id) {
        Product product = productRepository.findByBizProductId(id);
        return convertToProductListDto(product);
    }

}



