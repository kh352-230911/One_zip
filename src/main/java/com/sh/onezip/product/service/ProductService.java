package com.sh.onezip.product.service;

import com.sh.onezip.product.dto.BusinessProductCreateDto;
import com.sh.onezip.product.dto.ProductDetailDto;
import com.sh.onezip.product.dto.ProductListDto;
import com.sh.onezip.product.dto.ProductPurchaseInfoDto;
import com.sh.onezip.productReview.dto.ProductReviewCreateDto;
import com.sh.onezip.productReview.dto.ProductReviewDto;
import com.sh.onezip.productReview.entity.ProductReview;
import com.sh.onezip.productReview.repository.ProductReviewRepository;
import com.sh.onezip.productquestion.dto.ProductQuestionCreateDto;
import com.sh.onezip.productquestion.dto.ProductQuestionDto;
import com.sh.onezip.product.entity.Product;
import com.sh.onezip.product.repository.ProductRepository;
import com.sh.onezip.productoption.repository.ProductOptionRepository;
import com.sh.onezip.productquestion.entity.ProductQuestion;
import com.sh.onezip.productquestion.repository.ProductQuestionRepository;
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
    ProductOptionRepository productOptionRepository;

    @Autowired
    ProductReviewRepository productReviewRepository;

    @Autowired
    ProductQuestionRepository productQuestionRepository;

    @Autowired
    ModelMapper modelMapper;

    // 명준 작업 공간 start


    public Page<ProductListDto> productListDtoFindAll(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map((product) -> convertToProductListDto(product));
    }

    private ProductListDto convertToProductListDto(Product product) {
        ProductListDto productListDto = modelMapper.map(product, ProductListDto.class);
        productListDto.setBizName(product.getBusinessmember().getBizName());
        productListDto.setSellPrice((int)(product.getProductPrice() * (1 - ((double)product.getDiscountRate() / 100))));
        return productListDto;
    }

    public ProductDetailDto productDetailDtofindById(Long id) {
        return productRepository.findById(id)
                .map((product) -> convertToProductDetailDto(product))
                .orElseThrow();
    }

    private ProductDetailDto convertToProductDetailDto(Product product) {
        ProductDetailDto productDetailDto = modelMapper.map(product, ProductDetailDto.class);
        productDetailDto.setSellPrice((int)(product.getProductPrice() * (1 - ((double)product.getDiscountRate() / 100))));

//        productOptionRepository.findByProductId(product.getId());
//        productDetailDto.setOptionNames();
//        productDetailDto.setOptionPrices();
        return productDetailDto;
    }


    public List<ProductListDto> productListDtoFindAll() {
        List<Product> products = productRepository.findAll();
        List<ProductListDto> productListDtos = new ArrayList<>();
        for(Product product : products){
            productListDtos.add(convertToProductListDto(product));
        }
        return productListDtos;
    }


    public ProductPurchaseInfoDto productPurchaseInfoDtofindById(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        Product product = productOpt.orElse(null);
        ProductPurchaseInfoDto productPurchaseInfoDto = modelMapper.map(product, ProductPurchaseInfoDto.class);
        return productPurchaseInfoDto;
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public List<ProductQuestion> pquestionFindByProductid(Long id) {
        return productRepository.pquestionFindByProductid(id);
    }

    public List<ProductReview> productReviewFindByProductid(Long id) {
        return productRepository.productReviewFindByProductid(id);
    }




    private ProductQuestionDto convertToProductQuestionDto(ProductQuestion productQuestion) {
        ProductQuestionDto productQuestionDto = modelMapper.map(productQuestion, ProductQuestionDto.class);
//        if(productQuestionDto.getProductAnswer() == null){
//            productQuestionDto.getProductAnswer()
//        }
        return productQuestionDto;
    }

    public Page<ProductQuestionDto> productQuestionFindAllByProductId(Pageable pageable, Long productId) {
        Page<ProductQuestion> productQuestionPage = productRepository.productQuestionFindAllByProductId(pageable, productId);
        return productQuestionPage.map((productQuestion) -> convertToProductQuestionDto(productQuestion));
    }

    public List<ProductQuestionDto> productQuestionDtoFindAllByProductId(Long productId) {
        List<ProductQuestion> productQuestions = productRepository.productQuestionFindAllByProductId(productId);
        List<ProductQuestionDto> productQuestionDtos = new ArrayList<>();
        for(ProductQuestion productQuestion : productQuestions){
            productQuestionDtos.add(convertToProductQuestionDto(productQuestion));
        }
        return productQuestionDtos;
    }

    public Page<ProductReviewDto> productReviewFindAllByProductId(Pageable pageable, Long productId) {
        Page<ProductReview> productReviewPage = productRepository.productReviewFindAllByProductId(pageable, productId);
        return productReviewPage.map((productReview) -> convertToProductReviewDto(productReview));
    }

    public List<ProductReviewDto> productReviewDtoFindAllByProductId(Long productId) {
        List<ProductReview> productReviews = productRepository.productReviewFindAllByProductId(productId);
        List<ProductReviewDto> productReviewsDtos = new ArrayList<>();
        for(ProductReview productReview : productReviews){
            productReviewsDtos.add(convertToProductReviewDto(productReview));
        }
        return productReviewsDtos;
    }

    private ProductReviewDto convertToProductReviewDto(ProductReview productReview) {
        ProductReviewDto productReviewDto = modelMapper.map(productReview, ProductReviewDto.class);
        return productReviewDto;
    }

    public void createReview(ProductReviewCreateDto productReviewCreateDto) {
        ProductReview productReview = modelMapper.map(productReviewCreateDto, ProductReview.class);
        productReviewRepository.save(productReview);
    }

    public void createQuestion(ProductQuestionCreateDto productQuestionCreateDto) {
        ProductQuestion productQuestion = modelMapper.map(productQuestionCreateDto, ProductQuestion.class);
        productQuestionRepository.save(productQuestion);
    }

    public Page<ProductListDto> productListDtoFindAllByPrice(Pageable pageable, int refPrice) {
        Page<Product> productPage = null;
        if((refPrice == 0) || (refPrice == 100001)){
            productPage = productRepository.findAllByPriceUpper(pageable, refPrice);
        }
        else{
            productPage = productRepository.findAllByPriceUnder(pageable, refPrice);
        }

        return productPage.map((product) -> convertToProductListDto(product));
    }

    public List<ProductListDto> productListDtoFindAllByPrice(int refPrice) {
        List<Product> products = new ArrayList<>();
        if((refPrice == 0) || (refPrice == 100001)){
            products = productRepository.findAllByPriceUpper(refPrice);
        }
        else{
            products = productRepository.findAllByPriceUnder(refPrice);
        }
        List<ProductListDto> productListDtos = new ArrayList<>();
        for(Product product : products){
            productListDtos.add(convertToProductListDto(product));
        }
        return productListDtos;
    }

    // 명준 작업 공간 end

    // 보경 작업 공간 start =================================================================


    private Product convertTobusinessproductcreate(BusinessProductCreateDto businessProductCreateDto) {
        System.out.println(businessProductCreateDto + "등록해줘~");
        Product product = modelMapper.map(businessProductCreateDto, Product.class);
        System.out.println(product + "flag의 product");
        return product;
    }


    public void businessproductcreate(BusinessProductCreateDto businessProductCreateDto) {
        Product product1 = convertTobusinessproductcreate(businessProductCreateDto);
        System.out.println(product1 + "product1");


        Product product2 = productRepository.save(product1);
        System.out.println(businessProductCreateDto + "id~~");
//        businessProductCreateDto.setBizMemberId(product.getBusinessmember().getBizMemberId());
    }

    public Page<ProductListDto> findAllBiz(Pageable pageable, String bizMemberId) {
        Page<Product> businessproductPage = productRepository.findAllBiz(pageable, bizMemberId);
        return businessproductPage.map((product) -> convertTobusinessproductList(product));
    }

    // ProductListDto사용함 (사업자 상품 전체 조회 페이지)
    private ProductListDto convertTobusinessproductList(Product product) {
        ProductListDto productListDto = modelMapper.map(product, ProductListDto.class);
        productListDto.setSellPrice((int) (product.getProductPrice() * (1 - ((double) product.getDiscountRate() / 100))));
        return productListDto;
    }

    public List<ProductListDto> findByBusinessmemberBizMemberId(String bizMemberId) {
        List<Product> products = productRepository.findByBusinessmemberBizMemberId(bizMemberId);
        List<ProductListDto> productListDtos = new ArrayList<>();
        for (Product product : products) {
            productListDtos.add(convertTobusinessproductList(product));
        }
        return productListDtos;
    }

    public Product businessproductupdate(BusinessProductCreateDto businessProductCreateDto) {
        Product product1 = convertTobusinessproductupdate(businessProductCreateDto);
        System.out.println(product1 + "product1");
        return productRepository.save(product1);
    }

    private Product convertTobusinessproductupdate(BusinessProductCreateDto businessProductCreateDto) {
        Product product = modelMapper.map(businessProductCreateDto, Product.class);
        return product;
    }

    public void deleteproductlist(Product product) {
        productRepository.delete(product);
    }
}
