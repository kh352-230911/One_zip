//package com.sh.onezip.productimage.repository;
//
//import com.sh.onezip.productimage.entity.ProductImage;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
//    List<Optional<ProductImage>> findByProductId(Long id);
//
//    @Query("from ProductImage pi where pi.product.id = :id")
//    List<ProductImage> findAllByProductId(Long id);
//}
