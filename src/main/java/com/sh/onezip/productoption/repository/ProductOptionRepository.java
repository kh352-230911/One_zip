package com.sh.onezip.productoption.repository;

import com.sh.onezip.productoption.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    @Query("from ProductOption po where po.product.id = :id")
    List<ProductOption> findAllByProductId(Long id);

}
