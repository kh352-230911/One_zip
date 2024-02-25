package com.sh.onezip.productimage.service;

import com.sh.onezip.productimage.entity.ProductImage;
import com.sh.onezip.productimage.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {
    @Autowired
    ProductImageRepository productImageRepository;
    public List<ProductImage> findAllByProductId(Long id) {
        return productImageRepository.findAllByProductId(id);
    }
}
