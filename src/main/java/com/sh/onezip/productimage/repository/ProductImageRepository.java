package com.sh.onezip.productimage.repository;

import com.sh.onezip.productimage.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<Optional<ProductImage>> findByProductId(Long id);
}
