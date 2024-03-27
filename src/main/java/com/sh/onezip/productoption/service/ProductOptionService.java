package com.sh.onezip.productoption.service;

import com.sh.onezip.productoption.entity.ProductOption;
import com.sh.onezip.productoption.repository.ProductOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductOptionService {

    @Autowired
    ProductOptionRepository productOptionRepository;

    public List<ProductOption> findAllByProductId(Long id) {
        return productOptionRepository.findAllByProductId(id);
    }

    public ProductOption findById(Long selectOption) {
        return productOptionRepository.findById(selectOption).orElse(null);
    }

}
