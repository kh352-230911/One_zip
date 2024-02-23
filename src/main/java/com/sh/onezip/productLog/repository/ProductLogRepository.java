package com.sh.onezip.productLog.repository;

import com.sh.onezip.productLog.entity.ProductLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLogRepository extends JpaRepository<ProductLog, Long> {

}
