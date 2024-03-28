package com.sh.onezip.productoption.service;

import com.sh.onezip.product.dto.ProductDetailDto;
import com.sh.onezip.productoption.dto.ProductOptionDto;
import com.sh.onezip.productoption.entity.ProductOption;
import com.sh.onezip.productoption.repository.ProductOptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
@Transactional
public class ProductOptionService {

    @Autowired
    ProductOptionRepository productOptionRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<ProductOption> findAllByProductId(Long id) {
        return productOptionRepository.findAllByProductId(id);
    }

    public ProductOption findById(Long selectOption) {
        return productOptionRepository.findById(selectOption).orElse(null);
    }

    public void createProductOption(ProductOptionDto productOptionDto) {
        ProductOption productOption = convertToProductOption(productOptionDto);
        productOption.setId(null);
        productOptionRepository.save(productOption);
    }

    private ProductOption convertToProductOption(ProductOptionDto productOptionDto) {
        return modelMapper.map(productOptionDto, ProductOption.class);
    }


    // HBK start

}
