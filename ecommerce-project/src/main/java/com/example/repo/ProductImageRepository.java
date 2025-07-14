package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}
