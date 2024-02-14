package com.sh.onezip.productimage.entity;

import com.sh.onezip.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_pimage")
public class ProductImage {
    @Id
    @GeneratedValue(generator = "seq_pimage_generator")
    @SequenceGenerator(
            name = "seq_pimage_generator",
            sequenceName = "seq_tb_pimage",
            initialValue = 1,
            allocationSize = 1)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_no")
    private Product product;
    @Column(name = "original_filename")
    private String originalFilename;
    @Column(name = "image_key")
    private String imageKey;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "image_type")
    @Enumerated(EnumType.STRING)
    private PImageType imageType;

}
